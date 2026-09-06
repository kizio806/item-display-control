package com.kizio.itemdisplaycontrol.common;

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
public final class ItemDisplayControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);
    private static final int CONFIG_SAVE_DEBOUNCE_TICKS = 10;
    private static final ExecutorService CONFIG_IO_EXECUTOR = Executors.newSingleThreadExecutor(task -> {
        Thread thread = new Thread(task, Constants.MOD_ID + "-config-io");
        thread.setDaemon(true);
        return thread;
    });

    private static ToggleFeedback toggleFeedback = ToggleFeedback.NO_OP;
    private static boolean initialized;
    private static boolean shutdownHookRegistered;

    private static ItemDisplayRuntimeConfig runtimeConfig = ItemDisplayRuntimeConfig.defaults();
    private static Path configFilePath = Path.of("config", "itemdisplaycontrol.properties");
    private static int configSaveCooldownTicks;
    private static boolean configDirty;
    private static CompletableFuture<Void> pendingSave = CompletableFuture.completedFuture(null);

    private ItemDisplayControl() {
    }

    public static synchronized void init(ToggleFeedback feedback) {
        init(feedback, Path.of("config"));
    }

    public static synchronized void init(ToggleFeedback feedback, Path configDirectory) {
        toggleFeedback = Objects.requireNonNull(feedback, "feedback");
        registerShutdownHookIfNeeded();
        loadConfiguration(Objects.requireNonNull(configDirectory, "configDirectory"));

        initialized = true;
        LOGGER.info("{} initialized", Constants.MOD_NAME);
    }
    public static synchronized void onClientTick() {
        if (!initialized) {
            return;
        }

        flushConfigurationIfDue();
    }

    public static synchronized boolean toggleEnabled() {
        boolean enabled = !runtimeConfig.enabled();
        runtimeConfig.setEnabled(enabled);
        markConfigurationDirty();
        toggleFeedback.onToggle(enabled);
        LOGGER.debug("{} toggled {}", Constants.MOD_NAME, enabled ? "on" : "off");
        return enabled;
    }

    public static synchronized boolean toggleProtection(ProtectionTarget target) {
        Objects.requireNonNull(target, "target");
        boolean enabled = !runtimeConfig.isProtected(target);
        runtimeConfig.setProtected(target, enabled);
        markConfigurationDirty();
        return enabled;
    }

    public static synchronized boolean toggleWhitelistEnabled() {
        boolean enabled = !runtimeConfig.whitelistEnabled();
        runtimeConfig.setWhitelistEnabled(enabled);
        markConfigurationDirty();
        return enabled;
    }

    public static synchronized boolean toggleBlacklistEnabled() {
        boolean enabled = !runtimeConfig.blacklistEnabled();
        runtimeConfig.setBlacklistEnabled(enabled);
        markConfigurationDirty();
        return enabled;
    }

    public static synchronized void setWhitelistItemsFromText(String rawValue) {
        LinkedHashSet<String> normalizedItems = new LinkedHashSet<>(ItemDisplayRuntimeConfig.parseItemList(rawValue));
        if (runtimeConfig.whitelistedItems().equals(normalizedItems)) {
            return;
        }

        runtimeConfig.setWhitelistedItems(normalizedItems);
        markConfigurationDirty();
    }

    public static synchronized void setBlacklistItemsFromText(String rawValue) {
        LinkedHashSet<String> normalizedItems = new LinkedHashSet<>(ItemDisplayRuntimeConfig.parseItemList(rawValue));
        if (runtimeConfig.blacklistedItems().equals(normalizedItems)) {
            return;
        }

        runtimeConfig.setBlacklistedItems(normalizedItems);
        markConfigurationDirty();
    }

    public static synchronized void setProtection(ProtectionTarget target, boolean enabled) {
        Objects.requireNonNull(target, "target");
        if (runtimeConfig.isProtected(target) == enabled) {
            return;
        }

        runtimeConfig.setProtected(target, enabled);
        markConfigurationDirty();
    }

    public static synchronized boolean isEnabled() {
        return runtimeConfig.enabled();
    }

    public static synchronized boolean isProtectionEnabled(ProtectionTarget target) {
        return runtimeConfig.isProtected(Objects.requireNonNull(target, "target"));
    }

    public static synchronized boolean isWhitelistEnabled() {
        return runtimeConfig.whitelistEnabled();
    }

    public static synchronized boolean isBlacklistEnabled() {
        return runtimeConfig.blacklistEnabled();
    }

    public static synchronized String getWhitelistItemsText() {
        return ItemDisplayRuntimeConfig.formatItemList(runtimeConfig.whitelistedItems());
    }

    public static synchronized String getBlacklistItemsText() {
        return ItemDisplayRuntimeConfig.formatItemList(runtimeConfig.blacklistedItems());
    }

    public static synchronized boolean shouldBlock(ProtectionTarget target, String itemId) {
        return runtimeConfig.enabled()
                && runtimeConfig.isProtected(Objects.requireNonNull(target, "target"))
                && !runtimeConfig.canInsert(itemId);
    }

    public static synchronized ItemDisplayRuntimeConfig getConfigSnapshot() {
        return runtimeConfig.copy();
    }
    public static synchronized void flushPendingConfiguration() {
        flushConfigurationNow();
        awaitPendingSave();
    }

    private static void loadConfiguration(Path configDirectory) {
        awaitPendingSave();
        pendingSave = CompletableFuture.completedFuture(null);
        configFilePath = configDirectory.resolve("itemdisplaycontrol.properties");
        runtimeConfig = ItemDisplayConfigStorage.load(configFilePath).copy();
        configSaveCooldownTicks = 0;
        configDirty = false;
    }

    private static void registerShutdownHookIfNeeded() {
        if (shutdownHookRegistered) {
            return;
        }

        Thread hook = new Thread(() -> {
            try {
                flushPendingConfiguration();
            } catch (RuntimeException exception) {
                LOGGER.debug("Failed to flush pending configuration during JVM shutdown", exception);
            } finally {
                CONFIG_IO_EXECUTOR.shutdown();
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

    private static void persistConfigurationAsync() {
        ItemDisplayRuntimeConfig snapshot = runtimeConfig.copy();
        Path filePath = configFilePath;

        pendingSave = pendingSave
                .exceptionally(exception -> {
                    LOGGER.debug("Previous async config save failed", exception);
                    return null;
                })
                .thenRunAsync(() -> ItemDisplayConfigStorage.save(filePath, snapshot), CONFIG_IO_EXECUTOR);
    }

    private static void markConfigurationDirty() {
        configDirty = true;
        configSaveCooldownTicks = CONFIG_SAVE_DEBOUNCE_TICKS;
    }

    private static void flushConfigurationIfDue() {
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

    private static void flushConfigurationNow() {
        if (configDirty) {
            persistConfigurationAsync();
            configDirty = false;
        }
        configSaveCooldownTicks = 0;
    }

    private static void awaitPendingSave() {
        try {
            pendingSave.join();
        } catch (RuntimeException exception) {
            LOGGER.warn("Failed to wait for pending config save completion", exception);
        }
    }
}
