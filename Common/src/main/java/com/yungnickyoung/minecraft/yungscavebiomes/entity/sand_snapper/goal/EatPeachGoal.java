package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class EatPeachGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    private final float horizontalRange;
    private final float verticalRange;
    private final float playerDist;
    private final float eatRange;

    private ItemEntity peachItem;
    private int eatingTimer;
    private static final int EAT_LENGTH = 31;

    public EatPeachGoal(SandSnapperEntity sandSnapper, float horizontalRange, float verticalRange, float playerDist, float eatRange) {
        this.sandSnapper = sandSnapper;
        this.horizontalRange = horizontalRange;
        this.verticalRange = verticalRange;
        this.playerDist = playerDist;
        this.eatRange = eatRange;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    @Override
    public void start() {
        this.sandSnapper.setSubmerged(false);
        this.sandSnapper.setSubmergeLocked(true);

        this.sandSnapper.getNavigation().moveTo(this.peachItem, 1.0f);
    }

    @Override
    public void stop() {
        this.sandSnapper.setEating(false);
        this.sandSnapper.setSubmergeLocked(false);
    }

    @Override
    public void tick() {
        if (this.sandSnapper.distanceTo(this.peachItem) > this.eatRange && this.sandSnapper.getNavigation().isDone()) {
            if (this.sandSnapper.isEating()) {
                this.sandSnapper.setEating(false);
            }

            this.sandSnapper.getNavigation().moveTo(this.peachItem, 1.0f);
        } else if (this.sandSnapper.distanceTo(this.peachItem) <= this.eatRange && !this.sandSnapper.isEating()) {
            this.sandSnapper.setEating(true);
            this.eatingTimer = EAT_LENGTH;
        }

        if (this.sandSnapper.isEating()) {
            this.eatingTimer--;

            if (this.eatingTimer <= 0) {
                this.sandSnapper.setEating(false);
                this.peachItem.discard();
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        List<ItemEntity> itemsNearby = this.sandSnapper.getLevel().getEntitiesOfClass(ItemEntity.class,
                this.sandSnapper.getBoundingBox().inflate(this.horizontalRange / 2, this.verticalRange / 2, this.horizontalRange / 2),
                entity -> entity.getItem().is(ItemModule.PRICKLY_PEACH_ITEM.get()));

        if (itemsNearby.isEmpty()) return false;

        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        Collections.shuffle(itemsNearby);
        if (!sandstormServerData.isSandstormActive()) {
            for (ItemEntity item : itemsNearby) {
                Player nearestPlayer = item.getLevel().getNearestPlayer(item.getX(), item.getY(), item.getZ(), this.playerDist, true);

                if (nearestPlayer == null) {
                    this.peachItem = item;
                    return true;
                }
            }
        } else {
            this.peachItem = itemsNearby.get(0);
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {

        if (this.peachItem.isRemoved()) return false;

        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        if (!sandstormServerData.isSandstormActive()) {
            Player nearestPlayer = this.peachItem.getLevel().getNearestPlayer(this.peachItem.getX(), this.peachItem.getY(), this.peachItem.getZ(), this.playerDist, true);
            return nearestPlayer == null;
        }

        return true;
    }
}
