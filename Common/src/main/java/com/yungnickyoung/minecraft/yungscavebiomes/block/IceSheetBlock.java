package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class IceSheetBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty GLOWING = BlockStateProperties.LIT;
    public static final IntegerProperty GROWTH_DISTANCE = BlockStateProperties.AGE_3;

    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public IceSheetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(GLOWING, false)
                .setValue(WATERLOGGED, false)
                .setValue(GROWTH_DISTANCE, 0));
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        // Melt
        if (random.nextFloat() < 0.25f && serverLevel.getBrightness(LightLayer.BLOCK, blockPos) > 12 - blockState.getLightBlock(serverLevel, blockPos)) {
            serverLevel.removeBlock(blockPos, false);
        }

        // Grow
        if (random.nextFloat() < 0.05f) {
            spreadFromRandomFaceWithinBlock(blockState, serverLevel, blockPos, random);
            if (blockState.getValue(GROWTH_DISTANCE) > 0) {
                spreadFromRandomFaceTowardRandomDirection(blockState, serverLevel, blockPos, random);
            }
        }
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, blockPos) > 12 - blockState.getLightBlock(level, blockPos) && random.nextFloat() < 0.2f) {
            // TODO - melting particle
//            Vec3 vec3 = blockState.getOffset(level, blockPos);
//            double x = (double) blockPos.getX() + 0.5 + vec3.x;
//            double y = (double) blockPos.getY() + vec3.y;
//            double z = (double) blockPos.getZ() + 0.5 + vec3.z;
//            level.addParticle(ParticleTypes.LANDING_HONEY, x, y, z, 0.0, 0.0, 0.0);
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
            BlockState adjacentBlock = blockGetter.getBlockState(neighborPos);
            if (adjacentBlock.is(BlockModule.CREEPING_ICE_GLOWS_ON)) {
                updatedState = updatedState.setValue(GLOWING, true);
            }
        } else {
            updatedState = null;
        }

        return updatedState;
    }

    @Override
    public @NotNull MultifaceSpreader getSpreader() {
        return this.spreader;
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
                BlockState adjacentBlock = level.getBlockState(adjacentPos);
                if (adjacentBlock.is(BlockModule.CREEPING_ICE_GLOWS_ON)) {
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
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GLOWING, WATERLOGGED, GROWTH_DISTANCE);
    }
//
//    private static boolean canAttachTo(BlockGetter blockGetter, Direction direction, BlockPos blockPos, BlockState blockState) {
//        return Block.isFaceFull(blockState.getCollisionShape(blockGetter, blockPos), direction.getOpposite());
//    }

//    @Override
//    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
//        return  Block.box(0, 0, 0, 0, 0, 0);
//    }

    public boolean spreadFromRandomFaceWithinBlock(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        List<Direction> possibleSourceDirections = Lists.newArrayList(DIRECTIONS).stream()
                .filter(direction -> hasFace(blockState, direction))
                .collect(Collectors.toList());
        Collections.shuffle(possibleSourceDirections);

        for (Direction possibleSourceDirection : possibleSourceDirections) {
            List<Direction> possibleTargetDirections = Lists.newArrayList(DIRECTIONS).stream()
                    .filter(direction -> direction.getAxis() != possibleSourceDirection.getAxis() && !blockState.getValue(getFaceProperty(direction)))
                    .collect(Collectors.toList());
            Collections.shuffle(possibleTargetDirections);

            for (Direction targetDirection : possibleTargetDirections) {
                BlockPos neighborPos = blockPos.relative(targetDirection);

                if (canAttachTo(serverLevel, targetDirection, neighborPos, serverLevel.getBlockState(neighborPos))) {
                    BlockState updatedState = blockState.setValue(getFaceProperty(targetDirection), true);
                    BlockState adjacentBlock = serverLevel.getBlockState(neighborPos);
                    if (adjacentBlock.is(BlockModule.CREEPING_ICE_GLOWS_ON)) {
                        updatedState = updatedState.setValue(GLOWING, true);
                    }
                    serverLevel.setBlock(blockPos, updatedState, 2);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean spreadFromRandomFaceTowardRandomDirection(BlockState spreadOriginBlockState, ServerLevel serverLevel, BlockPos spreadOriginBlockPos, RandomSource random) {
        List<Direction> directions = Lists.newArrayList(DIRECTIONS);
        Collections.shuffle(directions);
        return directions.stream()
                .filter((direction) -> hasFace(spreadOriginBlockState, direction))
                .anyMatch((direction) -> this.spreadFromFaceTowardRandomDirection(spreadOriginBlockState, serverLevel, spreadOriginBlockPos, direction, random, false));
    }

    public boolean spreadFromFaceTowardRandomDirection(BlockState spreadOriginBlockState, LevelAccessor levelAccessor, BlockPos spreadOriginBlockPos, Direction faceDirection, RandomSource random, boolean bl) {
        List<Direction> list = Arrays.asList(DIRECTIONS);
        Collections.shuffle(list, new Random(random.nextLong()));
        return list.stream()
                .anyMatch((targetDirection) -> this.spreadFromFaceTowardDirection(spreadOriginBlockState, levelAccessor, spreadOriginBlockPos, faceDirection, targetDirection, bl));
    }

    public boolean spreadFromFaceTowardDirection(BlockState spreadOriginBlockState, LevelAccessor levelAccessor, BlockPos spreadOriginBlockPos, Direction faceDirection, Direction targetDirection, boolean bl) {
        Optional<Pair<BlockPos, Direction>> optional = this.getSpreadFromFaceTowardDirection(spreadOriginBlockState, levelAccessor, spreadOriginBlockPos, faceDirection, targetDirection);
        if (optional.isPresent()) {
            Pair<BlockPos, Direction> pair = optional.get();
            return this.spreadToFace(levelAccessor, pair.getFirst(), pair.getSecond(), bl, spreadOriginBlockState);
        } else {
            return false;
        }
    }

    private Optional<Pair<BlockPos, Direction>> getSpreadFromFaceTowardDirection(BlockState spreadOriginBlockState, LevelAccessor levelAccessor, BlockPos spreadOriginBlockPos, Direction faceDirection, Direction targetDirection) {
        if (targetDirection.getAxis() != faceDirection.getAxis() && hasFace(spreadOriginBlockState, faceDirection) && !hasFace(spreadOriginBlockState, targetDirection)) {
            if (this.canSpreadToFace(levelAccessor, spreadOriginBlockPos, targetDirection, spreadOriginBlockState)) {
                return Optional.of(Pair.of(spreadOriginBlockPos, targetDirection));
            } else {
                BlockPos blockPos2 = spreadOriginBlockPos.relative(targetDirection);
                if (this.canSpreadToFace(levelAccessor, blockPos2, faceDirection, spreadOriginBlockState)) {
                    return Optional.of(Pair.of(blockPos2, faceDirection));
                } else {
                    BlockPos blockPos3 = blockPos2.relative(faceDirection);
                    Direction direction3 = targetDirection.getOpposite();
                    return this.canSpreadToFace(levelAccessor, blockPos3, direction3, spreadOriginBlockState) ? Optional.of(Pair.of(blockPos3, direction3)) : Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }

    private boolean canSpreadToFace(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, BlockState spreadOriginBlockState) {
        BlockState blockState = levelAccessor.getBlockState(blockPos);
        if (!this.canSpreadInto(levelAccessor, blockState, blockPos, spreadOriginBlockState)) {
            return false;
        } else {
            BlockState blockState2 = this.getStateForPlacement(blockState, levelAccessor, blockPos, direction);
            return blockState2 != null;
        }
    }

    private boolean spreadToFace(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, boolean bl, BlockState spreadOriginBlockState) {
        if (!spreadOriginBlockState.hasProperty(GROWTH_DISTANCE)) {
            return false;
        }

        BlockState oldBlockState = levelAccessor.getBlockState(blockPos);
        BlockState newBlockState = this.getStateForPlacement(oldBlockState, levelAccessor, blockPos, direction);
        if (newBlockState != null) {
            newBlockState = newBlockState.setValue(GROWTH_DISTANCE, spreadOriginBlockState.getValue(GROWTH_DISTANCE) - 1);

            if (bl) {
                levelAccessor.getChunk(blockPos).markPosForPostprocessing(blockPos);
            }

            return levelAccessor.setBlock(blockPos, newBlockState, 2);
        } else {
            return false;
        }
    }

    private boolean canSpreadInto(LevelAccessor levelAccessor, BlockState blockState, BlockPos blockPos, BlockState spreadOriginBlockState) {
        return (blockState.isAir() || (blockState.is(this) && blockState.getValue(GROWTH_DISTANCE) < spreadOriginBlockState.getValue(GROWTH_DISTANCE)) || blockState.is(Blocks.WATER) && blockState.getFluidState().isSource())
                && levelAccessor.getBrightness(LightLayer.BLOCK, blockPos) <= 12 - blockState.getLightBlock(levelAccessor, blockPos);
    }

//    private static boolean hasFace(BlockState blockState, Direction direction) {
//        BooleanProperty booleanProperty = getFaceProperty(direction);
//        return blockState.hasProperty(booleanProperty) && blockState.getValue(booleanProperty);
//    }
}
