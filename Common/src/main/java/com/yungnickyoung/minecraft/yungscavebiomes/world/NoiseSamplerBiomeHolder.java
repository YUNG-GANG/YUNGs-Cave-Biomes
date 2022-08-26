package com.yungnickyoung.minecraft.yungscavebiomes.world;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

public interface NoiseSamplerBiomeHolder extends Climate.Sampler {
    BiomeSource getBiomeSource();
    Registry<Biome> getBiomeRegistry();

    void setBiomeSource(BiomeSource source);

    void setBiomeRegistry(Registry<Biome> registry);
}
