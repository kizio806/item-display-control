package com.kizio.itemdisplaycontrol.fabric.client;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.fabric.client.gui.FabricSettingsScreen;
import com.kizio.itemdisplaycontrol.fabric.client.input.FabricKeyMappings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public final class FabricEvents {

    private FabricEvents() {
    }

    public static void registerClientEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ItemDisplayControl.onClientTick();
            if (FabricKeyMappings.consumeTogglePress()) {
                ItemDisplayControl.toggleEnabled();
            }
            if (FabricKeyMappings.consumeConfigScreenPress()) {
                Minecraft minecraftClient = Minecraft.getInstance();
                if (minecraftClient != null) {
                    minecraftClient.setScreen(new FabricSettingsScreen(minecraftClient.screen));
                }
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ItemDisplayControl.flushPendingConfiguration());
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> ItemDisplayControl.flushPendingConfiguration());
    }
}
