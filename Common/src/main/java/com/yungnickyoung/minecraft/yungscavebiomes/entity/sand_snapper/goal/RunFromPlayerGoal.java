package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TagModule;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class RunFromPlayerGoal extends Goal {
    private final SandSnapperEntity sandSnapper;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    @Nullable
    protected Player toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;

    public RunFromPlayerGoal(SandSnapperEntity sandSnapper, float dist, double walkSpeedModifier, double runSpeedModifier) {
        this(sandSnapper, avoid -> true, dist, walkSpeedModifier, runSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public RunFromPlayerGoal(SandSnapperEntity sandSnapper, Predicate<LivingEntity> avoidPredicate, float maxDist, double walkSpeedModifier, double runSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
        this.sandSnapper = sandSnapper;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = maxDist;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = runSpeedModifier;
        this.predicateOnAvoidEntity = predicateOnAvoidEntity;
        this.pathNav = sandSnapper.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDist).selector(predicateOnAvoidEntity.and(avoidPredicate));
    }

    @Override
    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);

        if (this.sandSnapper.getLevel().getBlockState(this.sandSnapper.getOnPos()).is(TagModule.SAND_SNAPPER_BLOCKS) ||
                this.sandSnapper.getLevel().getBlockState(this.sandSnapper.getOnPos()).is(Blocks.AIR) &&
                        this.sandSnapper.level.getBlockState(this.sandSnapper.getOnPos().below()).is(TagModule.SAND_SNAPPER_BLOCKS)) {
            this.sandSnapper.setSubmerged(true);
        }
    }

    @Override
    public void stop() {
        this.toAvoid = null;
    }

    @Override
    public void tick() {
        if (!this.sandSnapper.isSubmerged() && this.sandSnapper.getLevel().getBlockState(this.sandSnapper.getOnPos()).is(TagModule.SAND_SNAPPER_BLOCKS) ||
                this.sandSnapper.getLevel().getBlockState(this.sandSnapper.getOnPos()).is(Blocks.AIR) &&
                        this.sandSnapper.level.getBlockState(this.sandSnapper.getOnPos().below()).is(TagModule.SAND_SNAPPER_BLOCKS)) {
            this.sandSnapper.setSubmerged(true);
        }

        int multiplier = this.sandSnapper.isSubmerged() ? 2 : 1;
        if (this.sandSnapper.distanceToSqr(this.toAvoid) < 49.0) {
            this.sandSnapper.getNavigation().setSpeedModifier(this.sprintSpeedModifier * multiplier);
        } else {
            this.sandSnapper.getNavigation().setSpeedModifier(this.walkSpeedModifier * multiplier);
        }
    }

    @Override
    public boolean canUse() {
        this.toAvoid = this.sandSnapper
                .level
                .getNearestEntity(
                        this.sandSnapper
                                .level
                                .getEntitiesOfClass(Player.class, this.sandSnapper.getBoundingBox().inflate(this.maxDist, 3.0, this.maxDist), $$0x -> true),
                        this.avoidEntityTargeting,
                        this.sandSnapper,
                        this.sandSnapper.getX(),
                        this.sandSnapper.getY(),
                        this.sandSnapper.getZ()
                );
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 posAway = DefaultRandomPos.getPosAway(this.sandSnapper, 16, 7, this.toAvoid.position());

            if (posAway == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(posAway.x, posAway.y, posAway.z) < this.toAvoid.distanceToSqr(this.sandSnapper)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(posAway.x, posAway.y, posAway.z, 0);
                SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

                return this.path != null && !sandstormServerData.isSandstormActive();
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        return !this.pathNav.isDone() && !sandstormServerData.isSandstormActive();
    }
}
