package com.kizio.itemdisplaycontrol.common.api;
/**
 * Callback interface invoked when the mod's protection state is toggled.
 */
@FunctionalInterface
public interface ToggleFeedback {

    ToggleFeedback NO_OP = enabled -> {
    };

    /**
     * Invoked when the mod is toggled on or off.
     *
     * @param enabled {@code true} if enabled, {@code false} if disabled.
     */
    void onToggle(boolean enabled);
}
