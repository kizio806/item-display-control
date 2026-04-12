package com.kizio.itemdisplaycontrol.fabric.client;

import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public final class FabricStatusMessages {

    private FabricStatusMessages() {
    }

    public static void sendToggleStatus(boolean enabled) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        MutableText stateText = Text.translatable(enabled
                ? TranslationKeys.MESSAGE_ENABLED
                : TranslationKeys.MESSAGE_DISABLED)
                .formatted(enabled ? Formatting.GREEN : Formatting.RED);

        client.player.sendMessage(Text.translatable(TranslationKeys.MESSAGE_STATUS, stateText), true);
    }
}
