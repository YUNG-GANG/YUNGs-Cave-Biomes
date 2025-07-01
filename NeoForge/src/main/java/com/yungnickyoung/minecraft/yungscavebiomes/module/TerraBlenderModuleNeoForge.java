package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.CaveBiomeRegion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import terrablender.api.RegionType;
import terrablender.api.Regions;

public class TerraBlenderModuleNeoForge {
    public static void init(IEventBus eventBus) {
        eventBus.addListener(TerraBlenderModuleNeoForge::commonSetup);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Regions.register(new CaveBiomeRegion(YungsCaveBiomesCommon.id("overworld"), RegionType.OVERWORLD, 3));
        });
    }
}
