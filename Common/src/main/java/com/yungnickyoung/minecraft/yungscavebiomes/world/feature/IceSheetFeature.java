package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class IceSheetFeature extends Feature<IceSheetConfiguration> {
    public IceSheetFeature(Codec<IceSheetConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<IceSheetConfiguration> featurePlaceContext) {
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos originPos = featurePlaceContext.origin();
        IceSheetConfiguration config = featurePlaceContext.config();
        int radius = config.searchRange;
        BlockPos.MutableBlockPos blockPos = originPos.mutable();
        BlockPos.MutableBlockPos neighborPos = blockPos.mutable();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dy = y / (double) radius;
                    double dz = z / (double) radius;
                    if (dx * dx + dy * dy + dz * dz > 1.0) {
                        continue;
                    }

                    blockPos.setWithOffset(originPos, x, y, z);
                    BlockState blockState = worldGenLevel.getBlockState(blockPos);

                    // Only replace air or water
                    if (!isAirOrWater(blockState)) {
                        continue;
                    }

                    // Determine which faces are valid for the ice sheet
                    for (Direction direction : config.validDirections) {
                        neighborPos.setWithOffset(blockPos, direction);
                        BlockState neighborBlockState = worldGenLevel.getBlockState(neighborPos);

                        // Validate neighbor blockstate
                        if (neighborBlockState.is(BlockModule.ICE_SHEET_FEATURE_AVOID)) {
                            continue;
                        }

                        IceSheetBlock iceSheetBlock = (IceSheetBlock) BlockModule.ICE_SHEET.get();
                        BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(blockState, worldGenLevel, blockPos, direction);
                        if (updatedBlockState == null) {
                            continue;
                        }

                        blockState = updatedBlockState;
                    }

                    // If valid location for an ice sheet, place it and update the chunk
                    if (blockState.is(BlockModule.ICE_SHEET.get())) {
                        blockState = blockState.setValue(IceSheetBlock.GROWTH_DISTANCE, 3);
                        worldGenLevel.setBlock(blockPos, blockState, Block.UPDATE_ALL);
                        worldGenLevel.getChunk(blockPos).markPosForPostprocessing(blockPos);
                    }
                }
            }
        }

        return true;
    }

    private static boolean isAirOrWater(BlockState blockState) {
        return blockState.isAir() || blockState.is(Blocks.WATER);
    }
}
