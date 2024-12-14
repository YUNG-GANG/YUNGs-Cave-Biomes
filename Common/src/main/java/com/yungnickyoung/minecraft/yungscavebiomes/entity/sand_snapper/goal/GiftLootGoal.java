package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.LootTableModule;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class GiftLootGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    protected final float maxDist;
    @Nullable
    private final PathNavigation pathNav;

    private int timer;
    private final int DURATION;

    public GiftLootGoal(SandSnapperEntity sandSnapper, float maxDist, int duration) {
        this.sandSnapper = sandSnapper;
        this.maxDist = maxDist;
        this.pathNav = sandSnapper.getNavigation();
        this.DURATION = duration;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Only fetch items if the snapper is able to submerge in the block it's on
        return this.sandSnapper.canSubmerge(true);
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
        this.sandSnapper.onFinishGiftLootGoal();
    }

    @Override
    public void tick() {
        timer--;
        if (timer == 0) {
            // Spawn loot
            LootTable lootTable = this.sandSnapper.getServer().getLootData().getLootTable(LootTableModule.LOST_CAVES_ANCIENT_SAND);
            LootParams lootParams = new LootParams.Builder((ServerLevel) this.sandSnapper.level())
                    .withParameter(LootContextParams.ORIGIN, this.sandSnapper.position())
                    .withParameter(LootContextParams.THIS_ENTITY, this.sandSnapper)
                    .create(LootContextParamSets.GIFT);
            ObjectArrayList<ItemStack> lootChoices = lootTable.getRandomItems(lootParams, new Random().nextLong());
            ItemStack loot = lootChoices.isEmpty() ? ItemStack.EMPTY : lootChoices.get(0);
            spawnLoot(loot);
        } else if (timer == DURATION / 2) {
            this.sandSnapper.setForceSpawnDigParticles(true);
        }
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    private void spawnLoot(ItemStack loot) {
        if (!loot.isEmpty()) {
//            double width = EntityType.ITEM.getWidth();
//            double xzMultiplier = 1.0 - width;
//            double xzOffset = width / 2.0;
//            BlockPos spawnPos = this.sandSnapper.blockPosition().relative(Direction.UP, 1);
//            double x = (double) spawnPos.getX() + 0.5 * xzMultiplier + xzOffset;
//            double y = (double) spawnPos.getY() + 0.5 + (double) (EntityType.ITEM.getHeight() / 2.0F);
//            double z = (double) spawnPos.getZ() + 0.5 * xzMultiplier + xzOffset;
//            ItemEntity itemEntity = new ItemEntity(this.sandSnapper.level(), x, y, z, loot.split(this.sandSnapper.level().random.nextInt(21) + 10));
//            itemEntity.setDeltaMovement(Vec3.ZERO);
//            this.sandSnapper.level().addFreshEntity(itemEntity);

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

            ItemEntity itemEntity = new ItemEntity(this.sandSnapper.level(), x, y, z, loot, dx, dy, dz);
            itemEntity.setDefaultPickUpDelay();
            this.sandSnapper.level().addFreshEntity(itemEntity);
        }
    }
}
