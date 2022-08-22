package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
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

        int maxCacti = 3;

        for (int j = 0; j < 16; j++) {
            if (maxCacti <= 0) {
                return true;
            }

            // Triangle 5x3x5 spread
            BlockPos local = pos.offset(
                    random.nextInt(6) - random.nextInt(6),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(6) - random.nextInt(6)
            );

            // Check for sandstone below
            if (!level.getBlockState(local.below()).is(BlockModule.ANCIENT_SAND.get())) {
                continue;
            }

            // Make most cases not have cacti
            if (random.nextInt(3) > 1) {
                continue;
            }

            level.setBlock(local.below(), BlockModule.ANCIENT_SAND.get().defaultBlockState(), 3);

            // Attempt to place cactus
            boolean placedCactus = false;
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
                placedCactus = true;
            }

            if (placedCactus) {
                maxCacti--;
            }
        }

        return false;
    }
}
