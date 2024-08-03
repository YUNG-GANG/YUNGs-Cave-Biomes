package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
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

@Mixin(Aquifer.NoiseBasedAquifer.class)
public abstract class MixinAquifer {
    @Shadow @Final private NoiseChunk noiseChunk;

    /**
     * Enforces a constant aquifer at y=16 in marble caves biome.
     */
    @Inject(method = "computeFluid", at = @At("HEAD"), cancellable = true)
    private void yungscavebiomes_enforceMarbleCavesAquifer(int x, int y, int z, CallbackInfoReturnable<Aquifer.FluidStatus> cir) {
        if (!YungsCaveBiomesCommon.MARBLE_CAVES_ENABLED) {
            return;
        }

        // Target multiple aquifer cells to prevent water level artifacts
        if (y > -4 && y < 36) {

            // Gather data that we need
            NoiseSamplerBiomeHolder holder = ((NoiseSamplerBiomeHolder) this.noiseChunk);
            BiomeSource source = holder.getBiomeSource();
            Registry<Biome> biomes = holder.getBiomeRegistry();
            Climate.Sampler sampler = holder.getClimateSampler();

            if (biomes != null) {
                // Find biome
                Holder<Biome> biome = source.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), sampler);

                // Make aquifer at y16 if marble caves is found
                if (biomes.getResourceKey(biome.value()).get() == BiomeModule.MARBLE_CAVES.getResourceKey()) {
                    cir.setReturnValue(new Aquifer.FluidStatus(16, Blocks.WATER.defaultBlockState()));
                }
            }
        }
    }
}
