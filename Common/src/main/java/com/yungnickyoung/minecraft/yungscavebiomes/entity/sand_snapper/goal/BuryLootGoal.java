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

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BuryLootGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    @Nullable
    private final PathNavigation pathNav;
    private int timer;
    private final int DURATION;
    private int maxDistance;

    public BuryLootGoal(SandSnapperEntity sandSnapper, int maxDistance, int duration) {
        this.sandSnapper = sandSnapper;
        this.pathNav = sandSnapper.getNavigation();
        this.DURATION = duration;
        this.maxDistance = maxDistance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Only fetch items if the snapper is able to submerge in the block it's on
        return this.sandSnapper.buryingLoot && this.sandSnapper.canSubmerge(true);
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0;
    }

    @Override
    public void start() {
        if (this.pathNav != null) {
            this.pathNav.stop();
        }
        if (!this.sandSnapper.isSubmerged() && this.sandSnapper.canSubmerge(true)) {
            this.sandSnapper.setSubmerged(true);
        }
        this.timer = DURATION;
    }

    @Override
    public void stop() {
        this.sandSnapper.buryingLoot = false;
        this.sandSnapper.setForceSpawnDigParticles(false);
    }

    @Override
    public void tick() {
        timer--;
        if (timer == 0) {
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
            }
        } else if (timer == DURATION / 2) {
            sandSnapper.setForceSpawnDigParticles(true);
        }
    }

    @Override
    public boolean isInterruptable() {
        return false;
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
            double dz =  Mth.nextDouble(random, 0, 0.05);

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
