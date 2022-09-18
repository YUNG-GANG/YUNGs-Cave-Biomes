package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class MultisurfaceNoisySphereReplaceFeature extends Feature<MultisurfaceNoisySphereReplaceConfig> {

    private static final double NOISE_FREQUENCY_XZ = 0.15;
    private static final double NOISE_FREQUENCY_Y = 0.15;
    private static final long NOISE_SEED_FLIP_MASK = -0x6139D09B0B75F247L;

    public MultisurfaceNoisySphereReplaceFeature(Codec<MultisurfaceNoisySphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MultisurfaceNoisySphereReplaceConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        int radiusMin = context.config().radiusMin;
        int radiusMax = context.config().radiusMax;
        List<Block> matches = context.config().matches;
        long noiseSeed = context.level().getSeed() ^ NOISE_SEED_FLIP_MASK;

        for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(
                origin, noiseSeed, NOISE_FREQUENCY_XZ, NOISE_FREQUENCY_Y, radiusMin, radiusMax)) {
            if (matches.contains(level.getBlockState(here).getBlock())) {
                if (level.getBlockState(here.above()).isAir()) {
                    level.setBlock(here, context.config().floor, 3);
                } else if (level.getBlockState(here.below()).isAir()) {
                    level.setBlock(here, context.config().ceiling, 3);
                } else {
                    level.setBlock(here, context.config().wall, 3);
                }
            }
        }

        return true;
    }
}
