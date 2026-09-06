package com.kizio.itemdisplaycontrol.fabric.client;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.fabric.client.input.FabricKeyMappings;
import com.kizio.itemdisplaycontrol.fabric.client.interaction.FabricInteractionGuards;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public final class FabricClientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);
    private static boolean initialized;

    private FabricClientBootstrap() {
    }

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        LOGGER.info("{} Fabric client bootstrap starting", Constants.MOD_NAME);
        FabricKeyMappings.register();
        FabricInteractionGuards.register();

        Minecraft client = Minecraft.getInstance();
        Path configDir = client != null && client.gameDirectory != null
                ? client.gameDirectory.toPath().resolve("config")
                : Path.of("config");

        ItemDisplayControl.init(FabricStatusMessages::sendToggleStatus, configDir);
        LOGGER.info("{} Fabric config directory: {}", Constants.MOD_NAME, configDir.toAbsolutePath());
        FabricEvents.registerClientEvents();
        initialized = true;
    }
}
