package com.yungnickyoung.minecraft.yungscavebiomes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;

import java.util.Map;

public class IceSheetBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty GLOWING = BlockStateProperties.LIT;

    public IceSheetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(GLOWING, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = super.getStateForPlacement(blockPlaceContext);
        if (blockState == null) return null;
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();

        // Query all adjacent blocks for glowing state
        for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
            Direction direction = entry.getKey();
            BooleanProperty property = entry.getValue();
            if (blockState.getValue(property)) {
                BlockPos adjacentPos = blockPos.relative(direction);
                Block adjacentBlock = level.getBlockState(adjacentPos).getBlock();
                boolean isAdjacentBlockOre = adjacentBlock instanceof OreBlock || adjacentBlock instanceof RedStoneOreBlock;
                if (isAdjacentBlockOre) {
                    return blockState.setValue(GLOWING, true);
                }
            }
        }

        return blockState;
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block neighborBlock, BlockPos neighborPos, boolean bl) {
        if (level.isClientSide) {
            return;
        }

        // Make ice sheet glow if attached to ore
        for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
            Direction direction = entry.getKey();
            BooleanProperty property = entry.getValue();
            if (blockState.getValue(property)) {
                BlockPos adjacentPos = blockPos.relative(direction);
                Block adjacentBlock = level.getBlockState(adjacentPos).getBlock();
                boolean isAdjacentBlockOre = adjacentBlock instanceof OreBlock || adjacentBlock instanceof RedStoneOreBlock;
                if (isAdjacentBlockOre) {
                    if (!blockState.getValue(GLOWING)) {
                        level.setBlock(blockPos, blockState.setValue(GLOWING, true), 2);
                    }
                    return;
                }
            }
        }

        // Make ice sheet stop glowing if not attached to ore
        if (blockState.getValue(GLOWING)) {
            level.setBlock(blockPos, blockState.setValue(GLOWING, false), 2);
        }
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        if (blockState.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GLOWING, WATERLOGGED);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return !blockPlaceContext.getItemInHand().is(Items.GLOW_LICHEN) || super.canBeReplaced(blockState, blockPlaceContext);
    }
}
