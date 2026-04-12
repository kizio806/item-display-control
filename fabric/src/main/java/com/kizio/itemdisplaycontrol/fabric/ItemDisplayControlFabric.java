package com.kizio.itemdisplaycontrol.fabric;

import com.kizio.itemdisplaycontrol.fabric.client.FabricClientBootstrap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ItemDisplayControlFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricClientBootstrap.initialize();
    }
}
