package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public abstract class MixinAquifer implements Aquifer {
    @Shadow @Final private NoiseChunk noiseChunk;

    /**
     * Changes aquifer lava to water in Frosted Caves biomes.
     */
    @Inject(method = "computeFluid", at = @At("RETURN"), cancellable = true)
    private void yungscavebiomes_noLavaAquifersInFrostedCaves(int x, int y, int z, CallbackInfoReturnable<FluidStatus> cir) {
        FluidStatus original = cir.getReturnValue();

        // For some reason, trying to limit the check to only lava aquifers didn't work. No idea why.
//        if (!original.at(y).is(Blocks.LAVA)) return;

        // Gather data that we need
        NoiseSamplerBiomeHolder holder = ((NoiseSamplerBiomeHolder) this.noiseChunk);
        BiomeSource source = holder.getBiomeSource();
        Registry<Biome> biomes = holder.getBiomeRegistry();
        Climate.Sampler sampler = holder.getClimateSampler();

        if (biomes != null) {
            // Find biome at the given position
            Holder<Biome> biome = source.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), sampler);

            // Change lava to water if Frosted Caves is found
            Optional<ResourceKey<Biome>> biomeKey = biomes.getResourceKey(biome.value());
            if (biomeKey.isPresent() && biomeKey.get() == BiomeModule.FROSTED_CAVES) {
                int originalLevel = original.fluidLevel;
                cir.setReturnValue(new FluidStatus(originalLevel, Blocks.WATER.defaultBlockState()));
            }
        }
    }
}
