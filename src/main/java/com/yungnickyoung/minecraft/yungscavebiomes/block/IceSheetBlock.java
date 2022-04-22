package com.yungnickyoung.minecraft.yungscavebiomes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IceSheetBlock extends DirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final int AABB_THICKNESS = 1;
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, AABB_THICKNESS, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(16 - AABB_THICKNESS, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, AABB_THICKNESS);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 16 - AABB_THICKNESS, 16.0, 16.0, 16.0);
    protected static final VoxelShape DOWN_AABB = Block.box(0.0, 16 - AABB_THICKNESS, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape UP_AABB = Block.box(0.0, 0.0, 0.0, 16.0, AABB_THICKNESS, 16.0);

    public IceSheetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch (blockState.getValue(FACING)) {
            default -> {
                return NORTH_AABB;
            }
            case SOUTH -> {
                return SOUTH_AABB;
            }
            case WEST -> {
                return WEST_AABB;
            }
            case EAST -> {
                return EAST_AABB;
            }
            case UP -> {
                return UP_AABB;
            }
            case DOWN -> {
                return DOWN_AABB;
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        Direction direction = blockState.getValue(FACING);
        BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
        return levelReader.getBlockState(blockPos2).isFaceSturdy(levelReader, blockPos2, direction);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction == blockState.getValue(FACING).getOpposite() && !blockState.canSurvive(levelAccessor, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
        switch (pathComputationType) {
            case LAND, AIR -> {
                return blockState.getValue(FACING).getAxis() != Direction.Axis.Y;
            }
            case WATER -> {
                return blockState.getValue(WATERLOGGED);
            }
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Level levelAccessor = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        return this.defaultBlockState()
                .setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER)
                .setValue(FACING, blockPlaceContext.getClickedFace());
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
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
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
        if (blockState2.is(this)) {
            return true;
        }
        return super.skipRendering(blockState, blockState2, direction);
    }


    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }
}
