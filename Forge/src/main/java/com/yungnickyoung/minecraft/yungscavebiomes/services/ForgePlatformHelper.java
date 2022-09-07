package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModuleForge;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public ResourceKey<Biome> getMarbleCavesResourceKey() {
        return ForgeRegistries.BIOMES.getResourceKey(BiomeModuleForge.MARBLE_CAVES.get()).get();
    }

    @Override
    public ResourceKey<Biome> getFrostedCavesResourceKey() {
        return ForgeRegistries.BIOMES.getResourceKey(BiomeModuleForge.FROSTED_CAVES.get()).get();
    }

    @Override
    public ResourceKey<Biome> getAncientCavesResourceKey() {
        return ForgeRegistries.BIOMES.getResourceKey(BiomeModuleForge.ANCIENT_CAVES.get()).get();
    }
}
