package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper;

import com.mojang.math.Vector3d;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.EmergeGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.RunFromPlayerGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.SnapperStrollGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TagModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SandSnapperEntity extends PathfinderMob implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private final AnimationBuilder EMERGE = new AnimationBuilder().addAnimation("look");
    private final AnimationBuilder DIVE = new AnimationBuilder().addAnimation("diveback");
    private final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");
    private final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk");

    private static final EntityDataAccessor<Boolean> SUBMERGED = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EMERGED = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DIVING = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean divingHolder;
    private boolean emergedHolder;
    private boolean submergedHolder;

    private int divingTimer;
    private static final int DIVING_LENGTH = 15;

    /**
     * Timer tracking how long the Snapper has been above ground, in ticks.
     */
    private int aboveGroundTimer;

    /**
     * Minimum number of ticks the Snapper must be above ground before it can submerge.
     */
    private static final int MIN_TICKS_ABOVE_GROUND = 100;

    public SandSnapperEntity(EntityType<SandSnapperEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EMERGED, false);
        this.entityData.define(DIVING, false);
        this.entityData.define(SUBMERGED, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RunFromPlayerGoal(this, 16.0f, 1.25f));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SnapperStrollGoal(this, 1.0f, 1.1f));
        this.goalSelector.addGoal(2, new EmergeGoal(this, 20.0f, 2.0f, 100));
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.isEmerging() || this.isDiving()) {
            return EntityDimensions.fixed(0.4f, 0.7f);
        } else if (this.isSubmerged()) {
            return EntityDimensions.fixed(0.01f, 0.01f);
        }

        return super.getDimensions(pose);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        super.onSyncedDataUpdated(dataAccessor);

        if (dataAccessor.equals(EMERGED)) {
            if (this.level.isClientSide) {
                AnimationController controller = this.getFactory()
                        .getOrCreateAnimationData(this.getId())
                        .getAnimationControllers()
                        .get("generalController");
                if (controller != null) {
                    controller.markNeedsReload();
                }
            }

            this.emergedHolder = this.entityData.get(EMERGED);
            this.refreshDimensions();
        } else if (dataAccessor.equals(DIVING)) {
            this.divingHolder = this.entityData.get(DIVING);
            this.divingTimer = DIVING_LENGTH;
            this.refreshDimensions();
        } else if (dataAccessor.equals(SUBMERGED)) {
            this.submergedHolder = this.entityData.get(SUBMERGED);
            this.refreshDimensions();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            if (this.aboveGroundTimer > 0) {
                this.aboveGroundTimer--;
            }

            if (!this.isSubmerged() && this.canSubmerge(false)) {
                this.setSubmerged(true);
            } else if (this.isSubmerged() && !this.canSubmerge(false)) {
                this.setSubmerged(false);
            }

            if (this.isDiving()) {
                if (this.divingTimer > 0) {
                    this.divingTimer--;
                } else {
                    this.setDiving(false);
                    this.divingTimer = 0;
                }
            }
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isSubmerged()) return InteractionResult.FAIL;

        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.is(ItemModule.PRICKLY_PEACH_ITEM.get())) {
            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            }

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            this.heal(4.0f);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            this.level.broadcastEntityEvent(this, (byte) 7); // Heart particles event
            float pitch = Mth.randomBetween(this.random, 0.9f, 1.2f);
            this.playSound(SoundModule.SAND_SNAPPER_HAPPY.get(), 1.0f, pitch);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void handleEntityEvent(byte eventId) {
        if (eventId == 7) {
            this.spawnHeartParticles();
        } else {
            super.handleEntityEvent(eventId);
        }
    }

    protected void spawnHeartParticles() {
        for (int i = 0; i < 7; i++) {
            double xSpeed = this.random.nextGaussian() * 0.02;
            double ySpeed = this.random.nextGaussian() * 0.02;
            double zSpeed = this.random.nextGaussian() * 0.02;
            this.level.addParticle(ParticleTypes.HEART,
                    this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0),
                    xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return !this.isSubmerged() && super.canCollideWith(entity);
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && !this.isSubmerged();
    }

    @Override
    public void push(Entity entity) {
        if (!this.isSubmerged()) {
            super.push(entity);
        }
    }

    @Override
    public boolean isInvulnerable() {
        return this.isSubmerged() || super.isInvulnerable();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (this.isSubmerged() && source != DamageSource.OUT_OF_WORLD && !source.isCreativePlayer()) return true;
        return super.isInvulnerableTo(source);
    }

    @Override
    public float getWalkTargetValue(BlockPos movePos, LevelReader level) {
        // prefer moving on sand
        return level.getBlockState(movePos.below()).is(TagModule.SAND_SNAPPER_BLOCKS) ? 10.0F : 0.0F;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Unable to move while playing diving animation
        if (this.isDiving()) {
            this.getNavigation().stop();
        }

        // Spawn block particles when moving while submerged
        Vec3 movement = this.getDeltaMovement();
        if (this.level.isClientSide() && this.isSubmerged() && movement.length() > 0.1) {
            float width = this.getDimensions(this.getPose()).width * 0.8F;
            Vector3d particlePos = new Vector3d(
                    this.getX() + (this.random.nextDouble() - 0.5) * (double) width,
                    this.getY() + 0.1,
                    this.getZ() + (this.random.nextDouble() - 0.5) * (double) width);
            Vector3d particleSpeed = new Vector3d(movement.x * -4.0, 1.5, movement.z * -4.0);
            this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()),
                    particlePos.x, particlePos.y, particlePos.z,
                    particleSpeed.x, particleSpeed.y, particleSpeed.z);
        }
    }

    @Override
    public boolean isInWall() {
        if (this.noPhysics || this.isSleeping()) {
            return false;
        } else {
            float width = this.getDimensions(this.getPose()).width * 0.8F;
            AABB aabb = AABB.ofSize(this.getEyePosition(), width, 1.0E-6, width);
            return BlockPos.betweenClosedStream(aabb).anyMatch((pos) -> {
                BlockState blockState = this.level.getBlockState(pos);
                BlockState stateAbove = this.level.getBlockState(pos.above());
                return !blockState.isAir() && !(blockState.is(TagModule.SAND_SNAPPER_BLOCKS) && stateAbove.is(Blocks.AIR)) && blockState.isSuffocating(this.level, pos) && Shapes.joinIsNotEmpty(blockState.getCollisionShape(this.level, pos).move(pos.getX(), pos.getY(), pos.getZ()), Shapes.create(aabb), BooleanOp.AND);
            });
        }
    }

    public void setSubmerged(boolean isSubmerged) {
        this.entityData.set(SUBMERGED, isSubmerged);
        this.submergedHolder = isSubmerged;
        this.aboveGroundTimer = isSubmerged ? 0 : MIN_TICKS_ABOVE_GROUND;
        this.refreshDimensions();
    }

    /**
     * Returns whether the Snapper can submerge based on the surrounding blocks and the aboveGroundTimer.
     *
     * @param ignoreTimer If true, the aboveGroundTimer will be ignored and the Snapper will be able to submerge regardless,
     *                    as long as it's in a valid submerging position.
     * @return Whether the Snapper can submerge.
     */
    public boolean canSubmerge(boolean ignoreTimer) {
        if (!ignoreTimer && this.aboveGroundTimer > 0) return false;

        // Do these checks instead of just always setting based on the block it's on,
        // to allow dimension refreshing without cost
        BlockState blockStateOn = this.getBlockStateOn();
        BlockState blockStateBelow = this.level.getBlockState(this.getOnPos().below());
        return blockStateOn.is(TagModule.SAND_SNAPPER_BLOCKS) ||
                (blockStateOn.is(Blocks.AIR) && blockStateBelow.is(TagModule.SAND_SNAPPER_BLOCKS));
    }

    public void setEmerging(boolean isEmerging) {
        this.entityData.set(EMERGED, isEmerging);
        this.emergedHolder = isEmerging;
    }

    public boolean isEmerging() {
        return this.emergedHolder;
    }

    public void setDiving(boolean isDiving) {
        this.entityData.set(DIVING, isDiving);
        this.divingHolder = isDiving;
    }

    public boolean isDiving() {
        return this.divingHolder;
    }

    public boolean isSubmerged() {
        return this.submergedHolder && !this.isEmerging() && !this.isDiving();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource $$0) {
        return SoundModule.SAND_SNAPPER_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundModule.SAND_SNAPPER_DEATH.get();
    }

    public SoundEvent getPanicSound() {
        return SoundModule.SAND_SNAPPER_PANIC.get();
    }

    private <E extends IAnimatable> PlayState generalPredicate(AnimationEvent<E> event) {
        if (this.isEmerging()) {
            event.getController().setAnimation(EMERGE);
            return PlayState.CONTINUE;
        } else if (this.isDiving()) {
            event.getController().setAnimation(DIVE);
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            if (this.isSubmerged()) {
                event.getController().setAnimation(SWIM);
            } else {
                event.getController().setAnimation(WALK);
            }
            return PlayState.CONTINUE;
        }

        event.getController().markNeedsReload();

        return PlayState.STOP;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState stateOn) {
        if (this.isSubmerged()) return;

        super.playStepSound(pos, stateOn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "generalController", 2, this::generalPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
