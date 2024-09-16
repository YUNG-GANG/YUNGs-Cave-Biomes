package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TagModule;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class RunFromPlayerGoal extends Goal {
    private static final double UPDATE_TARGET_POS_DISTANCE_SQUARED = 9.0;
    private static final int REFRESH_PATH_INTERVAL = 20;


    private final SandSnapperEntity sandSnapper;
    private final double speedModifier;
    private final double submergedSpeedModifier;
    @Nullable
    protected Player playerToAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    private final TargetingConditions avoidEntityTargeting;
    private int refreshPathTimer;

    public RunFromPlayerGoal(SandSnapperEntity sandSnapper, float dist, double speedModifier, double submergedSpeedModifier) {
        this(sandSnapper, dist, speedModifier, submergedSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    private RunFromPlayerGoal(SandSnapperEntity sandSnapper, float maxDist, double speedModifier,
                             double submergedSpeedModifier, Predicate<LivingEntity> avoidEntityPredicate) {
        this.sandSnapper = sandSnapper;
        this.maxDist = maxDist;
        this.speedModifier = speedModifier;
        this.submergedSpeedModifier = submergedSpeedModifier;
        this.pathNav = sandSnapper.getNavigation();
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDist).selector(avoidEntityPredicate);
        this.refreshPathTimer = adjustedTickDelay(REFRESH_PATH_INTERVAL);
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
        this.refreshPathTimer = adjustedTickDelay(REFRESH_PATH_INTERVAL);
    }

    @Override
    public void tick() {
        if (!this.sandSnapper.isSubmerged() && this.sandSnapper.canSubmerge(true)) {
            this.sandSnapper.setSubmerged(true);
            this.sandSnapper.tryPlayPanicSound();
        }

        double multiplier = this.sandSnapper.isSubmerged() ? submergedSpeedModifier : 1.0;
        this.sandSnapper.getNavigation().setSpeedModifier(this.speedModifier * multiplier);

        this.refreshPathTimer--;

        // TODO - also periodically check if there's a closer player, in which case we should also update the playerToAvoid
        // Update targetPos if the player gets too close
        if (this.playerToAvoid != null
                && this.refreshPathTimer <= 0
                && this.playerToAvoid.distanceToSqr(this.sandSnapper) < UPDATE_TARGET_POS_DISTANCE_SQUARED) {
            this.refreshPathTimer = adjustedTickDelay(REFRESH_PATH_INTERVAL);
            Vec3 newTargetPos = findTargetPos();
            if (newTargetPos != null) {
                this.path = this.pathNav.createPath(newTargetPos.x, newTargetPos.y, newTargetPos.z, 0);
                this.pathNav.moveTo(this.path, this.speedModifier);
            }
        }

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


        Vec3 targetPos = findTargetPos();
        if (targetPos == null) {
            return false;
        }

        this.path = this.pathNav.createPath(targetPos.x, targetPos.y, targetPos.z, 0);
        return this.path != null && !isInActiveSandstorm();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pathNav.isDone() && !isInActiveSandstorm();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    /**
     * Attempts to find a target position away from the player for the sand snapper to run to.
     * @return The target position, or null if no target position is found or if the playerToAvoid is null.
     */
    @Nullable
    private Vec3 findTargetPos() {
        // No nearby players -> don't run
        if (this.playerToAvoid == null) {
            return null;
        }

        int minHorizontalDist = 4;
        int maxHorizontalDist = 16;
        int maxVerticalDist = 7;

        // First try to find a sand block away from the player
        Function<BlockPos, Boolean> isValidSandPos = (pos) -> this.sandSnapper.level.getBlockState(pos).is(TagModule.SAND_SNAPPER_BLOCKS)
                && this.sandSnapper.level.getBlockState(pos.above()).isAir();
        Vec3 targetPos = getPosAway(minHorizontalDist, maxHorizontalDist, maxVerticalDist, Math.PI / 2, this.playerToAvoid.position(), isValidSandPos);

        // If no sand blocks are found, try to find a sand block in almost any direction (270 degrees away from the player)
        if (targetPos == null) {
            targetPos = getPosAway(minHorizontalDist, maxHorizontalDist, maxVerticalDist, 1.5D * Math.PI, this.playerToAvoid.position(), isValidSandPos);
        }

        // If still no sand blocks are found, try to find any block away from the player
        if (targetPos == null) {
            targetPos = getPosAway(minHorizontalDist, maxHorizontalDist, maxVerticalDist, Math.PI / 2, this.playerToAvoid.position(), null);
        }

        // As a last resort, find any block away from the player with no minimum horizontal distance requirement
        if (targetPos == null) {
            targetPos = getPosAway(0, maxHorizontalDist, maxVerticalDist, Math.PI / 2, this.playerToAvoid.position(), null);
        }

        // If still no blocks are found, it's gg

        return targetPos;
    }

    /**
     * Attempts to find a position away from the avoidPos for the snapper to run to.
     * @param maxHorizontalDist The maximum horizontal distance away from the avoidPos to search for a position.
     * @param maxVerticalDist The maximum vertical distance away from the avoidPos to search for a position.
     * @param searchAngle The angle in radians to search for a position in.
     *                    E.g. Math.PI / 2 will search in a 90 degree arc away from the avoidPos.
     * @param avoidPos The position to avoid.
     * @param blockFilter A function that returns true if the block at the given position is valid, or null if any block is valid.
     * @return The position to run to, or null if no valid position is found.
     */
    @Nullable
    private Vec3 getPosAway(int minHorizontalDist, int maxHorizontalDist, int maxVerticalDist, double searchAngle, Vec3 avoidPos,
                            @Nullable Function<BlockPos, Boolean> blockFilter
    ) {
        Vec3 awayDirection = this.sandSnapper.position().subtract(avoidPos);
        return RandomPos.generateRandomPos(this.sandSnapper, () -> {
            BlockPos randomlyOffsetPos = generateRandomOffsetInDirection(this.sandSnapper.getRandom(), minHorizontalDist, maxHorizontalDist, maxVerticalDist,
                    awayDirection.x, awayDirection.z, searchAngle);
            BlockPos pos = new BlockPos(this.sandSnapper.getX() + randomlyOffsetPos.getX(),
                    this.sandSnapper.getY() + randomlyOffsetPos.getY(),
                    this.sandSnapper.getZ() + randomlyOffsetPos.getZ());

            return blockFilter == null || blockFilter.apply(pos) ? pos : null;
        });
    }

    /**
     * Generates a random offset in the given direction, with constraints on distance and angle.
     * @param random The mob's random.
     * @param maxHorizontalDistance The maximum horizontal distance to offset.
     * @param maxVerticalDistance The maximum vertical distance to offset.
     * @param dirX The x component of the direction to offset in.
     * @param dirZ The z component of the direction to offset in.
     * @param searchAngle The angle in radians to search for a position in.
     *                    E.g. Math.PI / 2 will search in a 90 degree arc centered on the aforementioned direction.
     * @return The randomly generated offset.
     */
    private static BlockPos generateRandomOffsetInDirection(Random random, int minHorizontalDist, int maxHorizontalDistance, int maxVerticalDistance,
                                                            double dirX, double dirZ, double searchAngle) {
        double theta = Math.atan2(dirZ, dirX); // angle between x-axis and point, from -pi to +pi
        double randomAngleOffset = (random.nextDouble() * searchAngle) - (searchAngle / 2.0D); // random angle offset from -searchAngle/2 to +searchAngle/2
        double randomAngle = theta + randomAngleOffset;

//        double amplitude = Math.sqrt(random.nextDouble()) * (double) maxHorizontalDistance;
        double amplitude = Math.sqrt(random.nextDouble()) * (double) (maxHorizontalDistance - minHorizontalDist) + minHorizontalDist;
        double randomX = amplitude * Math.cos(randomAngle);
        double randomZ = amplitude * Math.sin(randomAngle);
        int randomY = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance;

        return new BlockPos(randomX, randomY, randomZ);
    }

    private boolean isInActiveSandstorm() {
        // TODO - check if the sand snapper is in a Lost Caves biome when checking for sandstorm
        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();
        return sandstormServerData.isSandstormActive();
    }
}
