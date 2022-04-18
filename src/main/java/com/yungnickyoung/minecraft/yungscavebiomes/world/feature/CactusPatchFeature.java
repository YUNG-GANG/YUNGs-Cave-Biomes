package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CactusPatchFeature extends Feature<NoneFeatureConfiguration> {
    public CactusPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        for (int j = 0; j < 24; j++) {
            // Triangle 5x3x5 spread
            BlockPos local = pos.offset(
                    random.nextInt(6) - random.nextInt(6),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(6) - random.nextInt(6)
            );

            // Check for sandstone below
            if (!level.getBlockState(local.below()).is(YCBModBlocks.ANCIENT_SAND)) {
                continue;
            }

            // Make most cases not have cacti
            if (random.nextInt(3) > 1) {
                continue;
            }

            level.setBlock(local.below(), YCBModBlocks.ANCIENT_SAND.defaultBlockState(), 3);

            // Cactus patch
            for (int i = 0; i < random.nextInt(3); i++) {
                if (!level.getBlockState(local.above(i)).isAir()) {
                    break;
                }

                if (!level.getBlockState(local.above(i).north()).isAir()) {
                    break;
                }

                if (!level.getBlockState(local.above(i).east()).isAir()) {
                    break;
                }

                if (!level.getBlockState(local.above(i).west()).isAir()) {
                    break;
                }

                if (!level.getBlockState(local.above(i).south()).isAir()) {
                    break;
                }

                level.setBlock(local.above(i), Blocks.CACTUS.defaultBlockState(), 3);
            }
        }

        return false;
    }
}
