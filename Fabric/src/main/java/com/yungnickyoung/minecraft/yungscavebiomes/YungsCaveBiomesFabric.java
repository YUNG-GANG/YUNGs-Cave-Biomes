package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.world.CaveBiomeRegion;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class YungsCaveBiomesFabric implements ModInitializer, TerraBlenderApi {
    @Override
    public void onInitialize() {
        YungsCaveBiomesCommon.init();
    }

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new CaveBiomeRegion(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "overworld"), RegionType.OVERWORLD, 3));
    }
}
