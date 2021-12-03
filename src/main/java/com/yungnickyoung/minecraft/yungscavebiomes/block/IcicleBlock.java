package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class IcicleBlock extends Block implements Fallable {
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;

    private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape TIP_SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);

    public IcicleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(THICKNESS, DripstoneThickness.TIP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(THICKNESS);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isValidPosition(levelReader, blockPos);
    }

    @Override
    public BlockState updateShape(BlockState currState, Direction neighborDirection, BlockState neighborBlockState, LevelAccessor levelAccessor, BlockPos currPos, BlockPos neighborPos) {
        // We only care about vertical direction updates for icicles
        if (neighborDirection != Direction.UP && neighborDirection != Direction.DOWN) {
            return currState;
        }

        // No need to update if tick on this block is already scheduled
        if (levelAccessor.getBlockTicks().hasScheduledTick(currPos, this)) {
            return currState;
        }

        // Schedule fall tick if block above is no longer valid support
        if (neighborDirection == Direction.UP && !this.canSurvive(currState, levelAccessor, currPos)) {
            this.scheduleFallingTicks(currState, levelAccessor, currPos);
            return currState;
        }

        DripstoneThickness thickness = calculateIcicleThickness(levelAccessor, currPos);
        return currState.setValue(THICKNESS, thickness);
    }

    /**
     * Similar to pointed dripstone, icicles can be destroyed via projectile.
     * But while dripstone can only be destroyed via trident, icicles can be destroyed by any projectile that extends
     * AbstractArrow (which includes arrows, spectral arrows, and tridents).
     * It also only requires a projectile speed of 0.4, as opposed to 0.6 for dripstone.
     */
    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, blockPos) && projectile instanceof AbstractArrow && projectile.getDeltaMovement().length() > 0.4) {
            level.destroyBlock(blockPos, true);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        spawnFallingIcicle(blockState, serverLevel, blockPos);
    }

    /**
     * Randomly attempt to grow icicle.
     */
    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        if (random.nextFloat() < 0.011377778f && isTop(blockState, serverLevel, blockPos)) {
            tryToGrowIcicle(blockState, serverLevel, blockPos);
        }
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Level levelAccessor = blockPlaceContext.getLevel();
        if (!isValidPosition(levelAccessor, blockPlaceContext.getClickedPos())) return null;

        DripstoneThickness thickness = calculateIcicleThickness(levelAccessor, blockPlaceContext.getClickedPos());
        if (thickness == null) return null;

        return this.defaultBlockState().setValue(THICKNESS, thickness);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        DripstoneThickness thickness = blockState.getValue(THICKNESS);
        VoxelShape voxelShape;
        switch (thickness) {
            case BASE -> voxelShape = BASE_SHAPE;
            case MIDDLE -> voxelShape = MIDDLE_SHAPE;
            case FRUSTUM -> voxelShape = FRUSTUM_SHAPE;
            default -> voxelShape = TIP_SHAPE;
        }

        Vec3 vec3 = blockState.getOffset(blockGetter, blockPos);
        return voxelShape.move(vec3.x, 0.0, vec3.z);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.125f;
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos blockPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            level.levelEvent(1045, blockPos, 0);
        }
    }

    @Override
    public DamageSource getFallDamageSource() {
        return DamageSource.FALLING_STALACTITE;
    }

    @Override
    public Predicate<Entity> getHurtsEntitySelector() {
        return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
    }

    /**
     * Schedules ticks for an entire icicle, rom the tip up.
     * Should only be called after determining the icicle is no longer valid.
     */
    private void scheduleFallingTicks(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
        BlockPos tipPos = findTip(blockState, levelAccessor, blockPos, Integer.MAX_VALUE);
        if (tipPos == null) return;

        BlockPos.MutableBlockPos mutable = tipPos.mutable();
        mutable.move(Direction.DOWN);
        BlockState blockBelowTip = levelAccessor.getBlockState(mutable);

        // If a whole block is directly below tip, no need to schedule tick on tip - just destroy it
        if (blockBelowTip.getCollisionShape(levelAccessor, mutable, CollisionContext.empty()).max(Direction.Axis.Y) >= 1.0 || blockBelowTip.is(Blocks.POWDER_SNOW)) {
            levelAccessor.destroyBlock(tipPos, true);
            mutable.move(Direction.UP);
        }

        mutable.move(Direction.UP);

        // Schedule ticks on the rest of the stalactite
        while (levelAccessor.getBlockState(mutable).is(YCBModBlocks.ICICLE)) {
            levelAccessor.scheduleTick(mutable, this, 2);
            mutable.move(Direction.UP);
        }
    }

    @Nullable
    private static BlockPos findTip(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, int searchDistance) {
        if (isTip(blockState)) return blockPos;
        return findBlockVertical(levelAccessor, blockPos, Direction.DOWN, state -> state.is(YCBModBlocks.ICICLE), IcicleBlock::isTip, searchDistance).orElse(null);
    }

    private static boolean isValidPosition(LevelReader levelReader, BlockPos blockPos) {
        BlockPos posAbove = blockPos.relative(Direction.UP);
        BlockState blockStateAbove = levelReader.getBlockState(posAbove);
        return blockStateAbove.isFaceSturdy(levelReader, posAbove, Direction.DOWN) || blockStateAbove.is(YCBModBlocks.ICICLE);
    }

    private static boolean isTip(BlockState blockState) {
        if (!blockState.is(YCBModBlocks.ICICLE)) return false;
        DripstoneThickness thickness = blockState.getValue(THICKNESS);
        return thickness == DripstoneThickness.TIP;
    }

    private static boolean isTop(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return blockState.is(YCBModBlocks.ICICLE) && !levelReader.getBlockState(blockPos.above()).is(YCBModBlocks.ICICLE);
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor levelAccessor,
                                                        BlockPos startPos,
                                                        Direction searchDirection,
                                                        Predicate<BlockState> predicate,
                                                        Predicate<BlockState> stoppingPredicate,
                                                        int searchDistance
    ) {
        BlockPos.MutableBlockPos mutable = startPos.mutable();
        for (int i = 1; i < searchDistance; ++i) {
            mutable.move(searchDirection);
            BlockState blockState = levelAccessor.getBlockState(mutable);

            if (stoppingPredicate.test(blockState)) {
                return Optional.of(mutable.immutable());
            }

            if (!predicate.test(blockState) || levelAccessor.isOutsideBuildHeight(mutable.getY())) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private static DripstoneThickness calculateIcicleThickness(LevelReader levelReader, BlockPos blockPos) {
        BlockState blockBelow = levelReader.getBlockState(blockPos.relative(Direction.DOWN));
        BlockState blockAbove = levelReader.getBlockState(blockPos.relative(Direction.UP));

        if (!blockBelow.is(YCBModBlocks.ICICLE)) return DripstoneThickness.TIP;
        if (blockBelow.getValue(THICKNESS) == DripstoneThickness.TIP) return DripstoneThickness.FRUSTUM;
        if (!blockAbove.is(YCBModBlocks.ICICLE)) return DripstoneThickness.BASE;
        return DripstoneThickness.MIDDLE;
    }

    private static void spawnFallingIcicle(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
        Vec3 vec3 = Vec3.atBottomCenterOf(blockPos);
        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(serverLevel, vec3.x, vec3.y, vec3.z, blockState);

        if (isTip(blockState)) {
            int icicleSize = getIcicleSizeFromTip(serverLevel, blockPos, 5);
            float damagePerBlock = .5f * (float) icicleSize;
            fallingBlockEntity.setHurtsEntities(damagePerBlock, 20);
        }

        serverLevel.addFreshEntity(fallingBlockEntity);
    }

    /**
     * Should be called with the TOP block in the icicle ONLY!
     */
    private static void tryToGrowIcicle(BlockState topIcicleBlock, ServerLevel serverLevel, BlockPos topIcicleBlockPos) {
        // Block above top block must be flowing water
        BlockState blockAbove = serverLevel.getBlockState(topIcicleBlockPos.above());
        if (!isFlowingWaterAbove(blockAbove, serverLevel.getBlockState(topIcicleBlockPos.above(2)))) return;

        // Find tip for this icicle
        BlockPos tipPos = findTip(topIcicleBlock, serverLevel, topIcicleBlockPos, 6); // Icicles can grow up to 6 long naturally
        if (tipPos == null || !isTip(serverLevel.getBlockState(tipPos))) return;

        // Make sure tip block is valid and has room to grow
        if (doesTipHaveRoomToGrow(serverLevel, tipPos)) growIcicle(serverLevel, tipPos);
    }

    /**
     * Icicles grow if flowing water is above the top (NOT source water)
     */
    private static boolean isFlowingWaterAbove(BlockState blockState, BlockState blockStateAbove) {
        return blockState.is(YCBModBlocks.ICICLE) && blockStateAbove.is(Blocks.WATER) && !blockStateAbove.getFluidState().isSource();
    }

    private static boolean doesTipHaveRoomToGrow(ServerLevel serverLevel, BlockPos tipPos) {
        BlockPos belowTipPos = tipPos.relative(Direction.DOWN);
        BlockState belowTipBlock = serverLevel.getBlockState(belowTipPos);

        if (!belowTipBlock.getFluidState().isEmpty()) return false;
        return belowTipBlock.isAir();
    }

    /**
     * Should be called with the BlockPos of the TIP that we want to grow.
     * Note that this method performs no validation on the block below the tip,
     * nor does it validate the tip block itself.
     */
    private static void growIcicle(ServerLevel serverLevel, BlockPos tipPos) {
        BlockPos belowTipBlock = tipPos.below();
        createIcicle(serverLevel, belowTipBlock, DripstoneThickness.TIP);
    }

    private static void createIcicle(LevelAccessor levelAccessor, BlockPos blockPos, DripstoneThickness thickness) {
        BlockState blockState = YCBModBlocks.ICICLE.defaultBlockState().setValue(THICKNESS, thickness);
        levelAccessor.setBlock(blockPos, blockState, 3);
    }

    private static int getIcicleSizeFromTip(ServerLevel serverLevel, BlockPos tipPos, int maxLength) {
        int size;
        BlockPos.MutableBlockPos mutable = tipPos.mutable().move(Direction.UP);

        for (size = 1; size < maxLength && serverLevel.getBlockState(mutable).is(YCBModBlocks.ICICLE); ++size) {
            mutable.move(Direction.UP);
        }
        return size;
    }
}
