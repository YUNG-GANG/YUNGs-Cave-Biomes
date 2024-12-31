package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.BrushableBlockEntityAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BuryLootGoal extends Goal {
    private static final double MAX_ALLOWED_SQR_DISTANCE_TO_TARGET = 100.0;

    private final SandSnapperEntity sandSnapper;

    @Nullable
    private final PathNavigation pathNav;

    /**
     * The position of the block that the snapper will bury the loot in.
     * This is the block that the snapper will be standing on when burying the loot.
     */
    private Vec3 targetPos;

    /**
     * Duration in ticks that the snapper will "bury" the loot for.
     * This is the time it takes for the snapper to turn the block into a suspicious counterpart.
     */
    private final int buryingDuration;

    /**
     * Timer that counts down the duration of the burying process.
     */
    private int buryingTimer;

    /**
     * The range in the x and z directions that the snapper will search for a block to bury the loot in.
     */
    private final int xzRange;

    /**
     * The range in the y direction that the snapper will search for a block to bury the loot in.
     */
    private final int yRange;

    private boolean isDone;

    public BuryLootGoal(SandSnapperEntity sandSnapper, int xzRange, int yRange, int buryingDuration) {
        this.sandSnapper = sandSnapper;
        this.pathNav = sandSnapper.getNavigation();
        this.buryingDuration = buryingDuration;
        this.xzRange = xzRange;
        this.yRange = yRange;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.sandSnapper.buryingLoot || this.isDone) return false;
        this.targetPos = this.findTargetPos();
        return this.targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.isDone) return false;

        // At any time, if the target block is no longer valid, stop burying and pop the item
        if (!isBlockAValidTarget(BlockPos.containing(this.targetPos).below())) {
            this.popItem();
            this.isDone = true;
            return false;
        }

        // Must be burying or within range of target
        return buryingTimer > 0 || this.sandSnapper.distanceToSqr(this.targetPos) < MAX_ALLOWED_SQR_DISTANCE_TO_TARGET;
    }

    @Override
    public void start() {
        this.isDone = false;
    }

    @Override
    public void stop() {
        this.sandSnapper.buryingLoot = false;
        this.sandSnapper.setForceSpawnDigParticles(false);
        this.buryingTimer = 0;
        this.isDone = false;
    }

    @Override
    public void tick() {
        if (isDone) return;

        if (buryingTimer > 0) { // buryingTimer is counting down
            buryingTimer--;
            if (buryingTimer == 0) {
                // Turn block into suspicious counterpart
                BlockState blockStateOn = sandSnapper.getBlockStateOn();
                if (blockStateOn.is(Blocks.SAND)) {
                    sandSnapper.level().broadcastEntityEvent(sandSnapper, SandSnapperEntity.BURY_LOOT_PARTICLES_EVENT_SAND);
                    sandSnapper.level().setBlock(sandSnapper.getOnPos(), Blocks.SUSPICIOUS_SAND.defaultBlockState(), Block.UPDATE_ALL);
                    sandSnapper.level().getBlockEntity(sandSnapper.getOnPos(), BlockEntityType.BRUSHABLE_BLOCK)
                            .ifPresentOrElse(blockEntity -> {
                                ((BrushableBlockEntityAccessor) blockEntity).setItem(sandSnapper.carryingItem);
                                sandSnapper.carryingItem = ItemStack.EMPTY;
                            }, this::popItem);
                } else if (blockStateOn.is(BlockModule.ANCIENT_SAND.get())) {
                    sandSnapper.level().broadcastEntityEvent(sandSnapper, SandSnapperEntity.BURY_LOOT_PARTICLES_EVENT_ANCIENT_SAND);
                    sandSnapper.level().setBlock(sandSnapper.getOnPos(), BlockModule.SUSPICIOUS_ANCIENT_SAND.get().defaultBlockState(), Block.UPDATE_ALL);
                    sandSnapper.level().getBlockEntity(sandSnapper.getOnPos(), EntityTypeModule.SUSPICIOUS_ANCIENT_SAND.get())
                            .ifPresentOrElse(blockEntity -> {
                                blockEntity.setItem(sandSnapper.carryingItem);
                                sandSnapper.carryingItem = ItemStack.EMPTY;
                            }, this::popItem);
                } else if (blockStateOn.is(Blocks.GRAVEL)) {
                    sandSnapper.level().broadcastEntityEvent(sandSnapper, SandSnapperEntity.BURY_LOOT_PARTICLES_EVENT_GRAVEL);
                    sandSnapper.level().setBlock(sandSnapper.getOnPos(), Blocks.SUSPICIOUS_GRAVEL.defaultBlockState(), Block.UPDATE_ALL);
                    sandSnapper.level().getBlockEntity(sandSnapper.getOnPos(), BlockEntityType.BRUSHABLE_BLOCK)
                            .ifPresentOrElse(blockEntity -> {
                                ((BrushableBlockEntityAccessor) blockEntity).setItem(sandSnapper.carryingItem);
                                sandSnapper.carryingItem = ItemStack.EMPTY;
                            }, this::popItem);
                } else {
                    // Block is not a valid target - pop the item
                    this.popItem();
                }
                this.isDone = true;
            } else if (buryingTimer == buryingDuration / 2) {
                // Start spawning dig particles halfway through burying
                sandSnapper.setForceSpawnDigParticles(true);
            }
        } else { // We haven't started burying yet
            if (!this.sandSnapper.blockPosition().equals(BlockPos.containing(this.targetPos))) { // We're not on the target block yet
                double sqrDist = this.sandSnapper.position().distanceToSqr(this.targetPos);
                if (sqrDist > 4.0 && this.pathNav != null) {
                    this.pathNav.moveTo(targetPos.x(), targetPos.y(), targetPos.z(), 1.0);
                } else {
                    this.sandSnapper.getMoveControl().setWantedPosition(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), 1.0);
                }
            } else { // We're on the target block - stop moving and start burying
                this.buryingTimer = buryingDuration;
                if (this.pathNav != null) {
                    this.pathNav.stop();
                }
                if (!this.sandSnapper.isSubmerged() && !this.sandSnapper.isDiggingDown() && !this.sandSnapper.isDiving() && this.sandSnapper.canSubmerge(true)) {
                    this.sandSnapper.setSubmerged(true);
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Nullable
    private Vec3 findTargetPos() {
        return BlockPos.findClosestMatch(this.sandSnapper.getOnPos(), this.xzRange, this.yRange, this::isBlockAValidTarget)
                .map(BlockPos::above)
                .map(pos -> new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5))
                .orElse(null);
    }

    private boolean isBlockAValidTarget(BlockPos blockPos) {
        Block block = this.sandSnapper.level().getBlockState(blockPos).getBlock();
        return (block == Blocks.SAND || block == BlockModule.ANCIENT_SAND.get() || block == Blocks.GRAVEL)
                && this.sandSnapper.level().getBlockState(blockPos.above()).isAir();
    }

    private void popItem() {
        if (!sandSnapper.carryingItem.isEmpty()) {
            BlockPos spawnPos = this.sandSnapper.blockPosition();
            double x = (double) spawnPos.getX() + 0.5;
            double y = (double) spawnPos.getY() + 0.5 - (EntityType.ITEM.getHeight() / 2.0F);
            double z = (double) spawnPos.getZ() + 0.5;

            RandomSource random = this.sandSnapper.getRandom();

            double xOffset = Mth.nextDouble(random, 0, 0.2);
            double yOffset = 0.2;
            double zOffset = Mth.nextDouble(random, 0, 0.2);

            double dx = Mth.nextDouble(random, 0, 0.05);
            double dy = 0.2;
            double dz = Mth.nextDouble(random, 0, 0.05);

            if (random.nextBoolean()) {
                xOffset *= -1;
                dx *= -1;
            }
            if (random.nextBoolean()) {
                zOffset *= -1;
                dz *= -1;
            }

            x += xOffset;
            y += yOffset;
            z += zOffset;

            ItemEntity itemEntity = new ItemEntity(this.sandSnapper.level(), x, y, z, sandSnapper.carryingItem, dx, dy, dz);
            itemEntity.setDefaultPickUpDelay();
            this.sandSnapper.level().addFreshEntity(itemEntity);
        }
    }
}
