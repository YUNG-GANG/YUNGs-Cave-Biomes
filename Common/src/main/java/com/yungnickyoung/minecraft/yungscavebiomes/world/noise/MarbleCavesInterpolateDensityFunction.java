package com.yungnickyoung.minecraft.yungscavebiomes.world.noise;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;

public class MarbleCavesInterpolateDensityFunction implements DensityFunction {
    private final DensityFunction inner;
    private final NoiseSamplerBiomeHolder holder;

    public MarbleCavesInterpolateDensityFunction(DensityFunction inner, NoiseSamplerBiomeHolder holder) {
        this.inner = inner;
        this.holder = holder;
    }

    @Override
    public double compute(FunctionContext context) {
        double noise = this.inner.compute(context);
        int x = context.blockX() / 4;
        int y = context.blockY() / 4;
        int z = context.blockZ() / 4;

        BiomeSource source = holder.getBiomeSource();
        Registry<Biome> biomes = holder.getBiomeRegistry();

        if (source != null && biomes != null) {
            boolean found = false;
            int interp = 0;

            if (y <= 1) {
                for (int x1 = -1; x1 <= 1; x1++) {
                    for (int z1 = -1; z1 <= 1; z1++) {
                        Holder<Biome> biome = source.getNoiseBiome(x + x1, y, z + z1, holder.getClimateSampler());

                        if (biome.unwrapKey().get() == BiomeModule.MARBLE_CAVES) {
                            interp++;
                            found = true;
                        }
                    }
                }
            }

            if (found) {
                return Mth.lerp(interp / 9.0, noise, 2);
            }
        }

        return noise;
    }

    @Override
    public void fillArray(double[] doubles, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(doubles, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return new MarbleCavesInterpolateDensityFunction(inner.mapAll(visitor), holder);
    }

    @Override
    public double minValue() {
        return inner.minValue();
    }

    @Override
    public double maxValue() {
        return inner.maxValue();
    }

    @Override
    public Codec<? extends DensityFunction> codec() {
        // TODO: is this needed? at the point where this is injected no deserialization is taking place.
        return null;
    }
}
