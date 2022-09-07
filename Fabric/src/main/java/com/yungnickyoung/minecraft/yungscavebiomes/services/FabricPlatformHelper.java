package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public ResourceKey<Biome> getMarbleCavesResourceKey() {
        return BuiltinRegistries.BIOME.getResourceKey(BuiltinRegistries.BIOME.get(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "marble_caves"))).get();
    }

    @Override
    public ResourceKey<Biome> getFrostedCavesResourceKey() {
        return BuiltinRegistries.BIOME.getResourceKey(BuiltinRegistries.BIOME.get(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "frosted_caves"))).get();
    }

    @Override
    public ResourceKey<Biome> getAncientCavesResourceKey() {
        return BuiltinRegistries.BIOME.getResourceKey(BuiltinRegistries.BIOME.get(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ancient_caves"))).get();
    }
}
