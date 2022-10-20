package com.yungnickyoung.minecraft.yungscavebiomes.world.noise;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;

public class MarbleCavesInterpolationSlideDensityFunction implements DensityFunction {
    private static final double SQRT3 = Math.sqrt(3.0);
    private static final int SEARCH_RADIUS_INT = Mth.ceil(SQRT3 * QuartPos.SIZE);

    public static final int BLOCK_XYZ_OFFSET = QuartPos.SIZE / 2;
    private static final int FIDDLE_HASH_BIT_START = 24;
    private static final int FIDDLE_HASH_BIT_COUNT = 10;
    private static final int FIDDLE_HASH_BIT_SHIFTED = 1 << FIDDLE_HASH_BIT_COUNT;
    private static final int FIDDLE_HASH_BIT_MASK = FIDDLE_HASH_BIT_SHIFTED - 1;
    private static final double FIDDLE_MAGNITUDE = 1.0; // 0.9 in net.minecraft.world.level.biome.BiomeManager

    private final NoiseSamplerBiomeHolder holder;
    private long biomeZoomSeed;

    public MarbleCavesInterpolationSlideDensityFunction(NoiseSamplerBiomeHolder holder) {
        this.holder = holder;
        this.biomeZoomSeed = BiomeManager.obfuscateSeed(holder.getWorldSeed());
    }

    @Override
    public double compute(FunctionContext context) {
        BiomeSource source = holder.getBiomeSource();
        Registry<Biome> biomes = holder.getBiomeRegistry();

        if (source != null && biomes != null) {
            int offsetX = context.blockX() - BLOCK_XYZ_OFFSET;
            int offsetY = context.blockY() - BLOCK_XYZ_OFFSET;
            int offsetZ = context.blockZ() - BLOCK_XYZ_OFFSET;

            int xStart = (offsetX - SEARCH_RADIUS_INT) >> QuartPos.BITS;
            int yStart = (offsetY - SEARCH_RADIUS_INT) >> QuartPos.BITS;
            int zStart = (offsetZ - SEARCH_RADIUS_INT) >> QuartPos.BITS;
            int xEnd = (offsetX + SEARCH_RADIUS_INT) >> QuartPos.BITS;
            int yEnd = (offsetY + SEARCH_RADIUS_INT) >> QuartPos.BITS;
            int zEnd = (offsetZ + SEARCH_RADIUS_INT) >> QuartPos.BITS;
            int xCount = xEnd - xStart + 1;
            int yCount = yEnd - yStart + 1;
            int zCount = zEnd - zStart + 1;

            double xDelta = (offsetX - (xStart << QuartPos.BITS)) * (1.0 / QuartPos.SIZE);
            double yDelta = (offsetY - (yStart << QuartPos.BITS)) * (1.0 / QuartPos.SIZE);
            double zDelta = (offsetZ - (zStart << QuartPos.BITS)) * (1.0 / QuartPos.SIZE);

            double totalWeight = 0;
            double marbleWeight = 0;
            for (int cz = 0, cy = 0, cx = 0;;) {
                double fiddledDistanceSquared = getFiddledDistance(this.biomeZoomSeed, cx + xStart, cy + yStart, cz + zStart, xDelta - cx, yDelta - cy, zDelta - cz);

                if (fiddledDistanceSquared < 3) {
                    double falloff = 3 - fiddledDistanceSquared;
                    falloff *= falloff * falloff;

                    Holder<Biome> biomeHolderHere = source.getNoiseBiome(cx + xStart, cy + yStart, cz + zStart, holder.getClimateSampler());
                    totalWeight += falloff;
                    if (shouldSolidifyForMarbleCaves(biomeHolderHere, cy + yStart)) marbleWeight += falloff;
                }

                cz++;
                if (cz < zCount) continue;
                cz = 0;
                cy++;
                if (cy < yCount) continue;
                cy = 0;
                cx++;
                if (cx >= xCount) break;
            }

            return marbleWeight / totalWeight;
        }

        return 0;
    }

    private boolean shouldSolidifyForMarbleCaves(Holder<Biome> biome, int quartY) {
        return (quartY <= 0 && biome.unwrapKey().get() == BiomeModule.MARBLE_CAVES.getResourceKey());
    }

    private static double getFiddledDistance(long seed, int quartX, int quartY, int quartZ, double dx, double dy, double dz) {
        long hash = LinearCongruentialGenerator.next(seed, quartX);
        hash = LinearCongruentialGenerator.next(hash, quartY);
        hash = LinearCongruentialGenerator.next(hash, quartZ);
        hash = LinearCongruentialGenerator.next(hash, quartX);
        hash = LinearCongruentialGenerator.next(hash, quartY);
        hash = LinearCongruentialGenerator.next(hash, quartZ);
        double jz = getFiddle(hash);
        hash = LinearCongruentialGenerator.next(hash, seed);
        double jy = getFiddle(hash);
        hash = LinearCongruentialGenerator.next(hash, seed);
        double jx = getFiddle(hash);
        return Mth.square(dz + jz) + Mth.square(dy + jy) + Mth.square(dx + jx);
    }

    private static double getFiddle(long hash) {
        long hashBits = (hash >> FIDDLE_HASH_BIT_START) & FIDDLE_HASH_BIT_MASK;
        return hashBits * (FIDDLE_MAGNITUDE / FIDDLE_HASH_BIT_SHIFTED) - 0.5 * FIDDLE_MAGNITUDE;
    }

    @Override
    public void fillArray(double[] doubles, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(doubles, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(new MarbleCavesInterpolationSlideDensityFunction(holder));
    }

    @Override
    public double minValue() {
        return 0.0;
    }

    @Override
    public double maxValue() {
        return 1.0;
    }

    @Override
    public Codec<? extends DensityFunction> codec() {
        // TODO: is this needed? at the point where this is injected no deserialization is taking place.
        return null;
    }
}
