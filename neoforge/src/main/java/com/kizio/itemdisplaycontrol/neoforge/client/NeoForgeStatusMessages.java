package com.kizio.itemdisplaycontrol.neoforge.client;

import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class NeoForgeStatusMessages {

    private NeoForgeStatusMessages() {
    }

    public static void sendToggleStatus(boolean enabled) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        MutableComponent state = Component.translatable(enabled
                ? TranslationKeys.MESSAGE_ENABLED
                : TranslationKeys.MESSAGE_DISABLED)
                .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);

        client.player.displayClientMessage(Component.translatable(TranslationKeys.MESSAGE_STATUS, state), true);
    }
}
