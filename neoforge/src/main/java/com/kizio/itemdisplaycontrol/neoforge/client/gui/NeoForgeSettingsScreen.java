package com.kizio.itemdisplaycontrol.neoforge.client.gui;

import com.kizio.itemdisplaycontrol.client.gui.SettingsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * NeoForge client wrapper for {@link SettingsScreen}.
 */
@OnlyIn(Dist.CLIENT)
public final class NeoForgeSettingsScreen extends SettingsScreen {

    public NeoForgeSettingsScreen(Screen parent) {
        super(parent);
    }
}
