package com.kizio.itemdisplaycontrol.common;

import com.kizio.itemdisplaycontrol.common.api.ToggleFeedback;
import com.kizio.itemdisplaycontrol.common.config.ItemDisplayRuntimeConfig;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.engine.ItemDisplayControlEngine;

import java.nio.file.Path;

public final class ItemDisplayControl {

    private static final ItemDisplayControlEngine DEFAULT_ENGINE = new ItemDisplayControlEngine();

    private ItemDisplayControl() {
    }

    public static ItemDisplayControlEngine defaultEngine() {
        return DEFAULT_ENGINE;
    }

    public static synchronized void init(ToggleFeedback feedback) {
        DEFAULT_ENGINE.init(feedback);
    }

    public static synchronized void init(ToggleFeedback feedback, Path configDirectory) {
        DEFAULT_ENGINE.init(feedback, configDirectory);
    }

    public static synchronized void onClientTick() {
        DEFAULT_ENGINE.onClientTick();
    }

    public static synchronized boolean toggleEnabled() {
        return DEFAULT_ENGINE.toggleEnabled();
    }

    public static synchronized boolean toggleProtection(ProtectionTarget target) {
        return DEFAULT_ENGINE.toggleProtection(target);
    }

    public static synchronized boolean toggleWhitelistEnabled() {
        return DEFAULT_ENGINE.toggleWhitelistEnabled();
    }

    public static synchronized boolean toggleBlacklistEnabled() {
        return DEFAULT_ENGINE.toggleBlacklistEnabled();
    }

    public static synchronized void setWhitelistItemsFromText(String rawValue) {
        DEFAULT_ENGINE.setWhitelistItemsFromText(rawValue);
    }

    public static synchronized void setBlacklistItemsFromText(String rawValue) {
        DEFAULT_ENGINE.setBlacklistItemsFromText(rawValue);
    }

    public static synchronized void setProtection(ProtectionTarget target, boolean enabled) {
        DEFAULT_ENGINE.setProtection(target, enabled);
    }

    public static synchronized boolean isEnabled() {
        return DEFAULT_ENGINE.isEnabled();
    }

    public static synchronized boolean isProtectionEnabled(ProtectionTarget target) {
        return DEFAULT_ENGINE.isProtectionEnabled(target);
    }

    public static synchronized boolean isWhitelistEnabled() {
        return DEFAULT_ENGINE.isWhitelistEnabled();
    }

    public static synchronized boolean isBlacklistEnabled() {
        return DEFAULT_ENGINE.isBlacklistEnabled();
    }

    public static synchronized String getWhitelistItemsText() {
        return DEFAULT_ENGINE.getWhitelistItemsText();
    }

    public static synchronized String getBlacklistItemsText() {
        return DEFAULT_ENGINE.getBlacklistItemsText();
    }

    public static synchronized boolean shouldBlock(ProtectionTarget target, String itemId) {
        return DEFAULT_ENGINE.shouldBlock(target, itemId);
    }

    public static synchronized ItemDisplayRuntimeConfig getConfigSnapshot() {
        return DEFAULT_ENGINE.getConfigSnapshot();
    }

    public static synchronized void flushPendingConfiguration() {
        DEFAULT_ENGINE.flushPendingConfiguration();
    }
}
