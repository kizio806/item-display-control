package com.kizio.itemdisplaycontrol.neoforge.client;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.neoforge.client.gui.NeoForgeSettingsScreen;
import com.kizio.itemdisplaycontrol.neoforge.client.input.NeoForgeKeyMappings;
import com.kizio.itemdisplaycontrol.neoforge.client.interaction.NeoForgeInteractionGuards;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@OnlyIn(Dist.CLIENT)
public final class NeoForgeClientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);
    private static boolean initialized;

    private NeoForgeClientBootstrap() {
    }

    public static synchronized void init(IEventBus modEventBus) {
        if (initialized) {
            return;
        }

        LOGGER.info("{} NeoForge client bootstrap starting", Constants.MOD_NAME);
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new NeoForgeSettingsScreen(screen))
        );
        modEventBus.addListener(NeoForgeKeyMappings::onRegisterKeyMappings);
        modEventBus.addListener(NeoForgeClientBootstrap::onClientSetup);
        MinecraftForge.EVENT_BUS.addListener(NeoForgeClientBootstrap::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(NeoForgeClientBootstrap::onClientLogout);
        MinecraftForge.EVENT_BUS.addListener(NeoForgeInteractionGuards::onInteractionKeyMappingTriggered);
        initialized = true;
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Minecraft client = Minecraft.getInstance();
            Path configDir = client != null && client.gameDirectory != null
                    ? client.gameDirectory.toPath().resolve("config")
                    : Path.of("config");

            ItemDisplayControl.init(NeoForgeStatusMessages::sendToggleStatus, configDir);
            LOGGER.info("{} NeoForge config directory: {}", Constants.MOD_NAME, configDir.toAbsolutePath());
        });
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
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
