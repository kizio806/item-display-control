package com.kizio.itemdisplaycontrol.fabric.client.gui;

import com.kizio.itemdisplaycontrol.client.gui.SettingsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;

/**
 * Fabric client wrapper for {@link SettingsScreen}.
 */
@Environment(EnvType.CLIENT)
public final class FabricSettingsScreen extends SettingsScreen {

    public FabricSettingsScreen(Screen parent) {
        super(parent);
    }
}
