package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class IceSheetFeature extends Feature<IceSheetConfiguration> {
    private static final Set<Block> invalid_blocks = Set.of(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE);
    private static final Set<Material> valid_materials = Set.of(Material.DIRT, Material.STONE, Material.SAND, Material.CLAY, Material.GRASS);

    public IceSheetFeature(Codec<IceSheetConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<IceSheetConfiguration> featurePlaceContext) {
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos originPos = featurePlaceContext.origin();
        IceSheetConfiguration config = featurePlaceContext.config();
        int radius = config.searchRange;
        BlockPos.MutableBlockPos currPos = originPos.mutable();
        BlockPos.MutableBlockPos mutable = currPos.mutable();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dy = y / (double) radius;
                    double dz = z / (double) radius;
                    if (dx * dx + dy * dy + dz * dz > 1.0) {
                        continue;
                    }

                    currPos.setWithOffset(originPos, x, y, z);
                    BlockState currState = worldGenLevel.getBlockState(currPos);

                    // Only replace air or water
                    if (!isAirOrWater(worldGenLevel.getBlockState(currPos))) {
                        continue;
                    }

                    for (Direction direction : config.validDirections) {
                        mutable.setWithOffset(currPos, direction);
                        BlockState neighborBlockState = worldGenLevel.getBlockState(mutable);

                        // Validate neighbor blockstate
                        if (invalid_blocks.contains(neighborBlockState.getBlock()) || !valid_materials.contains(neighborBlockState.getMaterial())) {
                            continue;
                        }

                        IceSheetBlock iceSheetBlock = (IceSheetBlock) YCBModBlocks.ICE_SHEET;
                        BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(currState, worldGenLevel, currPos, direction);
                        if (updatedBlockState == null) {
                            continue;
                        }

                        currState = updatedBlockState;
                        worldGenLevel.setBlock(currPos, updatedBlockState, 3);
                        worldGenLevel.getChunk(currPos).markPosForPostprocessing(currPos);
                    }
                }
            }
        }

        return true;
//
//        if (placeGlowLichenIfPossible(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), config, random, config.validDirections)) {
//            return true;
//        }
//
//        BlockPos.MutableBlockPos currPos = blockPos.currPos();
//
//        block0:
//        for (Direction direction : config.validDirections) {
//            currPos.set(blockPos);
//            List<Direction> list2 = GlowLichenFeature.getShuffledDirectionsExcept(config, random, direction.getOpposite());
//            for (int i = 0; i < config.searchRange; ++i) {
//                currPos.setWithOffset(blockPos, direction);
//                BlockState blockState = worldGenLevel.getBlockState(currPos);
//                if (!GlowLichenFeature.isAirOrWater(blockState) && !blockState.is(Blocks.GLOW_LICHEN)) continue block0;
//                if (!GlowLichenFeature.placeGlowLichenIfPossible(worldGenLevel, currPos, blockState, config, random, list2))
//                    continue;
//                return true;
//            }
//        }
//        return false;
    }

    public static boolean placeGlowLichenIfPossible(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockState blockState, GlowLichenConfiguration config, Random random, List<Direction> directions) {
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        for (Direction direction : directions) {
            BlockState blockState2 = worldGenLevel.getBlockState(mutable.setWithOffset(blockPos, direction));
            if (!config.canBePlacedOn.contains(blockState2.getBlock())) continue;
            IceSheetBlock iceSheetBlock = (IceSheetBlock) YCBModBlocks.ICE_SHEET;
            BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(blockState, worldGenLevel, blockPos, direction);
            if (updatedBlockState == null) {
                return false;
            }
            worldGenLevel.setBlock(blockPos, updatedBlockState, 3);
            worldGenLevel.getChunk(blockPos).markPosForPostprocessing(blockPos);
            return true;
        }
        return false;
    }

    public static List<Direction> getShuffledDirectionsExcept(GlowLichenConfiguration glowLichenConfiguration, Random random, Direction direction) {
        List<Direction> list = glowLichenConfiguration.validDirections.stream().filter(direction2 -> direction2 != direction).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list;
    }

    private static boolean isAirOrWater(BlockState blockState) {
        return blockState.isAir() || blockState.is(Blocks.WATER);
    }
}
