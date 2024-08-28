package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class EmergeGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    private final float distanceFromPlayer;
    private final float sandstormDistanceFromPlayer;
    private final int cooldown;

    private int ticksRunning;
    private long lastUseTime;

    public EmergeGoal(SandSnapperEntity sandSnapper, float distanceFromPlayer, float sandstormDistanceFromPlayer, int cooldown) {
        this.sandSnapper = sandSnapper;
        this.distanceFromPlayer = distanceFromPlayer;
        this.sandstormDistanceFromPlayer = sandstormDistanceFromPlayer;
        this.cooldown = cooldown;

        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public void tick() {
        this.ticksRunning++;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.ticksRunning = 0;
        this.sandSnapper.setEmerging(true);
        this.sandSnapper.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.sandSnapper.setEmerging(false);
        this.lastUseTime = this.sandSnapper.tickCount;
    }

    @Override
    public boolean canUse() {
        if (this.sandSnapper.tickCount - this.lastUseTime < this.cooldown) {
            return false;
        }

        // TODO: Add logic for checking if it's a sandstorm
        AABB searchBox = this.sandSnapper.getBoundingBox().inflate(this.distanceFromPlayer / 2, 4.0f, this.distanceFromPlayer / 2);
        List<Player> nearbyPlayers = this.sandSnapper.level.getNearbyPlayers(TargetingConditions.DEFAULT, this.sandSnapper, searchBox);

        return nearbyPlayers.isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        AABB searchBox = this.sandSnapper.getBoundingBox().inflate(this.distanceFromPlayer / 2, 4.0f, this.distanceFromPlayer / 2);
        List<Player> nearbyPlayers = this.sandSnapper.level.getNearbyPlayers(TargetingConditions.DEFAULT, this.sandSnapper, searchBox);

        return nearbyPlayers.isEmpty() && this.ticksRunning <= 57;
    }
}
