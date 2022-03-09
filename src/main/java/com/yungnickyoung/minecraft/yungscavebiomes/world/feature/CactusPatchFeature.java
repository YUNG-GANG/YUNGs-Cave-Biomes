package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
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

        // Check for sandstone below
        if (!level.getBlockState(pos.below()).is(Blocks.SANDSTONE)) {
            return false;
        }

        level.setBlock(pos, Blocks.SAND.defaultBlockState(), 3);

        // Make most cases not have cacti
        if (random.nextInt(3) > 0) {
            return false;
        }

        // Cactus patch
        for (int i = 0; i < random.nextInt(3); i++) {
            if (!level.getBlockState(pos.above(1 + i)).isAir()) {
                return false;
            }

            level.setBlock(pos.above(1 + i), Blocks.CACTUS.defaultBlockState(), 3);
        }

        return false;
    }
}
