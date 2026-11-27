package com.kizio.itemdisplaycontrol.neoforge;

import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.neoforge.client.NeoForgeClientBootstrap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.Objects;

@Mod(Constants.MOD_ID)
public final class ItemDisplayControlNeoForge {

    public ItemDisplayControlNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        IEventBus bus = Objects.requireNonNull(modEventBus, "modEventBus");
        Objects.requireNonNull(modContainer, "modContainer");

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            NeoForgeClientBootstrap.init(bus);
        }
    }
}
