package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.CaveBiomeRegion;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.RegionType;
import terrablender.api.Regions;

public class TerraBlenderModule {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TerraBlenderModule::commonSetup);
    }

    private static void commonSetup (FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Regions.register(new CaveBiomeRegion(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "overworld"), RegionType.OVERWORLD, 3));
        });
    }
}
