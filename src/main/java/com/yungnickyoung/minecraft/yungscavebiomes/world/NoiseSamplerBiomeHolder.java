package com.yungnickyoung.minecraft.yungscavebiomes.world;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;

public interface NoiseSamplerBiomeHolder {
    BiomeSource getBiomeSource();

    void setBiomeSource(BiomeSource source);

    void setBiomeRegistry(Registry<Biome> registry);
}
