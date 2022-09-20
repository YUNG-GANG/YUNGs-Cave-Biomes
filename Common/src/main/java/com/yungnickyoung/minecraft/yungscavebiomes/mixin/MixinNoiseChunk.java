package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.MarbleCavesInterpolateDensityFunction;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunk.class)
public abstract class MixinNoiseChunk implements NoiseSamplerBiomeHolder {

    // NoiseSamplerBiomeHolder impl
    private BiomeSource ycbBiomeSource;
    private Registry<Biome> ycbBiomeRegistry;
    private Climate.Sampler ycbClimateSampler;

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
        return ycbClimateSampler;
    }

    @Override
    public void setClimateSampler(Climate.Sampler sampler) {
        this.ycbClimateSampler = sampler;
    }

    // Make marble caves interpolate ground to zero
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target ="Lnet/minecraft/world/level/levelgen/NoiseRouter;finalDensity()Lnet/minecraft/world/level/levelgen/DensityFunction;"))
    private DensityFunction yungscavebiomes_ewireFinalDensity(NoiseRouter instance) {
        return new MarbleCavesInterpolateDensityFunction(instance.finalDensity(), this);
    }
}
