package com.kizio.itemdisplaycontrol.fabric;

import com.kizio.itemdisplaycontrol.fabric.client.interaction.FabricInteractionGuards;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FabricInteractionGuardsTest {

    @Test
    void shouldSafelyRegisterInteractionGuards() {
        assertDoesNotThrow(FabricInteractionGuards::register);
    }
}
