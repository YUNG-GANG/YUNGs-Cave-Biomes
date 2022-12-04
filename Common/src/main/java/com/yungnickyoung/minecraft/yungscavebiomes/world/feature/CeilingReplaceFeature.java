package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class CeilingReplaceFeature extends Feature<CeilingReplaceConfig> {

    private static final double NOISE_FREQUENCY_XZ = 0.15;
    private static final double NOISE_FREQUENCY_Y = 0.15;
    private static final long NOISE_SEED_FLIP_MASK = -0x6139D09B0B75F247L;

    public CeilingReplaceFeature(Codec<CeilingReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CeilingReplaceConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        int radiusMin = context.config().radiusMin;
        int radiusMax = context.config().radiusMax;
        List<Block> matches = context.config().matches;
        long noiseSeed = context.level().getSeed() ^ NOISE_SEED_FLIP_MASK;

        for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(
                origin, noiseSeed, NOISE_FREQUENCY_XZ, NOISE_FREQUENCY_Y, radiusMin, radiusMax)) {
            if (matches.contains(level.getBlockState(here).getBlock())) {
                for (int yOffset = context.config().width; yOffset >= 1; yOffset--) {
                    if (level.getBlockState(here.below(yOffset)).isAir()) {
                        level.setBlock(here, context.config().place, 3);
                        break;
                    }
                }
            }
        }

        return true;
    }

}
