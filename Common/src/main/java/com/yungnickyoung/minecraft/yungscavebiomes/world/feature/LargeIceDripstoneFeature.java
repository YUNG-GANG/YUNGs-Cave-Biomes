package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class LargeIceDripstoneFeature extends Feature<LargeIceDripstoneConfiguration> {
    public LargeIceDripstoneFeature(Codec<LargeIceDripstoneConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LargeIceDripstoneConfiguration> featurePlaceContext) {
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos origin = featurePlaceContext.origin();
        LargeIceDripstoneConfiguration config = featurePlaceContext.config();
        Random random = featurePlaceContext.random();

        if (!DripstoneIceUtils.isEmptyOrWater(worldGenLevel, origin)) return false;

        Optional<Column> optionalColumn = Column.scan(worldGenLevel, origin, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);

        // Only continue if column is valid Range with height >= 4
        if (optionalColumn.isEmpty() || !(optionalColumn.get() instanceof Column.Range range)) return false;
        if (range.height() < 4) return false;

        // Determine column radius
        int maxColumnRadius = (int)((float)range.height() * config.maxColumnRadiusToCaveHeightRatio);
        maxColumnRadius = Mth.clamp(maxColumnRadius, config.columnRadius.getMinValue(), config.columnRadius.getMaxValue());
        int columnRadius = Mth.randomBetweenInclusive(random, config.columnRadius.getMinValue(), maxColumnRadius);

        // Create stalactite and stalagmite objects
        LargeIceDripstone stalactite = makeIceDripstone(origin.atY(range.ceiling() - 1), false, random, columnRadius, config.stalactiteBluntness, config.heightScale);
        LargeIceDripstone stalagmite = makeIceDripstone(origin.atY(range.floor() + 1), true, random, columnRadius, config.stalagmiteBluntness, config.heightScale);

        // Wind offset
        LargeIceDripstoneFeature.WindOffsetter windOffsetter;
        if (stalactite.isSuitableForWind(config) && stalagmite.isSuitableForWind(config)) {
            windOffsetter = new LargeIceDripstoneFeature.WindOffsetter(origin.getY(), random, config.windSpeed, config.angle);
        } else {
            windOffsetter = LargeIceDripstoneFeature.WindOffsetter.noWind();
        }

        boolean canSpawnStalactite = stalactite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
        boolean canspawnStalagmite = stalagmite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldGenLevel, windOffsetter);
        if (canSpawnStalactite) stalactite.placeBlocks(worldGenLevel, random, windOffsetter, config.rareIceChance);
        if (canspawnStalagmite) stalagmite.placeBlocks(worldGenLevel, random, windOffsetter, config.rareIceChance);

        return true;
    }

    private static LargeIceDripstone makeIceDripstone(BlockPos blockPos,
                                                      boolean isPointingUp,
                                                      Random random,
                                                      int radius,
                                                      FloatProvider bluntnessProvider,
                                                      FloatProvider scaleProvider
    ) {
        return new LargeIceDripstone(blockPos, isPointingUp, radius, bluntnessProvider.sample(random), scaleProvider.sample(random));
    }

    private void placeDebugMarkers(WorldGenLevel worldGenLevel, BlockPos blockPos, Column.Range range, LargeIceDripstoneFeature.WindOffsetter windOffsetter) {
        worldGenLevel.setBlock(windOffsetter.offset(blockPos.atY(range.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
        worldGenLevel.setBlock(windOffsetter.offset(blockPos.atY(range.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

        for(BlockPos.MutableBlockPos mutableBlockPos = blockPos.atY(range.floor() + 2).mutable(); mutableBlockPos.getY() < range.ceiling() - 1; mutableBlockPos.move(Direction.UP)) {
            BlockPos blockPos2 = windOffsetter.offset(mutableBlockPos);
            if (DripstoneIceUtils.isEmptyOrWater(worldGenLevel, blockPos2) || worldGenLevel.getBlockState(blockPos2).is(Blocks.DRIPSTONE_BLOCK)) {
                worldGenLevel.setBlock(blockPos2, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
            }
        }
    }

    static final class LargeIceDripstone {
        private BlockPos root;
        private final boolean pointingUp;
        private int radius;
        private final double bluntness;
        private final double scale;

        LargeIceDripstone(BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
            this.root = root;
            this.pointingUp = pointingUp;
            this.radius = radius;
            this.bluntness = bluntness;
            this.scale = scale;
        }

        private int getHeight() {
            return this.getHeightAtRadius(0.0F);
        }

        private int getMinY() {
            return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
        }

        private int getMaxY() {
            return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
        }

        /**
         * Adjusts root position & radius to ensure the dripstone's based position is encased in stone
         * so that it doesn't float.
         */
        boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel worldGenLevel, LargeIceDripstoneFeature.WindOffsetter windOffsetter) {
            while (this.radius > 1) {
                BlockPos.MutableBlockPos mutable = this.root.mutable();

                for (int i = 0; i < Math.min(10, this.getHeight()); ++i) {
                    if (worldGenLevel.getBlockState(mutable).is(Blocks.LAVA)) {
                        return false;
                    }

                    if (DripstoneIceUtils.isCircleMostlyEmbeddedInStone(worldGenLevel, windOffsetter.offset(mutable), this.radius)) {
                        this.root = mutable;
                        return true;
                    }

                    // Move further into the floor/ceiling if not sufficiently encased in stone
                    mutable.move(this.pointingUp ? Direction.DOWN : Direction.UP);
                }

                this.radius /= 2;
            }

            return false;
        }

        /**
         *
         * @param radialDistance x-z distance from the center
         * @return height at given radial distance
         */
        private int getHeightAtRadius(float radialDistance) {
            return (int)DripstoneIceUtils.getDripstoneHeight(radialDistance, this.radius, this.scale, this.bluntness);
        }

        void placeBlocks(WorldGenLevel worldGenLevel, Random random, LargeIceDripstoneFeature.WindOffsetter windOffsetter, float rareIceChance) {
            boolean hasRareIce = random.nextFloat() < rareIceChance;
            if (this.getHeightAtRadius(0) < 6) {
                hasRareIce = false;
            }

            for (int dx = -this.radius; dx <= this.radius; ++dx) {
                for (int dz = -this.radius; dz <= this.radius; ++dz) {
                    float distToCenter = Mth.sqrt((float)(dx * dx + dz * dz));
                    if (distToCenter <= (float)this.radius) {
                        int heightAtCurrPos = this.getHeightAtRadius(distToCenter);
                        if (heightAtCurrPos > 0) {
                            if (random.nextDouble() < 0.2) {
                                heightAtCurrPos = (int)((float)heightAtCurrPos * Mth.randomBetween(random, 0.8F, 1.0F));
                            }

                            BlockPos.MutableBlockPos mutable = this.root.offset(dx, 0, dz).mutable();
                            boolean placed = false;
                            int surfaceHeight = this.pointingUp ? worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;

                            for (int dy = 0; dy < heightAtCurrPos && mutable.getY() < surfaceHeight; ++dy) {
                                BlockPos blockPos = windOffsetter.offset(mutable);
                                if (DripstoneIceUtils.isEmptyOrWaterOrLava(worldGenLevel, blockPos)) {
                                    placed = true;
                                    Block block = Blocks.PACKED_ICE;

                                    if (hasRareIce) {
                                        double progress = (double)dy / (double)heightAtCurrPos;
                                        if (dx == 0 && dz == 0 && dy == (int)(heightAtCurrPos / 2.2)) {
                                            block = BlockModule.RARE_ICE.get();
                                        } else if (progress > 0.3 + random.nextDouble() * 0.04) {
                                            block = Blocks.ICE;
                                        }
                                    }

                                    worldGenLevel.setBlock(blockPos, block.defaultBlockState(), 2);
                                } else if (placed && worldGenLevel.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                                    break;
                                }

                                mutable.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                            }
                        }
                    }
                }
            }
        }

        boolean isSuitableForWind(LargeIceDripstoneConfiguration largeDripstoneConfiguration) {
            return this.radius >= largeDripstoneConfiguration.minRadiusForWind && this.bluntness >= (double)largeDripstoneConfiguration.minBluntnessForWind;
        }
    }

    static final class WindOffsetter {
        private final int originY;
        @Nullable
        private final Vec3 windSpeed;

        WindOffsetter(int originY, Random random, FloatProvider windSpeedProvider, FloatProvider angleProvider) {
            this.originY = originY;
            float speedAmp = windSpeedProvider.sample(random);
            float radAngle = angleProvider.sample(random);
            this.windSpeed = new Vec3(Mth.cos(radAngle) * speedAmp, 0.0, Mth.sin(radAngle) * speedAmp);
        }

        private WindOffsetter() {
            this.originY = 0;
            this.windSpeed = null;
        }

        static WindOffsetter noWind() {
            return new WindOffsetter();
        }

        BlockPos offset(BlockPos blockPos) {
            if (this.windSpeed == null) {
                return blockPos;
            } else {
                int dy = this.originY - blockPos.getY();
                Vec3 vec3 = this.windSpeed.scale(dy);
                return blockPos.offset(vec3.x, 0.0, vec3.z);
            }
        }
    }
}
