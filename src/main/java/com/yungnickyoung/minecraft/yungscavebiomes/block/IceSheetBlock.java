package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBlocks;
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
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.Set;

public class IceSheetBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty GLOWING = BlockStateProperties.LIT;

    private static final ImmutableMap<BooleanProperty, Direction> directionMap = ImmutableMap.<BooleanProperty, Direction>builder()
            .put(UP, Direction.DOWN)
            .put(DOWN, Direction.UP)
            .put(NORTH, Direction.SOUTH)
            .put(SOUTH, Direction.NORTH)
            .put(EAST, Direction.WEST)
            .put(WEST, Direction.EAST)
            .build();

    protected static final int AABB_THICKNESS = 1;
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, AABB_THICKNESS, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(16 - AABB_THICKNESS, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, AABB_THICKNESS);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 16 - AABB_THICKNESS, 16.0, 16.0, 16.0);
    protected static final VoxelShape DOWN_AABB = Block.box(0.0, 16 - AABB_THICKNESS, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape UP_AABB = Block.box(0.0, 0.0, 0.0, 16.0, AABB_THICKNESS, 16.0);

    private final Map<BlockState, VoxelShape> shapeByIndex;

    public IceSheetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, true)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(GLOWING, false)
                .setValue(WATERLOGGED, false));
        this.shapeByIndex = this.makeShapes();
    }

    private Map<BlockState, VoxelShape> makeShapes() {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        for (boolean up : UP.getPossibleValues()) {
            for (boolean down : DOWN.getPossibleValues()) {
                for (boolean north : NORTH.getPossibleValues()) {
                    for (boolean south : SOUTH.getPossibleValues()) {
                        for (boolean east : EAST.getPossibleValues()) {
                            for (boolean west : WEST.getPossibleValues()) {
                                for (boolean glowing : GLOWING.getPossibleValues()) {
                                    for (boolean waterlogged : WATERLOGGED.getPossibleValues()) {
                                        VoxelShape voxelShape = Shapes.empty();
                                        if (up) voxelShape = Shapes.or(voxelShape, UP_AABB);
                                        if (down) voxelShape = Shapes.or(voxelShape, DOWN_AABB);
                                        if (north) voxelShape = Shapes.or(voxelShape, NORTH_AABB);
                                        if (south) voxelShape = Shapes.or(voxelShape, SOUTH_AABB);
                                        if (east) voxelShape = Shapes.or(voxelShape, EAST_AABB);
                                        if (west) voxelShape = Shapes.or(voxelShape, WEST_AABB);
                                        BlockState blockState = this.defaultBlockState()
                                                .setValue(UP, up)
                                                .setValue(DOWN, down)
                                                .setValue(NORTH, north)
                                                .setValue(SOUTH, south)
                                                .setValue(EAST, east)
                                                .setValue(WEST, west)
                                                .setValue(GLOWING, glowing)
                                                .setValue(WATERLOGGED, waterlogged);
                                        builder.put(blockState, voxelShape);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.shapeByIndex.get(blockState);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        // Ensure all faces are attached to sturdy blocks
        for (Map.Entry<BooleanProperty, Direction> entry : directionMap.entrySet()) {
            BooleanProperty property = entry.getKey();
            Direction direction = entry.getValue();
            if (blockState.getValue(property)) {
                BlockPos adjacentPos = blockPos.relative(direction);
                if (!levelReader.getBlockState(adjacentPos).isFaceSturdy(levelReader, adjacentPos, direction.getOpposite())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (!blockState.canSurvive(levelAccessor, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
        switch (pathComputationType) {
            case LAND, AIR -> {
                return !blockState.getValue(NORTH) && !blockState.getValue(SOUTH);
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
        Direction facing = blockPlaceContext.getClickedFace();
        Block neighborBlock = levelAccessor.getBlockState(blockPos.relative(facing.getOpposite())).getBlock();
        boolean isCoveringOre = neighborBlock instanceof OreBlock || neighborBlock instanceof RedStoneOreBlock;

        boolean waterlogged = false;
        boolean glowing = false;
        boolean up = false;
        boolean down = false;
        boolean north = false;
        boolean south = false;
        boolean east = false;
        boolean west = false;

        if (levelAccessor.getBlockState(blockPos).getBlock() == YCBModBlocks.ICE_SHEET) {
            BlockState currState = levelAccessor.getBlockState(blockPos);
            waterlogged = currState.getValue(WATERLOGGED);
            glowing = currState.getValue(GLOWING);
            up = currState.getValue(UP);
            down = currState.getValue(DOWN);
            north = currState.getValue(NORTH);
            south = currState.getValue(SOUTH);
            east = currState.getValue(EAST);
            west = currState.getValue(WEST);
        }

        return this.defaultBlockState()
                .setValue(WATERLOGGED, waterlogged || levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER)
                .setValue(GLOWING, glowing || isCoveringOre)
                .setValue(UP, up || facing == Direction.UP)
                .setValue(DOWN, down || facing == Direction.DOWN)
                .setValue(NORTH, north || facing == Direction.NORTH)
                .setValue(SOUTH, south || facing == Direction.SOUTH)
                .setValue(EAST, east || facing == Direction.EAST)
                .setValue(WEST, west || facing == Direction.WEST);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block neighborBlock, BlockPos neighborPos, boolean bl) {
        if (level.isClientSide) {
            return;
        }

        // Make ice sheet glow if attached to ore
        for (Map.Entry<BooleanProperty, Direction> entry : directionMap.entrySet()) {
            BooleanProperty property = entry.getKey();
            Direction direction = entry.getValue();
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
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180 -> {
                return blockState
                        .setValue(NORTH, blockState.getValue(SOUTH))
                        .setValue(SOUTH, blockState.getValue(NORTH))
                        .setValue(EAST, blockState.getValue(WEST))
                        .setValue(WEST, blockState.getValue(EAST));
            }
            case COUNTERCLOCKWISE_90 -> {
                return blockState
                        .setValue(NORTH, blockState.getValue(EAST))
                        .setValue(EAST, blockState.getValue(SOUTH))
                        .setValue(SOUTH, blockState.getValue(WEST))
                        .setValue(WEST, blockState.getValue(NORTH));
            }
            case CLOCKWISE_90 -> {
                return blockState
                        .setValue(NORTH, blockState.getValue(WEST))
                        .setValue(EAST, blockState.getValue(NORTH))
                        .setValue(SOUTH, blockState.getValue(EAST))
                        .setValue(WEST, blockState.getValue(SOUTH));
            }
        }
        return blockState;
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT -> {
                return blockState
                        .setValue(NORTH, blockState.getValue(SOUTH))
                        .setValue(SOUTH, blockState.getValue(NORTH));
            }
            case FRONT_BACK -> {
                return blockState
                        .setValue(EAST, blockState.getValue(WEST))
                        .setValue(WEST, blockState.getValue(EAST));
            }
        }
        return super.mirror(blockState, mirror);
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
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST, GLOWING, WATERLOGGED);
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

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getItemInHand().is(this.asItem())) {
            return true;
        }
        return super.canBeReplaced(blockState, blockPlaceContext);
    }
}
