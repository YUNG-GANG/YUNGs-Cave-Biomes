package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CactusPatchFeature extends Feature<NoneFeatureConfiguration> {

    public static final int MAX_PLACEMENT_ATTEMPTS = 16;
    public static final float PLACEMENT_SKIP_CHANCE = 0.25f;
    public static final int MAX_TOTAL_PLACEMENTS = 4;
    public static final int PLACEMENT_RADIUS_XZ = 7;
    public static final int PLACEMENT_RADIUS_Y = 5;
    public static final int MAX_CACTUS_HEIGHT = 3;
    public static final float CACTUS_HEIGHT_GROWTH_CHANCE = 0.3f;

    public CactusPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        int cactiRemaining = MAX_TOTAL_PLACEMENTS;

        for (int j = 0; j < MAX_PLACEMENT_ATTEMPTS; j++) {
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
            BlockPos local = pos.offset(
                    (int)(radiusScale * PLACEMENT_RADIUS_XZ * sphereXZScale * Mth.cos(sphereTheta)),
                    (int)(radiusScale * PLACEMENT_RADIUS_Y * sphereY),
                    (int)(radiusScale * PLACEMENT_RADIUS_XZ * sphereXZScale * Mth.sin(sphereTheta))
            );

            // Check for sandstone below
            if (!level.getBlockState(local.below()).is(BlockModule.ANCIENT_SAND.get())) {
                continue;
            }

            // Attempt to place cactus
            boolean placedCactus = false;
            for (int i = 0; i < MAX_CACTUS_HEIGHT; i++) {
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

                if (random.nextFloat() >= CACTUS_HEIGHT_GROWTH_CHANCE) {
                    break;
                }
            }

            if (placedCactus) {
                cactiRemaining--;
            }
        }

        return cactiRemaining < MAX_TOTAL_PLACEMENTS;
    }
}
