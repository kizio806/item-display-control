package com.kizio.itemdisplaycontrol.fabric;

import com.kizio.itemdisplaycontrol.fabric.client.input.FabricKeyMappings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FabricKeyMappingsTest {

    @Test
    void shouldSafelyConsumeUnregisteredKeyMappings() {
        assertFalse(FabricKeyMappings.consumeTogglePress());
        assertFalse(FabricKeyMappings.consumeConfigScreenPress());
    }
}
