package com.kizio.itemdisplaycontrol.fabric.client;

import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;

@Environment(EnvType.CLIENT)
public final class FabricStatusMessages {

    private FabricStatusMessages() {
    }

    public static void sendToggleStatus(boolean enabled) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        MutableComponent stateText = Component.translatable(enabled
                ? TranslationKeys.MESSAGE_ENABLED
                : TranslationKeys.MESSAGE_DISABLED)
                .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);

        client.player.sendOverlayMessage(Component.translatable(TranslationKeys.MESSAGE_STATUS, stateText));
    }
}
