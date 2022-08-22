package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.TerrainInfo;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseSampler.class)
public abstract class MixinNoiseSampler implements NoiseSamplerBiomeHolder {
    @Shadow public abstract Climate.TargetPoint sample(int i, int j, int k);

    // NoiseSamplerBiomeHolder impl
    private BiomeSource ycbBiomeSource;
    private Registry<Biome> ycbBiomeRegistry;

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

    // Make marble caves interpolate ground to zero
    @Inject(method = "calculateBaseNoise(IIILnet/minecraft/world/level/levelgen/TerrainInfo;DZZLnet/minecraft/world/level/levelgen/blending/Blender;)D", at = @At("TAIL"), cancellable = true)
    private void ycbCalculateNoise(int x, int y, int z, TerrainInfo terrainInfo, double density, boolean bl, boolean bl2, Blender blender, CallbackInfoReturnable<Double> cir) {
        double noise = cir.getReturnValueD();

        // x, y, z -> block coords!
        // Normalize to quart (biome) coords
        x /= 4;
        y /= 4;
        z /= 4;

        if (noise != density) {
            if (this.ycbBiomeSource != null && this.ycbBiomeRegistry != null) {
                boolean found = false;
                int interp = 0;

                if (y <= 1) {
                    for (int x1 = -1; x1 <= 1; x1++) {
                        for (int z1 = -1; z1 <= 1; z1++) {
                            Biome biome = this.ycbBiomeSource.getNoiseBiome(x + x1, y, z + z1, (NoiseSampler) (Object) this);

                            if (this.ycbBiomeRegistry.getResourceKey(biome).get() == YCBModBiomes.MARBLE_CAVES) {
                                interp++;
                                found = true;
                            }
                        }
                    }
                }

                if (found) {
                    cir.setReturnValue(Mth.lerp(interp / 9.0, noise, 2));
                }
            }
        }
    }
}
