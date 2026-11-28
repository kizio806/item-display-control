package com.kizio.itemdisplaycontrol.common.api;
@FunctionalInterface
public interface ToggleFeedback {

    ToggleFeedback NO_OP = enabled -> {
    };
    void onToggle(boolean enabled);
}
