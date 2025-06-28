package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkMixin implements NoiseSamplerBiomeHolder {

    // NoiseSamplerBiomeHolder impl
    @Unique
    private BiomeSource ycbBiomeSource;
    @Unique
    private Registry<Biome> ycbBiomeRegistry;
    @Unique
    private Climate.Sampler ycbClimateSampler;
    @Unique
    private long ycbWorldSeed;

    @Override
    @Unique
    public BiomeSource getBiomeSource() {
        return this.ycbBiomeSource;
    }

    @Override
    @Unique
    public Registry<Biome> getBiomeRegistry() {
        return this.ycbBiomeRegistry;
    }

    @Override
    @Unique
    public void setBiomeSource(BiomeSource source) {
        this.ycbBiomeSource = source;
    }

    @Override
    @Unique
    public void setBiomeRegistry(Registry<Biome> registry) {
        this.ycbBiomeRegistry = registry;
    }

    @Override
    @Unique
    public Climate.Sampler getClimateSampler() {
        return this.ycbClimateSampler;
    }

    @Override
    @Unique
    public void setClimateSampler(Climate.Sampler sampler) {
        this.ycbClimateSampler = sampler;
    }

    @Override
    @Unique
    public long getWorldSeed() {
        return ycbWorldSeed;
    }

    @Override
    @Unique
    public void setWorldSeed(long worldSeed) {
        this.ycbWorldSeed = worldSeed;
    }
}
