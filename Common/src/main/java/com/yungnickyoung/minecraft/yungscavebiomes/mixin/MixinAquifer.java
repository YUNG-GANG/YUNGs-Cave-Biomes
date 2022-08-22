package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.NoiseChunkAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Enforces a constant aquifer at y=16 in marble caves biome.
 */
@Mixin(Aquifer.NoiseBasedAquifer.class)
public class MixinAquifer {
    @Shadow @Final private NoiseChunk noiseChunk;

    @Inject(method = "computeFluid", at = @At("HEAD"), cancellable = true)
    private void ycbAquiferFluids(int x, int y, int z, CallbackInfoReturnable<Aquifer.FluidStatus> cir) {
        // Target multiple aquifer cells to prevent water level artifacts
        if (y > -4 && y < 36) {

            // Gather data that we need
            NoiseSamplerBiomeHolder holder = (NoiseSamplerBiomeHolder) ((NoiseChunkAccessor) (this.noiseChunk)).getSampler();
            BiomeSource source = holder.getBiomeSource();
            Registry<Biome> biomes = holder.getBiomeRegistry();

            if (biomes != null) {
                // Find biome
                Holder<Biome> biome = source.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), holder);

                // Make aquifer at y16 if marble caves is found
                if (biomes.getResourceKey(biome.value()).get() == BiomeModule.MARBLE_CAVES) {
                    cir.setReturnValue(new Aquifer.FluidStatus(16, Blocks.WATER.defaultBlockState()));
                }
            }
        }
    }
}
