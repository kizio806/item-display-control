package com.kizio.itemdisplaycontrol.neoforge.client;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.ModConstants;
import com.kizio.itemdisplaycontrol.neoforge.client.gui.NeoForgeSettingsScreen;
import com.kizio.itemdisplaycontrol.neoforge.client.input.NeoForgeKeyMappings;
import com.kizio.itemdisplaycontrol.neoforge.client.interaction.NeoForgeInteractionGuards;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@OnlyIn(Dist.CLIENT)
public final class NeoForgeClientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);
    private static boolean initialized;

    private NeoForgeClientBootstrap() {
    }

    public static synchronized void init(IEventBus modEventBus) {
        if (initialized) {
            return;
        }

        LOGGER.info("{} NeoForge client bootstrap starting", ModConstants.MOD_NAME);
        modEventBus.addListener(NeoForgeKeyMappings::onRegisterKeyMappings);
        modEventBus.addListener(NeoForgeClientBootstrap::onClientSetup);
        NeoForge.EVENT_BUS.addListener(NeoForgeClientBootstrap::onClientTick);
        NeoForge.EVENT_BUS.addListener(NeoForgeClientBootstrap::onClientLogout);
        NeoForge.EVENT_BUS.addListener(NeoForgeInteractionGuards::onInteractionKeyMappingTriggered);
        initialized = true;
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Minecraft client = Minecraft.getInstance();
            Path configDir = client != null && client.gameDirectory != null
                    ? client.gameDirectory.toPath().resolve("config")
                    : Path.of("config");

            ItemDisplayControl.init(NeoForgeStatusMessages::sendToggleStatus, configDir);
            LOGGER.info("{} NeoForge config directory: {}", ModConstants.MOD_NAME, configDir.toAbsolutePath());
        });
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        ItemDisplayControl.onClientTick();
        if (NeoForgeKeyMappings.consumeTogglePress()) {
            ItemDisplayControl.toggleEnabled();
        }
        if (NeoForgeKeyMappings.consumeConfigScreenPress()) {
            Minecraft client = Minecraft.getInstance();
            if (client != null) {
                client.setScreen(new NeoForgeSettingsScreen(client.screen));
            }
        }
    }

    private static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ItemDisplayControl.flushPendingConfiguration();
    }
}
