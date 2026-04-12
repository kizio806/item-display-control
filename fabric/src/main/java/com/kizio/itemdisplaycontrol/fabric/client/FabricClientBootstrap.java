package com.kizio.itemdisplaycontrol.fabric.client;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.ModConstants;
import com.kizio.itemdisplaycontrol.fabric.client.input.FabricKeyMappings;
import com.kizio.itemdisplaycontrol.fabric.client.interaction.FabricInteractionGuards;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public final class FabricClientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);
    private static boolean initialized;

    private FabricClientBootstrap() {
    }

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        LOGGER.info("{} Fabric client bootstrap starting", ModConstants.MOD_NAME);
        FabricKeyMappings.register();
        FabricInteractionGuards.register();

        MinecraftClient client = MinecraftClient.getInstance();
        Path configDir = client != null && client.runDirectory != null
                ? client.runDirectory.toPath().resolve("config")
                : Path.of("config");

        ItemDisplayControl.init(FabricStatusMessages::sendToggleStatus, configDir);
        LOGGER.info("{} Fabric config directory: {}", ModConstants.MOD_NAME, configDir.toAbsolutePath());
        FabricEvents.registerClientEvents();
        initialized = true;
    }
}
