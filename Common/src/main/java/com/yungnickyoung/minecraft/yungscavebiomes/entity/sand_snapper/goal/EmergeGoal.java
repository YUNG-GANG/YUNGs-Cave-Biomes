package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class EmergeGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    private final float minDistanceFromPlayer;
    private final float minDistanceFromPlayerDuringSandstorm;
    private final int cooldown;

    private int ticksRunning;
    private int lastUseTime;

    public EmergeGoal(SandSnapperEntity sandSnapper, float minDistanceFromPlayer, float minDistanceFromPlayerDuringSandstorm, int cooldown) {
        this.sandSnapper = sandSnapper;
        this.minDistanceFromPlayer = minDistanceFromPlayer;
        this.minDistanceFromPlayerDuringSandstorm = minDistanceFromPlayerDuringSandstorm;
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
        this.sandSnapper.setDiving(true);
        this.lastUseTime = this.sandSnapper.tickCount;
    }

    @Override
    public boolean canUse() {
        if (!this.sandSnapper.isSubmerged()) return false;

        if (this.sandSnapper.tickCount - this.lastUseTime < this.cooldown) {
            return false;
        }

        // Abort if there is any air around the sand snapper
        float halfWidth = 0.8f;
        Vec3 startPos = new Vec3(this.sandSnapper.getX() - (double) halfWidth, this.sandSnapper.getY() - 2.0f, this.sandSnapper.getZ() - (double) halfWidth);
        Vec3 endPos = new Vec3(this.sandSnapper.getX() + (double) halfWidth, this.sandSnapper.getY() - 0.6f, this.sandSnapper.getZ() + (double) halfWidth);
        boolean intersectsAir = BlockPos.betweenClosedStream(new AABB(startPos, endPos))
                .anyMatch((pos) -> this.sandSnapper.level.getBlockState(pos).isAir());
        if (intersectsAir) return false;

        return getPlayersInRange().isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return getPlayersInRange().isEmpty() && this.ticksRunning <= 57;
    }

    private List<Player> getPlayersInRange() {
        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();
        float dist = sandstormServerData.isSandstormActive() ?
                this.minDistanceFromPlayerDuringSandstorm :
                this.minDistanceFromPlayer;
        AABB searchBox = this.sandSnapper.getBoundingBox().inflate(dist / 2, 4.0f, dist / 2);
        return this.sandSnapper.level.getNearbyPlayers(TargetingConditions.DEFAULT, this.sandSnapper, searchBox);
    }
}
