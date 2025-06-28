package com.yungnickyoung.minecraft.yungscavebiomes.mixin.marble_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.MarbleCavesInterpolationSlideDensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkMixin implements NoiseSamplerBiomeHolder {
//    // Make marble caves interpolate ground to zero
//    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseRouter;finalDensity()Lnet/minecraft/world/level/levelgen/DensityFunction;"))
//    private DensityFunction yungscavebiomes_rewireFinalDensity(NoiseRouter instance) {
//        if (!YungsCaveBiomesCommon.MARBLE_CAVES_ENABLED) {
//            return instance.finalDensity();
//        }
//
//        return DensityFunctions.lerp(
//                DensityFunctions.interpolated(new MarbleCavesInterpolationSlideDensityFunction(this)),
//                instance.finalDensity(),
//                DensityFunctions.constant(0.1)
//        );
//    }
}
