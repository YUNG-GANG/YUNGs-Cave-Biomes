package com.yungnickyoung.minecraft.yungscavebiomes.mixin.marble_caves;

import net.minecraft.world.level.levelgen.Aquifer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public abstract class AquiferMixin {
//    @Shadow @Final private NoiseChunk noiseChunk;
//
//    /**
//     * Enforces a constant aquifer at y=16 in marble caves biome.
//     */
//    @Inject(method = "computeFluid", at = @At("HEAD"), cancellable = true)
//    private void yungscavebiomes_enforceMarbleCavesAquifer(int x, int y, int z, CallbackInfoReturnable<Aquifer.FluidStatus> cir) {
//        if (!YungsCaveBiomesCommon.MARBLE_CAVES_ENABLED) {
//            return;
//        }
//
//        // Target multiple aquifer cells to prevent water level artifacts
//        if (y > -4 && y < 36) {
//
//            // Gather data that we need
//            NoiseSamplerBiomeHolder holder = ((NoiseSamplerBiomeHolder) this.noiseChunk);
//            BiomeSource source = holder.getBiomeSource();
//            Registry<Biome> biomes = holder.getBiomeRegistry();
//            Climate.Sampler sampler = holder.getClimateSampler();
//
//            if (biomes != null) {
//                // Find biome
//                Holder<Biome> biome = source.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), sampler);
//
//                // Make aquifer at y16 if marble caves is found
//                if (biomes.getResourceKey(biome.value()).get() == BiomeModule.MARBLE_CAVES.getResourceKey()) {
//                    cir.setReturnValue(new Aquifer.FluidStatus(16, Blocks.WATER.defaultBlockState()));
//                }
//            }
//        }
//    }
}
