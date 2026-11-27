package com.kizio.itemdisplaycontrol.neoforge;

import com.kizio.itemdisplaycontrol.neoforge.client.NeoForgeStatusMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NeoForgeStatusMessagesTest {

    @Test
    void shouldSafelyHandleToggleStatusWithoutClient() {
        assertDoesNotThrow(() -> NeoForgeStatusMessages.sendToggleStatus(true));
        assertDoesNotThrow(() -> NeoForgeStatusMessages.sendToggleStatus(false));
    }
}
