package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyPeachCactusBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EatPricklyPeachCactusGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    private final int horizontalRange;
    private final int verticalRange;
    private final float playerDist;
    private final float eatRange;

    private Vec3 cactusPos;
    private int eatingTimer;
    private static final int EAT_LENGTH = 31;

    public EatPricklyPeachCactusGoal(SandSnapperEntity sandSnapper, int horizontalRange, int verticalRange, float playerDist, float eatRange) {
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

        this.sandSnapper.getNavigation().moveTo(this.cactusPos.x, this.cactusPos.y, this.cactusPos.z, 1.0f);
    }

    @Override
    public void stop() {
        this.sandSnapper.setEating(false);
        this.sandSnapper.setSubmergeLocked(false);

        if (this.sandSnapper.cactusEatCooldownTimer <= 0) {
            // Cooldown wasn't reset by eating the cactus, so it must have been interrupted by the player.
            // Reset the cooldown to a shorter value so the sand snapper can try again sooner.
            this.sandSnapper.cactusEatCooldownTimer = SandSnapperEntity.CACTUS_EAT_INTERRUPTED_COOLDOWN;
        }
    }

    @Override
    public void tick() {
        if (this.sandSnapper.distanceToSqr(this.cactusPos) > this.eatRange * this.eatRange && this.sandSnapper.getNavigation().isDone()) {
            if (this.sandSnapper.isEating()) {
                this.sandSnapper.setEating(false);
            }

            this.sandSnapper.getNavigation().moveTo(this.cactusPos.x, this.cactusPos.y, this.cactusPos.z, 1.0f);
        } else if (this.sandSnapper.distanceToSqr(this.cactusPos) <= this.eatRange * this.eatRange && !this.sandSnapper.isEating()) {
            this.sandSnapper.setEating(true);
            this.eatingTimer = EAT_LENGTH;
        }

        if (this.sandSnapper.isEating()) {
            this.eatingTimer--;

            if (this.eatingTimer <= 0) {
                this.sandSnapper.setEating(false);
                this.sandSnapper.eatCactus(new BlockPos(this.cactusPos));
                this.sandSnapper.cactusEatCooldownTimer = SandSnapperEntity.CACTUS_EAT_COOLDOWN;
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (this.sandSnapper.isOnCactusEatCooldown()) {
            return false;
        }

        List<BlockPos> cactusPositions = new ArrayList<>();
        BlockPos.betweenClosed(
                this.sandSnapper.blockPosition().getX() - this.horizontalRange,
                this.sandSnapper.blockPosition().getY() - this.verticalRange,
                this.sandSnapper.blockPosition().getZ() - this.horizontalRange,
                this.sandSnapper.blockPosition().getX() + this.horizontalRange,
                this.sandSnapper.blockPosition().getY() + this.verticalRange,
                this.sandSnapper.blockPosition().getZ() + this.horizontalRange
        ).forEach(p -> {
            BlockState state = this.sandSnapper.level.getBlockState(p);
            if (state.is(BlockModule.PRICKLY_PEACH_CACTUS.get()) && state.getValue(PricklyPeachCactusBlock.FRUIT)) {
                cactusPositions.add(new BlockPos(p));
            }
        });

        if (cactusPositions.isEmpty()) return false;

        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        if (!sandstormServerData.isSandstormActive()) {
            for (BlockPos pos : cactusPositions) {
                Player nearestPlayer = this.sandSnapper.getLevel().getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), this.playerDist, true);
                if (nearestPlayer == null) {
                    this.cactusPos = Vec3.atCenterOf(pos);
                    return true;
                }
            }
        } else {
            // If a sandstorm is active, just pick the first cactus we find
            this.cactusPos = Vec3.atCenterOf(cactusPositions.get(0));
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.sandSnapper.isOnCactusEatCooldown()) {
            return false;
        }

        BlockState state = this.sandSnapper.level.getBlockState(new BlockPos(this.cactusPos));
        if (!state.is(BlockModule.PRICKLY_PEACH_CACTUS.get()) || !state.getValue(PricklyPeachCactusBlock.FRUIT)) {
            return false;
        }

        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        if (!sandstormServerData.isSandstormActive()) {
            Player nearestPlayer = this.sandSnapper.getLevel().getNearestPlayer(this.cactusPos.x(), this.cactusPos.y(), this.cactusPos.z(), this.playerDist, true);
            return nearestPlayer == null;
        }

        return true;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}
