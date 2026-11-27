package com.kizio.itemdisplaycontrol.fabric;

import com.kizio.itemdisplaycontrol.fabric.client.FabricStatusMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FabricStatusMessagesTest {

    @Test
    void shouldSafelyHandleToggleStatusWithoutClient() {
        assertDoesNotThrow(() -> FabricStatusMessages.sendToggleStatus(true));
        assertDoesNotThrow(() -> FabricStatusMessages.sendToggleStatus(false));
    }
}
