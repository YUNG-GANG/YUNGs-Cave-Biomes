package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.BiomeMaker;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeModule {
    public static ResourceKey<Biome> ICE_CAVES = createKey("ice_caves");
    public static ResourceKey<Biome> MARBLE_CAVES = createKey("marble_caves");
    public static ResourceKey<Biome> DESERT_CAVES = createKey("desert_caves");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, name));
    }

    public static void init() {
        Services.REGISTRY.registerBiome(ICE_CAVES, BiomeMaker.iceCaves());
        Services.REGISTRY.registerBiome(MARBLE_CAVES, BiomeMaker.marbleCaves());
        Services.REGISTRY.registerBiome(DESERT_CAVES, BiomeMaker.ancientCaves());
    }
}
