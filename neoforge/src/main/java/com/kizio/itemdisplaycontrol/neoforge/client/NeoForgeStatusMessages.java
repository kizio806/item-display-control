package com.kizio.itemdisplaycontrol.neoforge.client;

import com.kizio.itemdisplaycontrol.client.StatusMessages;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * NeoForge client wrapper for actionbar status overlay notifications.
 */
@OnlyIn(Dist.CLIENT)
public final class NeoForgeStatusMessages {

    private NeoForgeStatusMessages() {
    }

    public static void sendToggleStatus(boolean enabled) {
        StatusMessages.sendToggleStatus(enabled);
    }
}
