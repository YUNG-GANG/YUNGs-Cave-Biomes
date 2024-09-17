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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

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
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        // Harvest peach if available
        if (blockState.getValue(FRUIT)) {
            popFruit(level, blockPos, new ItemStack(ItemModule.PRICKLY_PEACH_ITEM.get(), 1));
            float volume = Mth.randomBetween(level.random, 0.8f, 1.2f);
            level.playSound(null, blockPos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, volume);
            level.setBlock(blockPos, blockState.setValue(FRUIT, false).setValue(AGE, 0), 2);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            // Hurt player if nothing in hand
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (itemInHand.isEmpty()) {
                player.hurt(DamageSource.CACTUS, 1.0f);
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaModule.INTERACT_EMPTY_PRICKLY_CACTUS.trigger(serverPlayer);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        if (isMaxAge(blockState) || hasFruit(blockState)) {
            return;
        }

        // Chance of aging cactus
        if (random.nextDouble() < AGE_CHANCE) {
            int newAge = blockState.getValue(AGE) + 1;
            BlockState newBlockState = blockState.setValue(AGE, newAge);

            // Chance of growing fruit
            if (random.nextDouble() < BERRY_CHANCE || newAge == MAX_AGE) {
                newBlockState = newBlockState.setValue(FRUIT, true);
            }

            serverLevel.setBlockAndUpdate(blockPos, newBlockState);
        }
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack);
        if (blockState.hasProperty(FRUIT) && blockState.getValue(FRUIT)) {
            Block.popResource(serverLevel, blockPos, new ItemStack(ItemModule.PRICKLY_PEACH_ITEM.get(), 1));
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean b) {
        return blockState.hasProperty(FRUIT) && !blockState.getValue(FRUIT);
    }

    @Override
    public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, Random random, BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlock(blockPos, blockState.setValue(FRUIT, true), 2);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return OUTLINE_SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockStateBelow = levelReader.getBlockState(blockPos.below());
        return blockStateBelow.is(BlockTags.SAND);
    }

    @Override
    public BlockState updateShape(BlockState currState, Direction neighborDirection, BlockState neighborBlockState, LevelAccessor levelAccessor, BlockPos currPos, BlockPos neighborPos) {
        if (!currState.canSurvive(levelAccessor, currPos)) {
            levelAccessor.destroyBlock(currPos, true);
        }
        return currState;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity.getType() == EntityTypeModule.SAND_SNAPPER.get()) {
            return; // Sand snapper is immune to prickly cactus
        }
        entity.hurt(DamageSource.CACTUS, 1.0f);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
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

    private static void popFruit(Level level, BlockPos $$1, ItemStack itemStack) {
        if (!level.isClientSide && !itemStack.isEmpty() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            double x = (double) $$1.getX() + 0.5;
            double y = (double) $$1.getY() + 0.5 - (EntityType.ITEM.getHeight() / 2.0F);
            double z = (double) $$1.getZ() + 0.5;

            double xOffset = Mth.nextDouble(level.random, 0.20, 0.25);
            double yOffset = 0.25;
            double zOffset = Mth.nextDouble(level.random, 0.20, 0.25);

            double dx = Mth.nextDouble(level.random, 0.075, 0.1);
            double dy = 0.2;
            double dz = Mth.nextDouble(level.random, 0.075, 0.1);

            if (level.random.nextBoolean()) {
                xOffset *= -1;
                dx *= -1;
            }
            if (level.random.nextBoolean()) {
                zOffset *= -1;
                dz *= -1;
            }

            x += xOffset;
            y += yOffset;
            z += zOffset;

            ItemEntity itemEntity = new ItemEntity(level, x, y, z, itemStack, dx, dy, dz);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }
}
