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
import net.minecraft.world.level.material.Material;

import java.util.Set;

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
                    if (!isAirOrWater(currState)) {
                        continue;
                    }

                    for (Direction direction : config.validDirections) {
                        mutable.setWithOffset(currPos, direction);
                        BlockState neighborBlockState = worldGenLevel.getBlockState(mutable);

                        // Validate neighbor blockstate
                        if (invalid_blocks.contains(neighborBlockState.getBlock()) || !valid_materials.contains(neighborBlockState.getMaterial())) {
                            continue;
                        }

                        IceSheetBlock iceSheetBlock = (IceSheetBlock) BlockModule.ICE_SHEET.get();
                        BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(currState, worldGenLevel, currPos, direction);
                        if (updatedBlockState == null) {
                            continue;
                        }
                        updatedBlockState = updatedBlockState.setValue(IceSheetBlock.GROWTH_DISTANCE, 3);

                        currState = updatedBlockState;
                        worldGenLevel.setBlock(currPos, updatedBlockState, 3);
                        worldGenLevel.getChunk(currPos).markPosForPostprocessing(currPos);
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
