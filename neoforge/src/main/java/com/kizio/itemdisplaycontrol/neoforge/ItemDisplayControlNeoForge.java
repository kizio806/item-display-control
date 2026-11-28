package com.kizio.itemdisplaycontrol.neoforge;

import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.neoforge.client.NeoForgeClientBootstrap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public final class ItemDisplayControlNeoForge {

    public ItemDisplayControlNeoForge() {
        this(FMLJavaModLoadingContext.get());
    }

    public ItemDisplayControlNeoForge(FMLJavaModLoadingContext context) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            IEventBus bus = (context != null) ? context.getModEventBus() : FMLJavaModLoadingContext.get().getModEventBus();
            NeoForgeClientBootstrap.init(bus);
        }
    }
}
