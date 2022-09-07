package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.BiomeMaker;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeModuleFabric {
    public static Biome FROSTED_CAVES = BiomeMaker.frostedCaves();
    public static Biome MARBLE_CAVES = BiomeMaker.marbleCaves();
    public static Biome ANCIENT_CAVES = BiomeMaker.ancientCaves();


    public static void init() {
        FROSTED_CAVES = Registry.register(BuiltinRegistries.BIOME, new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "frosted_caves"), BiomeMaker.frostedCaves());
        MARBLE_CAVES = Registry.register(BuiltinRegistries.BIOME, new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "marble_caves"), BiomeMaker.marbleCaves());
        ANCIENT_CAVES = Registry.register(BuiltinRegistries.BIOME, new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ancient_caves"), BiomeMaker.ancientCaves());
    }
}
