package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBiome;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.BiomeMaker;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class BiomeModule {
    @AutoRegister("frosted_caves")
    public static AutoRegisterBiome FROSTED_CAVES = AutoRegisterBiome.of(BiomeMaker::frostedCaves);

    @AutoRegister("marble_caves")
    public static AutoRegisterBiome MARBLE_CAVES = AutoRegisterBiome.of(BiomeMaker::marbleCaves);

    @AutoRegister("lost_caves")
    public static AutoRegisterBiome LOST_CAVES = AutoRegisterBiome.of(BiomeMaker::lostCaves);

}
