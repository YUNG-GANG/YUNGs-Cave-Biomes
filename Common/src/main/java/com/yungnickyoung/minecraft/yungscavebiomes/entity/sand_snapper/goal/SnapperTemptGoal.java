package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SnapperTemptGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final SandSnapperEntity sandSnapper;
    private final double speedModifier;
    private final float playerDist;
    private double px;
    private double py;
    private double pz;
    private double pRotX;
    private double pRotY;
    @Nullable
    protected Player player;
    private int calmDown;
    private final Ingredient items;
    private final boolean canScare;

    public SnapperTemptGoal(SandSnapperEntity sandSnapper, double speedModifier, Ingredient ingredient, boolean canScare, float playerDist) {
        this.sandSnapper = sandSnapper;
        this.speedModifier = speedModifier;
        this.items = ingredient;
        this.canScare = canScare;
        this.playerDist = playerDist;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
    }

    @Override
    public boolean canUse() {
        if (this.calmDown > 0) {
            this.calmDown--;
            return false;
        } else {
            this.player = this.sandSnapper.level.getNearestPlayer(this.targetingConditions, this.sandSnapper);
            return this.player != null;
        }
    }

    @Override
    public void start() {
        this.sandSnapper.setSubmerged(false);
        this.sandSnapper.setSubmergeLocked(true);

        this.px = this.player.getX();
        this.py = this.player.getY();
        this.pz = this.player.getZ();
    }

    @Override
    public void tick() {
        this.sandSnapper.getLookControl().setLookAt(this.player, (float) (this.sandSnapper.getMaxHeadYRot() + 20), (float) this.sandSnapper.getMaxHeadXRot());
        if (this.sandSnapper.distanceToSqr(this.player) < this.playerDist * this.playerDist) {
            this.sandSnapper.getNavigation().stop();
        } else {
            this.sandSnapper.getNavigation().moveTo(this.player, this.speedModifier);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.canScare) {
            if (this.sandSnapper.distanceToSqr(this.player) < 36.0) {
                if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002) {
                    return false;
                }

                if (Math.abs((double) this.player.getXRot() - this.pRotX) > 5.0 || Math.abs((double) this.player.getYRot() - this.pRotY) > 5.0) {
                    return false;
                }
            } else {
                this.px = this.player.getX();
                this.py = this.player.getY();
                this.pz = this.player.getZ();
            }

            this.pRotX = this.player.getXRot();
            this.pRotY = this.player.getYRot();
        }

        return this.canUse();
    }

    @Override
    public void stop() {
        this.sandSnapper.setEating(false);
        this.sandSnapper.setSubmergeLocked(false);
        this.player = null;
        this.sandSnapper.getNavigation().stop();
        this.calmDown = reducedTickDelay(100);
    }

    private boolean shouldFollow(LivingEntity livingEntity) {
        return this.items.test(livingEntity.getMainHandItem()) || this.items.test(livingEntity.getOffhandItem());
    }
}

