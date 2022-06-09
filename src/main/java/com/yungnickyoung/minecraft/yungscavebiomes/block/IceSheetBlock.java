package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

public class IceSheetBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty GLOWING = BlockStateProperties.LIT;

    public IceSheetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(GLOWING, false)
                .setValue(WATERLOGGED, false));
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        // Melt
        if (random.nextFloat() < 0.25f && serverLevel.getBrightness(LightLayer.BLOCK, blockPos) > 12 - blockState.getLightBlock(serverLevel, blockPos)) {
            serverLevel.removeBlock(blockPos, false);
        }

        // Grow
        if (random.nextFloat() < 0.05f) {
            spreadFromRandomFaceTowardRandomDirection(blockState, serverLevel, blockPos, random);
        }
    }

    @Override
    public BlockState updateShape(BlockState thisState, Direction directionToNeighbor, BlockState neighborState, LevelAccessor levelAccessor, BlockPos thisPos, BlockPos neighborPos) {
        if (thisState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(thisPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(thisState, directionToNeighbor, neighborState, levelAccessor, thisPos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction neighborDirection) {
        if (!this.isFaceSupported(neighborDirection)) {
            return null;
        }

        BlockState updatedState;
        BlockPos neighborPos = blockPos.relative(neighborDirection);

        if (blockState.is(this)) {
            updatedState = blockState;
        } else {
            updatedState = blockState.getFluidState().isSourceOfType(Fluids.WATER)
                    ? this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)
                    : this.defaultBlockState();
        }

        if (canAttachTo(blockGetter, neighborDirection, neighborPos, blockGetter.getBlockState(neighborPos))) {
            updatedState = updatedState.setValue(getFaceProperty(neighborDirection), true);
            Block adjacentBlock = blockGetter.getBlockState(neighborPos).getBlock();
            if (adjacentBlock instanceof OreBlock || adjacentBlock instanceof RedStoneOreBlock) {
                updatedState = updatedState.setValue(GLOWING, true);
            }
        } else {
            updatedState = null;
        }

        return updatedState;
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

    private static boolean canAttachTo(BlockGetter blockGetter, Direction direction, BlockPos blockPos, BlockState blockState) {
        return Block.isFaceFull(blockState.getCollisionShape(blockGetter, blockPos), direction.getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return  Block.box(0, 0, 0, 0, 0, 0);
    }

    public boolean spreadFromRandomFaceTowardRandomDirection(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        List<Direction> list = Lists.newArrayList(DIRECTIONS);
        Collections.shuffle(list);
        return list.stream()
                .filter((direction) -> hasFace(blockState, direction))
                .anyMatch((direction) -> this.spreadFromFaceTowardRandomDirection(blockState, serverLevel, blockPos, direction, random, false));
    }

    public boolean spreadFromFaceTowardRandomDirection(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, Random random, boolean bl) {
        List<Direction> list = Arrays.asList(DIRECTIONS);
        Collections.shuffle(list, random);
        return list.stream()
                .anyMatch((direction2) -> this.spreadFromFaceTowardDirection(blockState, levelAccessor, blockPos, direction, direction2, bl));
    }

    public boolean spreadFromFaceTowardDirection(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, Direction direction2, boolean bl) {
        Optional<Pair<BlockPos, Direction>> optional = this.getSpreadFromFaceTowardDirection(blockState, levelAccessor, blockPos, direction, direction2);
        if (optional.isPresent()) {
            Pair<BlockPos, Direction> pair = optional.get();
            return this.spreadToFace(levelAccessor, pair.getFirst(), pair.getSecond(), bl);
        } else {
            return false;
        }
    }

    private Optional<Pair<BlockPos, Direction>> getSpreadFromFaceTowardDirection(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, Direction direction2) {
        if (direction2.getAxis() != direction.getAxis() && hasFace(blockState, direction) && !hasFace(blockState, direction2)) {
            if (this.canSpreadToFace(levelAccessor, blockPos, direction2)) {
                return Optional.of(Pair.of(blockPos, direction2));
            } else {
                BlockPos blockPos2 = blockPos.relative(direction2);
                if (this.canSpreadToFace(levelAccessor, blockPos2, direction)) {
                    return Optional.of(Pair.of(blockPos2, direction));
                } else {
                    BlockPos blockPos3 = blockPos2.relative(direction);
                    Direction direction3 = direction2.getOpposite();
                    return this.canSpreadToFace(levelAccessor, blockPos3, direction3) ? Optional.of(Pair.of(blockPos3, direction3)) : Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }

    private boolean canSpreadToFace(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction) {
        BlockState blockState = levelAccessor.getBlockState(blockPos);
        if (!this.canSpreadInto(levelAccessor, blockState, blockPos)) {
            return false;
        } else {
            BlockState blockState2 = this.getStateForPlacement(blockState, levelAccessor, blockPos, direction);
            return blockState2 != null;
        }
    }

    private boolean spreadToFace(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, boolean bl) {
        BlockState blockState = levelAccessor.getBlockState(blockPos);
        BlockState blockState2 = this.getStateForPlacement(blockState, levelAccessor, blockPos, direction);
        if (blockState2 != null) {
            if (bl) {
                levelAccessor.getChunk(blockPos).markPosForPostprocessing(blockPos);
            }

            return levelAccessor.setBlock(blockPos, blockState2, 2);
        } else {
            return false;
        }
    }

    private boolean canSpreadInto(LevelAccessor levelAccessor, BlockState blockState, BlockPos blockPos) {
        return (blockState.isAir() || blockState.is(this) || blockState.is(Blocks.WATER) && blockState.getFluidState().isSource())
                && levelAccessor.getBrightness(LightLayer.BLOCK, blockPos) <= 12 - blockState.getLightBlock(levelAccessor, blockPos) ;
    }

    private static boolean hasFace(BlockState blockState, Direction direction) {
        BooleanProperty booleanProperty = getFaceProperty(direction);
        return blockState.hasProperty(booleanProperty) && blockState.getValue(booleanProperty);
    }
}
