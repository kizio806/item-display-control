package com.kizio.itemdisplaycontrol.client;

import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * Shared client status overlay notifications for Item Display Control.
 * <p>
 * Displays non-intrusive actionbar alerts when toggling master protection state.
 */
public final class StatusMessages {

    private StatusMessages() {
    }

    /**
     * Sends an actionbar overlay message to the local player reflecting the current mod state.
     *
     * @param enabled {@code true} if protection is active, {@code false} otherwise.
     */
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
