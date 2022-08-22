package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class PricklyPearCactusPatchFeature extends Feature<NoneFeatureConfiguration> {
    public PricklyPearCactusPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        int maxCacti = 3;

        for (int i = 0; i < 10; i++) {
            if (maxCacti <= 0) {
                return true;
            }

            // Triangle 5x3x5 spread
            BlockPos pos = origin.offset(
                    random.nextInt(6) - random.nextInt(6),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(6) - random.nextInt(6)
            );

            // Check for sandstone below
            if (!level.getBlockState(pos.below()).is(BlockModule.ANCIENT_SAND.get())) {
                continue;
            }

            // Make most cases not have cacti
            if (random.nextInt(3) > 1) {
                continue;
            }

            // Ensure no adjacent cacti to help spread out distribution
            boolean isAdjacentCactus = false;

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Block block = level.getBlockState(pos.relative(direction)).getBlock();
                if (block == BlockModule.PRICKLY_PEAR_CACTUS.get() || block == Blocks.CACTUS) {
                    isAdjacentCactus = true;
                    break;
                }
            }

            if (!isAdjacentCactus) {
                level.setBlock(pos, BlockModule.PRICKLY_PEAR_CACTUS.get().defaultBlockState(), 3);
                maxCacti--;
            }
        }

        return false;
    }
}
