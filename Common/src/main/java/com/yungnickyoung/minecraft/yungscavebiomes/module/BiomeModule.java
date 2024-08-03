package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBiome;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.FrostedCavesBiomeMaker;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.LostCavesBiomeMaker;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.MarbleCavesBiomeMaker;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class BiomeModule {
    @AutoRegister("frosted_caves")
    public static final AutoRegisterBiome FROSTED_CAVES = AutoRegisterBiome.of(() -> new FrostedCavesBiomeMaker().make());

    @AutoRegister("marble_caves")
    public static final AutoRegisterBiome MARBLE_CAVES = AutoRegisterBiome.of(() -> new MarbleCavesBiomeMaker().make());

    @AutoRegister("lost_caves")
    public static final AutoRegisterBiome LOST_CAVES = AutoRegisterBiome.of(() -> new LostCavesBiomeMaker().make());
}
