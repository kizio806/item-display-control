package com.kizio.itemdisplaycontrol.fabric.client;

import com.kizio.itemdisplaycontrol.client.StatusMessages;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Fabric client wrapper for actionbar status overlay notifications.
 */
@Environment(EnvType.CLIENT)
public final class FabricStatusMessages {

    private FabricStatusMessages() {
    }

    public static void sendToggleStatus(boolean enabled) {
        StatusMessages.sendToggleStatus(enabled);
    }
}
