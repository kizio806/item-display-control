package com.kizio.itemdisplaycontrol.common.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ToggleFeedbackTest {

    @Test
    void shouldExecuteNoOpFeedbackWithoutException() {
        assertDoesNotThrow(() -> ToggleFeedback.NO_OP.onToggle(true));
        assertDoesNotThrow(() -> ToggleFeedback.NO_OP.onToggle(false));
    }
}
