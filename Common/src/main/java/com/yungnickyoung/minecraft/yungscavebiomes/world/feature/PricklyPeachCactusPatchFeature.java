package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class PricklyPeachCactusPatchFeature extends Feature<NoneFeatureConfiguration> {

    public static final int MAX_PLACEMENT_ATTEMPTS = 13;
    public static final float PLACEMENT_SKIP_CHANCE = 0.25f;
    public static final int MAX_TOTAL_PLACEMENTS = 4;
    public static final int PLACEMENT_RADIUS_XZ = 7;
    public static final int PLACEMENT_RADIUS_Y = 5;

    public PricklyPeachCactusPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();

        int cactiRemaining = MAX_TOTAL_PLACEMENTS;

        for (int i = 0; i < MAX_PLACEMENT_ATTEMPTS; i++) {
            if (cactiRemaining <= 0) {
                return true;
            }

            // Skip many attempts
            if (random.nextFloat() < PLACEMENT_SKIP_CHANCE) {
                continue;
            }

            // Ellipsoid spread
            float sphereY = random.nextFloat(-1.0f, 1.0f);
            float sphereTheta = random.nextFloat(Mth.TWO_PI);
            float sphereXZScale = Mth.sqrt(1.0f - sphereY * sphereY);
            float radiusScale = Mth.square(random.nextFloat()); // Higher density towards the center
            BlockPos pos = origin.offset(
                    (int)(radiusScale * PLACEMENT_RADIUS_XZ * sphereXZScale * Mth.cos(sphereTheta)),
                    (int)(radiusScale * PLACEMENT_RADIUS_Y * sphereY),
                    (int)(radiusScale * PLACEMENT_RADIUS_XZ * sphereXZScale * Mth.sin(sphereTheta))
            );

            // Check for sandstone below
            if (!level.getBlockState(pos.below()).is(BlockModule.ANCIENT_SAND.get())) {
                continue;
            }

            // Ensure no adjacent cacti to help spread out distribution
            boolean isAdjacentCactus = false;

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Block block = level.getBlockState(pos.relative(direction)).getBlock();
                if (block == BlockModule.PRICKLY_PEACH_CACTUS.get() || block == Blocks.CACTUS) {
                    isAdjacentCactus = true;
                    break;
                }
            }

            if (!isAdjacentCactus) {
                level.setBlock(pos, BlockModule.PRICKLY_PEACH_CACTUS.get().defaultBlockState(), 3);
                cactiRemaining--;
            }
        }

        return cactiRemaining < MAX_TOTAL_PLACEMENTS;
    }
}
