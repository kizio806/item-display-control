package com.kizio.itemdisplaycontrol.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Properties;

public final class ItemDisplayConfigStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDisplayConfigStorage.class);

    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_WHITELIST_ENABLED = "whitelist.enabled";
    private static final String KEY_BLACKLIST_ENABLED = "blacklist.enabled";
    private static final String KEY_WHITELIST_ITEMS = "whitelist.items";
    private static final String KEY_BLACKLIST_ITEMS = "blacklist.items";
    private static final String KEY_TARGET_PREFIX = "protect.";

    private ItemDisplayConfigStorage() {
    }
    public static ItemDisplayRuntimeConfig load(Path filePath) {
        Objects.requireNonNull(filePath, "filePath");

        ItemDisplayRuntimeConfig config = ItemDisplayRuntimeConfig.defaults();
        if (!Files.exists(filePath)) {
            return config;
        }

        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(filePath)) {
            properties.load(input);
        } catch (IOException exception) {
            LOGGER.warn("Failed to load config from {}. Falling back to defaults.", filePath, exception);
            return config;
        }

        config.setEnabled(getBoolean(properties, KEY_ENABLED, config.enabled()));
        config.setWhitelistEnabled(getBoolean(properties, KEY_WHITELIST_ENABLED, config.whitelistEnabled()));
        config.setBlacklistEnabled(getBoolean(properties, KEY_BLACKLIST_ENABLED, config.blacklistEnabled()));
        config.setWhitelistedItems(ItemDisplayRuntimeConfig.parseItemList(properties.getProperty(KEY_WHITELIST_ITEMS)));
        config.setBlacklistedItems(ItemDisplayRuntimeConfig.parseItemList(properties.getProperty(KEY_BLACKLIST_ITEMS)));
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            config.setProtected(
                    target,
                    getBoolean(properties, KEY_TARGET_PREFIX + target.propertyKey(), config.isProtected(target))
            );
        }

        return config;
    }
    public static void save(Path filePath, ItemDisplayRuntimeConfig config) {
        Objects.requireNonNull(filePath, "filePath");
        Objects.requireNonNull(config, "config");

        Properties properties = new Properties();
        properties.setProperty(KEY_ENABLED, Boolean.toString(config.enabled()));
        properties.setProperty(KEY_WHITELIST_ENABLED, Boolean.toString(config.whitelistEnabled()));
        properties.setProperty(KEY_BLACKLIST_ENABLED, Boolean.toString(config.blacklistEnabled()));
        properties.setProperty(KEY_WHITELIST_ITEMS, ItemDisplayRuntimeConfig.formatItemList(config.whitelistedItems()));
        properties.setProperty(KEY_BLACKLIST_ITEMS, ItemDisplayRuntimeConfig.formatItemList(config.blacklistedItems()));
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            properties.setProperty(
                    KEY_TARGET_PREFIX + target.propertyKey(),
                    Boolean.toString(config.isProtected(target))
            );
        }

        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            Path targetDirectory = parent != null ? parent : Path.of(".");
            Path temporaryFile = Files.createTempFile(targetDirectory, "itemdisplaycontrol-", ".properties.tmp");
            try (OutputStream output = Files.newOutputStream(temporaryFile)) {
                properties.store(output, "ItemDisplayControl configuration");
                try {
                    Files.move(
                            temporaryFile,
                            filePath,
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.ATOMIC_MOVE
                    );
                } catch (AtomicMoveNotSupportedException ignored) {
                    Files.move(temporaryFile, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally {
                Files.deleteIfExists(temporaryFile);
            }
        } catch (IOException exception) {
            LOGGER.warn("Failed to persist config to {}. Runtime state remains active.", filePath, exception);
        }
    }

    private static boolean getBoolean(Properties properties, String key, boolean fallback) {
        String value = properties.getProperty(key);
        return value == null ? fallback : Boolean.parseBoolean(value);
    }
}
