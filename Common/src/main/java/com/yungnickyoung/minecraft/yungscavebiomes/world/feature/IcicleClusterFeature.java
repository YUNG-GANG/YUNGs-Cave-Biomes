package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.DripstoneIceUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

public class IcicleClusterFeature extends Feature<DripstoneClusterConfiguration> {
    public IcicleClusterFeature(Codec<DripstoneClusterConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DripstoneClusterConfiguration> featurePlaceContext) {
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos origin = featurePlaceContext.origin();
        DripstoneClusterConfiguration config = featurePlaceContext.config();
        Random random = featurePlaceContext.random();
        if (!DripstoneIceUtils.isEmpty(worldGenLevel, origin)) {
            return false;
        } else {
            int height = config.height.sample(random);
            float wetness = config.wetness.sample(random);
            float density = config.density.sample(random);

            // Ellipse
            int uRadius = config.radius.sample(random);
            int vRadius = config.radius.sample(random);
            int uvRadiusMax = Math.max(uRadius, vRadius);
            float angle = random.nextFloat(Mth.TWO_PI);
            float rx = Mth.cos(angle), rz = Mth.sin(angle);
            float ux = rx / uRadius, uz = rz / uRadius;
            float vx = rz / vRadius, vz = -rx / vRadius;

            for (int xOffset = -uvRadiusMax; xOffset <= uvRadiusMax; ++xOffset) {
                for (int zOffset = -uvRadiusMax; zOffset <= uvRadiusMax; ++zOffset) {
                    float ellipseDensity = Mth.square(xOffset * ux + zOffset * uz) + Mth.square(xOffset * vx + zOffset * vz);
                    if (ellipseDensity >= 1.0f) continue;
                    float chanceOfStalagmiteOrStalactite = this.getChanceOfStalagmiteOrStalactite(ellipseDensity, uvRadiusMax, config);
                    BlockPos blockPos2 = origin.offset(xOffset, 0, zOffset);
                    this.placeColumn(worldGenLevel, random, blockPos2, xOffset, zOffset, wetness,
                            chanceOfStalagmiteOrStalactite, height, density, config);
                }
            }

            return true;
        }
    }

    private void placeColumn(WorldGenLevel worldGenLevel,
                             Random random,
                             BlockPos blockPos,
                             int xOffset,
                             int zOffset,
                             float wetness,
                             double chance,
                             int height,
                             float density,
                             DripstoneClusterConfiguration config
    ) {
        Optional<Column> columnOptional = Column.scan(worldGenLevel, blockPos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
        if (columnOptional.isEmpty()) return;

        OptionalInt ceilingOptional = columnOptional.get().getCeiling();
        OptionalInt floorOptional = columnOptional.get().getFloor();

        if (ceilingOptional.isPresent() || floorOptional.isPresent()) {
            Column column;

            // Determine if this dripstone is "wet", i.e. has water block on floor below it
            boolean isWet = random.nextFloat() < wetness;
            if (isWet && floorOptional.isPresent() && this.canPlacePool(worldGenLevel, blockPos.atY(floorOptional.getAsInt()))) {
                int floor = floorOptional.getAsInt();
                column = columnOptional.get().withFloor(OptionalInt.of(floor - 1));
                worldGenLevel.setBlock(blockPos.atY(floor), Blocks.WATER.defaultBlockState(), 2);
            } else {
                column = columnOptional.get();
            }

            OptionalInt adjustedFloorOptional = column.getFloor();

            // Place packed ice in ceiling
            boolean bl2 = random.nextDouble() < chance;
            int o;
            if (ceilingOptional.isPresent() && bl2 && !this.isLava(worldGenLevel, blockPos.atY(ceilingOptional.getAsInt()))) {
                int thickness = config.dripstoneBlockLayerThickness.sample(random);
                this.replaceBlocksWithPackedIce(worldGenLevel, blockPos.atY(ceilingOptional.getAsInt()), thickness, Direction.UP);
                int n;
                if (adjustedFloorOptional.isPresent()) {
                    n = Math.min(height, ceilingOptional.getAsInt() - adjustedFloorOptional.getAsInt());
                } else {
                    n = height;
                }

                o = this.getDripstoneHeight(random, xOffset, zOffset, density, n, config);
            } else {
                o = 0;
            }

            // Place packed ice in floor
            boolean n = random.nextDouble() < chance;
            int m;
            if (adjustedFloorOptional.isPresent() && n && !this.isLava(worldGenLevel, blockPos.atY(adjustedFloorOptional.getAsInt()))) {
                int thickness = config.dripstoneBlockLayerThickness.sample(random);
                this.replaceBlocksWithPackedIce(worldGenLevel, blockPos.atY(adjustedFloorOptional.getAsInt()), thickness, Direction.DOWN);
                if (ceilingOptional.isPresent()) {
                    m = Math.max(0, o + Mth.randomBetweenInclusive(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
                } else {
                    m = this.getDripstoneHeight(random, xOffset, zOffset, density, height, config);
                }
            } else {
                m = 0;
            }

            int w;
            int p;
            if (ceilingOptional.isPresent() && adjustedFloorOptional.isPresent() && ceilingOptional.getAsInt() - o <= adjustedFloorOptional.getAsInt() + m) {
                int adjustedFloor = adjustedFloorOptional.getAsInt();
                int adjustedCeiling = ceilingOptional.getAsInt();
                int s = Math.max(adjustedCeiling - o, adjustedFloor + 1);
                int t = Math.min(adjustedFloor + m, adjustedCeiling - 1);
                int u = Mth.randomBetweenInclusive(random, s, t + 1);
                int v = u - 1;
                p = adjustedCeiling - u;
                w = v - adjustedFloor;
            } else {
                p = o;
                w = m;
            }

            boolean q = random.nextBoolean() && p > 0 && w > 0 && column.getHeight().isPresent() && p + w == column.getHeight().getAsInt();
            if (ceilingOptional.isPresent()) {
                DripstoneIceUtils.growIcicle(worldGenLevel, blockPos.atY(ceilingOptional.getAsInt() - 1), Direction.DOWN, p, q);
            }
        }
    }

    private boolean isLava(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos).is(Blocks.LAVA);
    }

    private int getDripstoneHeight(Random random, int xOffset, int zOffset, float density, int height, DripstoneClusterConfiguration config) {
        if (random.nextFloat() > density) {
            return 0;
        } else {
            int l = Math.abs(xOffset) + Math.abs(zOffset);
            float g = (float)Mth.clampedMap(l, 0.0, config.maxDistanceFromCenterAffectingHeightBias, (double)height / 2.0, 0.0);
            return (int)randomBetweenBiased(random, 0.0F, (float)height, g, (float)config.heightDeviation);
        }
    }

    private boolean canPlacePool(WorldGenLevel worldGenLevel, BlockPos blockPos) {
        BlockState blockState = worldGenLevel.getBlockState(blockPos);
        if (!blockState.is(Blocks.WATER) && !blockState.is(Blocks.DRIPSTONE_BLOCK) && !blockState.is(Blocks.POINTED_DRIPSTONE)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (!this.canBeAdjacentToWater(worldGenLevel, blockPos.relative(direction))) {
                    return false;
                }
            }

            return this.canBeAdjacentToWater(worldGenLevel, blockPos.below());
        } else {
            return false;
        }
    }

    private boolean canBeAdjacentToWater(LevelAccessor levelAccessor, BlockPos blockPos) {
        BlockState blockState = levelAccessor.getBlockState(blockPos);
        return blockState.is(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().is(FluidTags.WATER);
    }

    private void replaceBlocksWithPackedIce(WorldGenLevel worldGenLevel, BlockPos blockPos, int length, Direction direction) {
        BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

        for(int i = 0; i < length; ++i) {
            if (!DripstoneIceUtils.placePackedIceIfPossible(worldGenLevel, mutableBlockPos)) {
                return;
            }

            mutableBlockPos.move(direction);
        }
    }

    private float getChanceOfStalagmiteOrStalactite(float ellipseDensity, float uvRadiusMax, DripstoneClusterConfiguration config) {
        return Mth.clampedMap(ellipseDensity * (uvRadiusMax * uvRadiusMax),
                (uvRadiusMax * uvRadiusMax),
                config.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn,
                config.chanceOfDripstoneColumnAtMaxDistanceFromCenter,
                1.0F);
    }

    private static float randomBetweenBiased(Random random, float f, float g, float h, float i) {
        return ClampedNormalFloat.sample(random, h, i, f, g);
    }
}