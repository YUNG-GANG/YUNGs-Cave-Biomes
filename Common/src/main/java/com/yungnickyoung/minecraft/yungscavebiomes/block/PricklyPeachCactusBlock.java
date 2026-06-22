package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;




public class PricklyPeachCactusBlock extends Block implements BonemealableBlock {
    public static final BooleanProperty FRUIT = BlockStateProperties.BERRIES;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_25;

    protected static final VoxelShape COLLISION_SHAPE = Block.box(4.0, 0.0, 3.0, 12.0, 8.0, 11.0);
    protected static final VoxelShape OUTLINE_SHAPE = Block.box(4.0, 0.0, 3.0, 12.0, 7.0, 11.0);

    private static final float AGE_CHANCE = 0.5f;
    private static final float BERRY_CHANCE = 0.1f;
    private static final int MAX_AGE = 25;

    public PricklyPeachCactusBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(FRUIT, false));
    }

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!isMaxAge(blockState) && itemStack.is(Items.BONE_MEAL)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
        // Harvest peach if available
        if (blockState.getValue(FRUIT)) {
            popFruit(level, blockPos);
            float volume = Mth.randomBetween(level.getRandom(), 0.8f, 1.2f);
            level.playSound(null, blockPos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, volume);
            level.setBlock(blockPos, blockState.setValue(FRUIT, false).setValue(AGE, 0), 2);
        } else if (player.getMainHandItem().isEmpty()){
            // Hurt player
            player.hurt(level.damageSources().cactus(), 1.0f);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaModule.INTERACT_EMPTY_PRICKLY_CACTUS.trigger(serverPlayer);
            }
        }
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        if (!blockState.canSurvive(serverLevel, blockPos)) {
            serverLevel.destroyBlock(blockPos, true);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        // If cactus is at max age or has fruit, no need to age further
        if (isMaxAge(blockState) || hasFruit(blockState)) {
            return;
        }

        // Chance of aging cactus
        if (random.nextDouble() < AGE_CHANCE) {
            int newAge = blockState.getValue(AGE) + 1;
            BlockState newBlockState = blockState.setValue(AGE, newAge);

            // Chance of growing fruit, or force grow if cactus is at max age
            if (random.nextDouble() < BERRY_CHANCE || newAge == MAX_AGE) {
                newBlockState = newBlockState.setValue(FRUIT, true);
            }

            serverLevel.setBlockAndUpdate(blockPos, newBlockState);
        }
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
        if (blockState.hasProperty(FRUIT) && blockState.getValue(FRUIT)) {
            Block.popResource(serverLevel, blockPos, new ItemStack(ItemModule.PRICKLY_PEACH_ITEM.get(), 1));
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.hasProperty(FRUIT) && !blockState.getValue(FRUIT);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource random, BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlock(blockPos, blockState.setValue(FRUIT, true).setValue(AGE, MAX_AGE), 2);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return COLLISION_SHAPE;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return OUTLINE_SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockStateBelow = levelReader.getBlockState(blockPos.below());
        return blockStateBelow.is(BlockTags.SAND);
    }

    @Override
    protected BlockState updateShape(BlockState currState, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (!currState.canSurvive(level, pos)) {
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.scheduleTick(pos, this, 1);
            }
        }
        return super.updateShape(currState, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity.getType() == EntityTypeModule.SAND_SNAPPER.get()) {
            return; // Sand snapper is immune to prickly cactus
        }
        if (level instanceof ServerLevel serverLevel) {
            entity.hurtServer(serverLevel, level.damageSources().cactus(), 1.0f);
        }
    }

    @Override
    public boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, FRUIT);
    }

    private static boolean isMaxAge(BlockState blockState) {
        return blockState.hasProperty(AGE) && blockState.getValue(AGE) == MAX_AGE;
    }

    private static boolean hasFruit(BlockState blockState) {
        return blockState.hasProperty(FRUIT) && blockState.getValue(FRUIT);
    }

    private static void popFruit(Level level, BlockPos $$1) {
        if (level instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.BLOCK_DROPS)) {
            double x = (double) $$1.getX() + 0.5;
            double y = (double) $$1.getY() + 0.5 - (EntityType.ITEM.getHeight() / 2.0F);
            double z = (double) $$1.getZ() + 0.5;

            double xOffset = Mth.nextDouble(level.getRandom(), 0.20, 0.25);
            double yOffset = 0.25;
            double zOffset = Mth.nextDouble(level.getRandom(), 0.20, 0.25);

            double dx = Mth.nextDouble(level.getRandom(), 0.075, 0.1);
            double dy = 0.2;
            double dz = Mth.nextDouble(level.getRandom(), 0.075, 0.1);

            if (level.getRandom().nextBoolean()) {
                xOffset *= -1;
                dx *= -1;
            }
            if (level.getRandom().nextBoolean()) {
                zOffset *= -1;
                dz *= -1;
            }

            x += xOffset;
            y += yOffset;
            z += zOffset;

            ItemStack fruitItemStack = new ItemStack(ItemModule.PRICKLY_PEACH_ITEM.get(), 1);
            ItemEntity itemEntity = new ItemEntity(level, x, y, z, fruitItemStack, dx, dy, dz);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }
}
