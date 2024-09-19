package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.AbstractCauldronBlockAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class IcicleBlock extends Block implements Fallable, SimpleWaterloggedBlock {
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape TIP_SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);

    public IcicleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(THICKNESS, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isValidPosition(levelReader, blockPos);
    }

    @Override
    public BlockState updateShape(BlockState currState, Direction neighborDirection, BlockState neighborBlockState,
                                  LevelAccessor levelAccessor, BlockPos currPos, BlockPos neighborPos) {
        // Schedule fluid tick if waterlogged
        if (currState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

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
            levelAccessor.scheduleTick(currPos, this, 2);
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
        maybeFillCauldron(blockState, serverLevel, blockPos, random.nextFloat());
        if (random.nextFloat() < 0.011377778f && isTop(blockState, serverLevel, blockPos)) {
            tryToGrowIcicle(blockState, serverLevel, blockPos);
        }
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        if (!canDrip(blockState)) {
            return;
        }

        float chance = random.nextFloat();
        if (chance > 0.12f) {
            return;
        }

        getFluidAboveIcicle(level, blockPos)
                .filter(fluid -> chance < 0.02f || canFillCauldron(fluid))
                .ifPresent(fluid -> spawnDripParticle(level, blockPos, blockState, fluid));
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

        // Determine waterlogged status
        boolean isWaterlogged = levelAccessor.getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER;

        // Determine thickness
        DripstoneThickness thickness = calculateIcicleThickness(levelAccessor, blockPlaceContext.getClickedPos());
        if (thickness == null) return null;

        return this.defaultBlockState().setValue(THICKNESS, thickness).setValue(WATERLOGGED, isWaterlogged);
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
        if (!fallingBlockEntity.isSilent() && level instanceof ServerLevel serverLevel) {
            Services.PLATFORM.sendIcicleProjectileShatterS2CPacket(serverLevel, Vec3.atCenterOf(blockPos));
            fallingBlockEntity.playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.2F / (new Random().nextFloat() * 0.2F + 0.9F));
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

    @Override
    public FluidState getFluidState(BlockState blockState) {
        if (blockState.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(blockState);
    }

    @Nullable
    private static BlockPos findTip(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, int searchDistance) {
        if (isTip(blockState)) return blockPos;
        return findBlockVertical(levelAccessor, blockPos, Direction.DOWN, state -> state.is(BlockModule.ICICLE.get()), IcicleBlock::isTip, searchDistance).orElse(null);
    }

    @Nullable
    private static BlockPos findRoot(LevelAccessor levelAccessor, BlockPos blockPos, int searchDistance) {
        Predicate<BlockState> matchingPredicate = state -> state.is(BlockModule.ICICLE.get());
        Predicate<BlockState> stoppingPredicate = state -> !state.is(BlockModule.ICICLE.get());
        return findBlockVertical(levelAccessor, blockPos, Direction.UP, matchingPredicate, stoppingPredicate, searchDistance).orElse(null);
    }

    /**
     * Determines if the position is valid for an icicle to be placed.
     * An icicle can be placed if the block above is sturdy or another icicle.
     */
    private static boolean isValidPosition(LevelReader levelReader, BlockPos blockPos) {
        BlockPos posAbove = blockPos.relative(Direction.UP);
        BlockState blockStateAbove = levelReader.getBlockState(posAbove);
        return blockStateAbove.isFaceSturdy(levelReader, posAbove, Direction.DOWN) || blockStateAbove.is(BlockModule.ICICLE.get());
    }

    private static boolean isTip(BlockState blockState) {
        if (!blockState.is(BlockModule.ICICLE.get())) return false;
        DripstoneThickness thickness = blockState.getValue(THICKNESS);
        return thickness == DripstoneThickness.TIP;
    }

    private static boolean isTop(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return blockState.is(BlockModule.ICICLE.get()) && !levelReader.getBlockState(blockPos.above()).is(BlockModule.ICICLE.get());
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

        if (!blockBelow.is(BlockModule.ICICLE.get())) return DripstoneThickness.TIP;
        if (blockBelow.getValue(THICKNESS) == DripstoneThickness.TIP) return DripstoneThickness.FRUSTUM;
        if (!blockAbove.is(BlockModule.ICICLE.get())) return DripstoneThickness.BASE;
        return DripstoneThickness.MIDDLE;
    }

    private static void spawnFallingIcicle(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        BlockState blockState2 = blockState;

        while (blockState2.is(BlockModule.ICICLE.get())) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, mutable, blockState2);
            if (isTip(blockState2)) {
                int icicleSize = Math.max(1 + blockPos.getY() - mutable.getY(), 6);
                float damagePerBlock = .5f * (float) icicleSize;
                fallingBlockEntity.setHurtsEntities(damagePerBlock, 10);
                break;
            }
            mutable.move(Direction.DOWN);
            blockState2 = serverLevel.getBlockState(mutable);
        }
    }

    /**
     * Should be called with the TOP block in the icicle ONLY!
     */
    private static void tryToGrowIcicle(BlockState rootIcicleBlock, ServerLevel serverLevel, BlockPos rootIcicleBlockPos) {
        // Block above top block must be source water
        if (!canGrow(serverLevel.getBlockState(rootIcicleBlockPos.above(1)), serverLevel.getBlockState(rootIcicleBlockPos.above(2)))) {
            return;
        }

        // Find tip for this icicle
        BlockPos tipPos = findTip(rootIcicleBlock, serverLevel, rootIcicleBlockPos, 6); // Icicles can grow up to 6 long naturally
        if (tipPos == null || !isTip(serverLevel.getBlockState(tipPos))) return;

        // Make sure tip block is valid and has room to grow
        if (doesTipHaveRoomToGrow(serverLevel, tipPos)) growIcicle(serverLevel, tipPos);
    }

    /**
     * Icicles grow if an ice block is above the root, w/ a source block of water above that.
     */
    private static boolean canGrow(BlockState blockState, BlockState blockStateAbove) {
        return blockState.is(BlockTags.ICE) && blockStateAbove.is(Blocks.WATER) && blockStateAbove.getFluidState().isSource();
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
        BlockState blockState = BlockModule.ICICLE.get().defaultBlockState()
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER);
        levelAccessor.setBlock(blockPos, blockState, 3);
    }

    /******************************************
     * Dripping utilities
     ******************************************/

    @Nullable
    public static BlockPos findIcicleTipAboveCauldron(Level level, BlockPos blockPos) {
        return findBlockVertical(level, blockPos, Direction.UP, BlockBehaviour.BlockStateBase::isAir, IcicleBlock::canDrip, 11).orElse(null);
    }

    private static boolean canDrip(BlockState blockState) {
        return blockState.getBlock() == BlockModule.ICICLE.get() && blockState.getValue(THICKNESS) == DripstoneThickness.TIP && !blockState.getValue(WATERLOGGED);
    }

    private static Optional<Fluid> getFluidAboveIcicle(Level level, BlockPos blockPos) {
        BlockPos rootPos = findRoot(level, blockPos, 11);
        if (rootPos == null) return Optional.empty();
        return Optional.of(level.getFluidState(rootPos.above()).getType());
    }

    private static boolean canFillCauldron(Fluid fluid) {
        return fluid == Fluids.WATER;
    }

    private static void spawnDripParticle(Level level, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        Vec3 vec3 = blockState.getOffset(level, blockPos);
        double d = 0.0625;
        double x = (double)blockPos.getX() + 0.5 + vec3.x;
        double y = (double)((float)(blockPos.getY() + 1) - 0.6875f) - d;
        double z = (double)blockPos.getZ() + 0.5 + vec3.z;
        level.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, x, y, z, 0.0, 0.0, 0.0);
    }

    public static Fluid getCauldronFillFluidType(Level level, BlockPos blockPos) {
        return getFluidAboveIcicle(level, blockPos).filter(IcicleBlock::canFillCauldron).orElse(null);
    }

    private static void maybeFillCauldron(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, float f) {
        if (f > 0.17578125f) {
            return;
        }

        if (!isTop(blockState, serverLevel, blockPos)) {
            return;
        }

        Fluid fluid = getCauldronFillFluidType(serverLevel, blockPos);
        if (fluid != Fluids.WATER) return;
        float dripChance = 0.17578125f;
        if (f >= dripChance) {
            return;
        }

        // Determine tip pos
        BlockPos tipPos = findTip(blockState, serverLevel, blockPos, 11);
        if (tipPos == null) {
            return;
        }

        // Determine cauldron pos
        Predicate<BlockState> isValidCauldron = state -> state.getBlock() instanceof AbstractCauldronBlock
                && ((AbstractCauldronBlockAccessor)state.getBlock()).callCanReceiveStalactiteDrip(fluid);
        BlockPos cauldronPos = findBlockVertical(serverLevel, tipPos, Direction.DOWN, BlockStateBase::isAir, isValidCauldron, 11).orElse(null);
        if (cauldronPos == null) {
            return;
        }

        // Schedule cauldron tick
        int yDist = tipPos.getY() - cauldronPos.getY();
        int timeUntilCauldronTick = 50 + yDist;
        BlockState cauldronBlockState = serverLevel.getBlockState(cauldronPos);
        serverLevel.scheduleTick(cauldronPos, cauldronBlockState.getBlock(), timeUntilCauldronTick);
    }
}
