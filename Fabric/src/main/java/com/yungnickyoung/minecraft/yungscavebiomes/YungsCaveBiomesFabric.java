package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.event.PlayerJoinHandler;
import com.yungnickyoung.minecraft.yungscavebiomes.world.CaveBiomeRegion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class YungsCaveBiomesFabric implements ModInitializer, TerraBlenderApi {
    @Override
    public void onInitialize() {
        YungsCaveBiomesCommon.init();
        ServerEntityEvents.ENTITY_LOAD.register(new PlayerJoinHandler());
    }

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new CaveBiomeRegion(YungsCaveBiomesCommon.id("overworld"), RegionType.OVERWORLD, 3));
    }
}
