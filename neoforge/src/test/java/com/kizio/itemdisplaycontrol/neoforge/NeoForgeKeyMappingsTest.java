package com.kizio.itemdisplaycontrol.neoforge;

import com.kizio.itemdisplaycontrol.neoforge.client.input.NeoForgeKeyMappings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class NeoForgeKeyMappingsTest {

    @Test
    void shouldSafelyHandleKeyMappingsWithoutActivePress() {
        assertFalse(NeoForgeKeyMappings.consumeTogglePress());
        assertFalse(NeoForgeKeyMappings.consumeConfigScreenPress());
    }
}
