package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.MarbleCavesInterpolationSlideDensityFunction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseChunk.class)
public abstract class MixinNoiseChunk implements NoiseSamplerBiomeHolder {

    // NoiseSamplerBiomeHolder impl
    private BiomeSource ycbBiomeSource;
    private Registry<Biome> ycbBiomeRegistry;
    private Climate.Sampler ycbClimateSampler;
    private long ycbWorldSeed;

    @Override
    public BiomeSource getBiomeSource() {
        return this.ycbBiomeSource;
    }

    @Override
    public Registry<Biome> getBiomeRegistry() {
        return this.ycbBiomeRegistry;
    }

    @Override
    public void setBiomeSource(BiomeSource source) {
        this.ycbBiomeSource = source;
    }

    @Override
    public void setBiomeRegistry(Registry<Biome> registry) {
        this.ycbBiomeRegistry = registry;
    }

    @Override
    public Climate.Sampler getClimateSampler() {
        return this.ycbClimateSampler;
    }

    @Override
    public void setClimateSampler(Climate.Sampler sampler) {
        this.ycbClimateSampler = sampler;
    }

    @Override
    public long getWorldSeed() {
        return ycbWorldSeed;
    }

    @Override
    public void setWorldSeed(long worldSeed) {
        this.ycbWorldSeed = worldSeed;
    }

    // Make marble caves interpolate ground to zero
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target ="Lnet/minecraft/world/level/levelgen/NoiseRouter;finalDensity()Lnet/minecraft/world/level/levelgen/DensityFunction;"))
    private DensityFunction yungscavebiomes_rewireFinalDensity(NoiseRouter instance) {
        return DensityFunctions.lerp(
                DensityFunctions.interpolated(new MarbleCavesInterpolationSlideDensityFunction(this)),
                instance.finalDensity(),
                DensityFunctions.constant(0.1)
        );
    }
}
