package com.kizio.itemdisplaycontrol.common;

import com.kizio.itemdisplaycontrol.common.api.ToggleFeedback;
import com.kizio.itemdisplaycontrol.common.config.ItemDisplayRuntimeConfig;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.engine.ItemDisplayControlEngine;

import java.nio.file.Path;

/**
 * Public facade and global client entrypoint for Item Display Control.
 * <p>
 * Delegates operations to the default {@link ItemDisplayControlEngine} instance.
 * Thread-safe across all methods.
 */
public final class ItemDisplayControl {

    private static final ItemDisplayControlEngine DEFAULT_ENGINE = new ItemDisplayControlEngine();

    private ItemDisplayControl() {
    }

    /**
     * Returns the singleton default engine instance.
     *
     * @return the default engine.
     */
    public static ItemDisplayControlEngine defaultEngine() {
        return DEFAULT_ENGINE;
    }

    /**
     * Initializes the default engine with the given feedback callback and standard config directory.
     *
     * @param feedback callback for toggle HUD notifications.
     */
    public static synchronized void init(ToggleFeedback feedback) {
        DEFAULT_ENGINE.init(feedback);
    }

    /**
     * Initializes the default engine with the given feedback callback and custom config directory.
     *
     * @param feedback callback for toggle HUD notifications.
     * @param configDirectory path to the directory containing configuration.
     */
    public static synchronized void init(ToggleFeedback feedback, Path configDirectory) {
        DEFAULT_ENGINE.init(feedback, configDirectory);
    }

    /**
     * Ticks the engine on each client tick to handle deferred debounced persistence.
     */
    public static synchronized void onClientTick() {
        DEFAULT_ENGINE.onClientTick();
    }

    /**
     * Toggles the master enabled state of the mod.
     *
     * @return the new enabled state.
     */
    public static synchronized boolean toggleEnabled() {
        return DEFAULT_ENGINE.toggleEnabled();
    }

    /**
     * Toggles protection for a specific target block or entity.
     *
     * @param target the target to toggle.
     * @return the new protection state for the target.
     */
    public static synchronized boolean toggleProtection(ProtectionTarget target) {
        return DEFAULT_ENGINE.toggleProtection(target);
    }

    /**
     * Toggles the item whitelist filter active state.
     *
     * @return the new whitelist state.
     */
    public static synchronized boolean toggleWhitelistEnabled() {
        return DEFAULT_ENGINE.toggleWhitelistEnabled();
    }

    /**
     * Toggles the item blacklist filter active state.
     *
     * @return the new blacklist state.
     */
    public static synchronized boolean toggleBlacklistEnabled() {
        return DEFAULT_ENGINE.toggleBlacklistEnabled();
    }

    /**
     * Parses and updates the whitelisted item identifiers from delimited text.
     *
     * @param rawValue comma-, space-, or semicolon-delimited item list.
     */
    public static synchronized void setWhitelistItemsFromText(String rawValue) {
        DEFAULT_ENGINE.setWhitelistItemsFromText(rawValue);
    }

    /**
     * Parses and updates the blacklisted item identifiers from delimited text.
     *
     * @param rawValue comma-, space-, or semicolon-delimited item list.
     */
    public static synchronized void setBlacklistItemsFromText(String rawValue) {
        DEFAULT_ENGINE.setBlacklistItemsFromText(rawValue);
    }

    /**
     * Sets protection state for the specified target.
     *
     * @param target the target to configure.
     * @param enabled {@code true} to enable protection, {@code false} to disable.
     */
    public static synchronized void setProtection(ProtectionTarget target, boolean enabled) {
        DEFAULT_ENGINE.setProtection(target, enabled);
    }

    /**
     * Returns whether master protection is enabled.
     *
     * @return {@code true} if enabled.
     */
    public static synchronized boolean isEnabled() {
        return DEFAULT_ENGINE.isEnabled();
    }

    /**
     * Returns whether protection is enabled for a given target.
     *
     * @param target the target to query.
     * @return {@code true} if protected.
     */
    public static synchronized boolean isProtectionEnabled(ProtectionTarget target) {
        return DEFAULT_ENGINE.isProtectionEnabled(target);
    }

    /**
     * Returns whether the item whitelist is active.
     *
     * @return {@code true} if whitelist is active.
     */
    public static synchronized boolean isWhitelistEnabled() {
        return DEFAULT_ENGINE.isWhitelistEnabled();
    }

    /**
     * Returns whether the item blacklist is active.
     *
     * @return {@code true} if blacklist is active.
     */
    public static synchronized boolean isBlacklistEnabled() {
        return DEFAULT_ENGINE.isBlacklistEnabled();
    }

    /**
     * Returns the formatted string representation of all whitelisted items.
     *
     * @return comma-separated items text.
     */
    public static synchronized String getWhitelistItemsText() {
        return DEFAULT_ENGINE.getWhitelistItemsText();
    }

    /**
     * Returns the formatted string representation of all blacklisted items.
     *
     * @return comma-separated items text.
     */
    public static synchronized String getBlacklistItemsText() {
        return DEFAULT_ENGINE.getBlacklistItemsText();
    }

    /**
     * Evaluates whether an interaction targeting a block or entity with a held item should be blocked.
     *
     * @param target the resolved protection target.
     * @param itemId the normalized item identifier.
     * @return {@code true} if the interaction should be blocked.
     */
    public static synchronized boolean shouldBlock(ProtectionTarget target, String itemId) {
        return DEFAULT_ENGINE.shouldBlock(target, itemId);
    }

    /**
     * Returns an immutable snapshot copy of the current runtime configuration.
     *
     * @return configuration snapshot.
     */
    public static synchronized ItemDisplayRuntimeConfig getConfigSnapshot() {
        return DEFAULT_ENGINE.getConfigSnapshot();
    }

    /**
     * Flushes any pending debounced configuration changes to disk synchronously.
     */
    public static synchronized void flushPendingConfiguration() {
        DEFAULT_ENGINE.flushPendingConfiguration();
    }
}
