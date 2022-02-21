package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public class MixinAquifer {
    @Shadow @Final private NoiseChunk noiseChunk;

    @Inject(method = "computeFluid", at = @At("HEAD"), cancellable = true)
    private void ycbAquiferFluids(int x, int y, int z, CallbackInfoReturnable<Aquifer.FluidStatus> cir) {
        if (y > -4 && y < 36) {

            NoiseSamplerBiomeHolder holder = (NoiseSamplerBiomeHolder) ((NoiseChunkAccessor) (this.noiseChunk)).getSampler();
            BiomeSource source = holder.getBiomeSource();
            Registry<Biome> biomes = holder.getBiomeRegistry();

            // TODO: why is it null? it should always be defined!
            if (biomes != null) {
                Biome biome = source.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), holder);
                if (biomes.getResourceKey(biome).get() == YCBModBiomes.MARBLE_CAVES) {
                    cir.setReturnValue(new Aquifer.FluidStatus(16, Blocks.WATER.defaultBlockState()));
                }
            }
        }
    }
}
