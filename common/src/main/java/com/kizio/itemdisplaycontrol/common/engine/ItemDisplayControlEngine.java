package com.kizio.itemdisplaycontrol.common.engine;

import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.common.api.ToggleFeedback;
import com.kizio.itemdisplaycontrol.common.config.ItemDisplayConfigStorage;
import com.kizio.itemdisplaycontrol.common.config.ItemDisplayRuntimeConfig;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ItemDisplayControlEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);
    public static final int CONFIG_SAVE_DEBOUNCE_TICKS = 10;

    private final ExecutorService ioExecutor;
    private final boolean managesExecutor;

    private ToggleFeedback toggleFeedback = ToggleFeedback.NO_OP;
    private boolean initialized;
    private boolean shutdownHookRegistered;

    private ItemDisplayRuntimeConfig runtimeConfig = ItemDisplayRuntimeConfig.defaults();
    private Path configFilePath = Path.of("config", "itemdisplaycontrol.properties");
    private int configSaveCooldownTicks;
    private boolean configDirty;
    private CompletableFuture<Void> pendingSave = CompletableFuture.completedFuture(null);

    public ItemDisplayControlEngine() {
        this(Executors.newSingleThreadExecutor(task -> {
            Thread thread = new Thread(task, Constants.MOD_ID + "-config-io");
            thread.setDaemon(true);
            return thread;
        }), true);
    }

    public ItemDisplayControlEngine(ExecutorService ioExecutor) {
        this(ioExecutor, false);
    }

    private ItemDisplayControlEngine(ExecutorService ioExecutor, boolean managesExecutor) {
        this.ioExecutor = Objects.requireNonNull(ioExecutor, "ioExecutor");
        this.managesExecutor = managesExecutor;
    }

    public synchronized void init(ToggleFeedback feedback) {
        init(feedback, Path.of("config"));
    }

    public synchronized void init(ToggleFeedback feedback, Path configDirectory) {
        this.toggleFeedback = Objects.requireNonNull(feedback, "feedback");
        registerShutdownHookIfNeeded();
        loadConfiguration(Objects.requireNonNull(configDirectory, "configDirectory"));

        this.initialized = true;
        LOGGER.info("{} initialized", Constants.MOD_NAME);
    }

    public synchronized void onClientTick() {
        if (!initialized) {
            return;
        }

        flushConfigurationIfDue();
    }

    public synchronized boolean toggleEnabled() {
        boolean enabled = !runtimeConfig.enabled();
        runtimeConfig.setEnabled(enabled);
        markConfigurationDirty();
        toggleFeedback.onToggle(enabled);
        LOGGER.debug("{} toggled {}", Constants.MOD_NAME, enabled ? "on" : "off");
        return enabled;
    }

    public synchronized boolean toggleProtection(ProtectionTarget target) {
        Objects.requireNonNull(target, "target");
        boolean enabled = !runtimeConfig.isProtected(target);
        runtimeConfig.setProtected(target, enabled);
        markConfigurationDirty();
        return enabled;
    }

    public synchronized boolean toggleWhitelistEnabled() {
        boolean enabled = !runtimeConfig.whitelistEnabled();
        runtimeConfig.setWhitelistEnabled(enabled);
        markConfigurationDirty();
        return enabled;
    }

    public synchronized boolean toggleBlacklistEnabled() {
        boolean enabled = !runtimeConfig.blacklistEnabled();
        runtimeConfig.setBlacklistEnabled(enabled);
        markConfigurationDirty();
        return enabled;
    }

    public synchronized void setWhitelistItemsFromText(String rawValue) {
        LinkedHashSet<String> normalizedItems = new LinkedHashSet<>(ItemDisplayRuntimeConfig.parseItemList(rawValue));
        if (runtimeConfig.whitelistedItems().equals(normalizedItems)) {
            return;
        }

        runtimeConfig.setWhitelistedItems(normalizedItems);
        markConfigurationDirty();
    }

    public synchronized void setBlacklistItemsFromText(String rawValue) {
        LinkedHashSet<String> normalizedItems = new LinkedHashSet<>(ItemDisplayRuntimeConfig.parseItemList(rawValue));
        if (runtimeConfig.blacklistedItems().equals(normalizedItems)) {
            return;
        }

        runtimeConfig.setBlacklistedItems(normalizedItems);
        markConfigurationDirty();
    }

    public synchronized void setProtection(ProtectionTarget target, boolean enabled) {
        Objects.requireNonNull(target, "target");
        if (runtimeConfig.isProtected(target) == enabled) {
            return;
        }

        runtimeConfig.setProtected(target, enabled);
        markConfigurationDirty();
    }

    public synchronized boolean isEnabled() {
        return runtimeConfig.enabled();
    }

    public synchronized boolean isProtectionEnabled(ProtectionTarget target) {
        return runtimeConfig.isProtected(Objects.requireNonNull(target, "target"));
    }

    public synchronized boolean isWhitelistEnabled() {
        return runtimeConfig.whitelistEnabled();
    }

    public synchronized boolean isBlacklistEnabled() {
        return runtimeConfig.blacklistEnabled();
    }

    public synchronized String getWhitelistItemsText() {
        return ItemDisplayRuntimeConfig.formatItemList(runtimeConfig.whitelistedItems());
    }

    public synchronized String getBlacklistItemsText() {
        return ItemDisplayRuntimeConfig.formatItemList(runtimeConfig.blacklistedItems());
    }

    public synchronized boolean shouldBlock(ProtectionTarget target, String itemId) {
        return runtimeConfig.enabled()
                && runtimeConfig.isProtected(Objects.requireNonNull(target, "target"))
                && !runtimeConfig.canInsert(itemId);
    }

    public synchronized ItemDisplayRuntimeConfig getConfigSnapshot() {
        return runtimeConfig.copy();
    }

    public synchronized void flushPendingConfiguration() {
        flushConfigurationNow();
        awaitPendingSave();
    }

    public synchronized void shutdown() {
        flushPendingConfiguration();
        if (managesExecutor) {
            ioExecutor.shutdown();
        }
    }

    private void loadConfiguration(Path configDirectory) {
        awaitPendingSave();
        pendingSave = CompletableFuture.completedFuture(null);
        configFilePath = configDirectory.resolve("itemdisplaycontrol.properties");
        runtimeConfig = ItemDisplayConfigStorage.load(configFilePath).copy();
        configSaveCooldownTicks = 0;
        configDirty = false;
    }

    private void registerShutdownHookIfNeeded() {
        if (shutdownHookRegistered) {
            return;
        }

        Thread hook = new Thread(() -> {
            try {
                flushPendingConfiguration();
            } catch (RuntimeException exception) {
                LOGGER.debug("Failed to flush pending configuration during JVM shutdown", exception);
            } finally {
                if (managesExecutor) {
                    ioExecutor.shutdown();
                }
            }
        }, Constants.MOD_ID + "-config-flush");

        try {
            Runtime.getRuntime().addShutdownHook(hook);
            shutdownHookRegistered = true;
        } catch (IllegalStateException exception) {
            LOGGER.debug("Skipping shutdown hook registration because JVM is shutting down");
        } catch (SecurityException exception) {
            LOGGER.warn("Unable to register configuration flush shutdown hook", exception);
        }
    }

    private void persistConfigurationAsync() {
        ItemDisplayRuntimeConfig snapshot = runtimeConfig.copy();
        Path filePath = configFilePath;

        pendingSave = pendingSave
                .exceptionally(exception -> {
                    LOGGER.debug("Previous async config save failed", exception);
                    return null;
                })
                .thenRunAsync(() -> ItemDisplayConfigStorage.save(filePath, snapshot), ioExecutor);
    }

    private void markConfigurationDirty() {
        configDirty = true;
        configSaveCooldownTicks = CONFIG_SAVE_DEBOUNCE_TICKS;
    }

    private void flushConfigurationIfDue() {
        if (!configDirty) {
            return;
        }

        if (configSaveCooldownTicks > 0) {
            configSaveCooldownTicks--;
            return;
        }

        persistConfigurationAsync();
        configDirty = false;
    }

    private void flushConfigurationNow() {
        if (configDirty) {
            persistConfigurationAsync();
            configDirty = false;
        }
        configSaveCooldownTicks = 0;
    }

    private void awaitPendingSave() {
        try {
            pendingSave.join();
        } catch (RuntimeException exception) {
            LOGGER.warn("Failed to wait for pending config save completion", exception);
        }
    }
}
