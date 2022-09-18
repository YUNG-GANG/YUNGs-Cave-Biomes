package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

// Replaces blocks in a noisy sphere that matches a list of blocks
public class NoisySphereReplaceFeature extends Feature<NoisySphereReplaceConfig> {

    private static final double NOISE_FREQUENCY_XZ = 0.15;
    private static final double NOISE_FREQUENCY_Y = 0.15;
    private static final long NOISE_SEED_FLIP_MASK = -0x6139D09B0B75F247L;

    public NoisySphereReplaceFeature(Codec<NoisySphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoisySphereReplaceConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        int radiusMin = context.config().radiusMin;
        int radiusMax = context.config().radiusMax;
        List<Block> matches = context.config().matches;
        long noiseSeed = context.level().getSeed() ^ NOISE_SEED_FLIP_MASK;

        for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(
                origin, noiseSeed, NOISE_FREQUENCY_XZ, NOISE_FREQUENCY_Y, radiusMin, radiusMax)) {
            if (matches.contains(level.getBlockState(here).getBlock())) {
                level.setBlock(here, context.config().place, 3);
            }
        }

        return true;
    }

}
