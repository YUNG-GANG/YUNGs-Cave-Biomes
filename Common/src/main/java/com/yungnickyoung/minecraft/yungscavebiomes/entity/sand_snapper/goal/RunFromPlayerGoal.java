package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class RunFromPlayerGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    private final double speedModifier;
    @Nullable
    protected Player playerToAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    private final TargetingConditions avoidEntityTargeting;

    public RunFromPlayerGoal(SandSnapperEntity sandSnapper, float dist, double speedModifier) {
        this(sandSnapper, dist, speedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public RunFromPlayerGoal(SandSnapperEntity sandSnapper, float maxDist,
                             double speedModifier, Predicate<LivingEntity> avoidEntityPredicate) {
        this.sandSnapper = sandSnapper;
        this.maxDist = maxDist;
        this.speedModifier = speedModifier;
        this.pathNav = sandSnapper.getNavigation();
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDist).selector(avoidEntityPredicate);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public void start() {
        this.pathNav.moveTo(this.path, this.speedModifier);
        if (!this.sandSnapper.isSubmerged() && this.sandSnapper.canSubmerge(true)) {
            this.sandSnapper.setSubmerged(true);
        }
    }

    @Override
    public void stop() {
        this.playerToAvoid = null;
    }

    @Override
    public void tick() {
        if (!this.sandSnapper.isSubmerged() && this.sandSnapper.canSubmerge(true)) {
            this.sandSnapper.setSubmerged(true);
        }

        int multiplier = this.sandSnapper.isSubmerged() ? 2 : 1;
        this.sandSnapper.getNavigation().setSpeedModifier(this.speedModifier * multiplier);

        // TODO - we don't need this do we? It seems odd to have a separate speed when it's within 7 blocks.
//        if (this.sandSnapper.distanceToSqr(this.toAvoid) < 49.0) {
//            this.sandSnapper.getNavigation().setSpeedModifier(this.runSpeedModifier * multiplier);
//        } else {
//            this.sandSnapper.getNavigation().setSpeedModifier(this.walkSpeedModifier * multiplier);
//        }
    }

    @Override
    public boolean canUse() {
        this.playerToAvoid = this.sandSnapper.level.getNearestEntity(
                this.sandSnapper.level
                        .getEntitiesOfClass(Player.class, this.sandSnapper.getBoundingBox().inflate(this.maxDist, 3.0, this.maxDist), p -> true),
                this.avoidEntityTargeting,
                this.sandSnapper,
                this.sandSnapper.getX(),
                this.sandSnapper.getY(),
                this.sandSnapper.getZ());

        // No nearby players -> don't run
        if (this.playerToAvoid == null) {
            return false;
        }

        // Get a random position away from the player to run towards
        // TODO - comment what the 16 and 7 are for, ideally moving them into named vars.
        // TODO - make the sand snapper attempt to stay in sand, even if it means turning back towards the player.
        //        In other words, don't allow the player to chase the sand snapper out of the sand.
        Vec3 targetPos = DefaultRandomPos.getPosAway(this.sandSnapper, 16, 7, this.playerToAvoid.position());
        if (targetPos == null) {
            return false;
        }

        // Abort if the target position will result in the sand snapper getting closer to the player.
        // TODO - Revise. Do we need this?
        if (this.playerToAvoid.distanceToSqr(targetPos.x, targetPos.y, targetPos.z) < this.playerToAvoid.distanceToSqr(this.sandSnapper)) {
            return false;
        }

        this.path = this.pathNav.createPath(targetPos.x, targetPos.y, targetPos.z, 0);
        return this.path != null && !isInActiveSandstorm();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pathNav.isDone() && !isInActiveSandstorm();
    }

    private boolean isInActiveSandstorm() {
        // TODO - check if the sand snapper is in a Lost Caves biome when checking for sandstorm
        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();
        return sandstormServerData.isSandstormActive();
    }
}
