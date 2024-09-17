package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal.IceCubeAttackGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal.IceCubeLeapGoal;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class IceCubeEntity extends Monster {
    private static final EntityDataAccessor<Boolean> IS_LEAPING = SynchedEntityData.defineId(IceCubeEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean wasOnGround;
    public float targetSquish;
    public float squish;
    public float oSquish;
    private int leapTicks;

    private final MoveControl slimeMoveControl;
    private final MoveControl slidingMoveControl;

    public IceCubeEntity(EntityType<? extends IceCubeEntity> entityType, Level level) {
        super(entityType, level);
        this.setDiscardFriction(true);
        this.xpReward = 12;

        this.slimeMoveControl = new IceCubeSlimeMoveControl(this);
        this.slidingMoveControl = new MoveControl(this);

        this.moveControl = this.slimeMoveControl;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, MagmaCube.class, 6.0f, 1.0, 1.2));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Blaze.class, 6.0f, 1.0, 1.2));
        this.goalSelector.addGoal(4, new IceCubeLeapGoal(this, 0.4f));
        this.goalSelector.addGoal(5, new IceCubeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 32.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("wasOnGround", this.wasOnGround);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.wasOnGround = compoundTag.getBoolean("wasOnGround");
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LEAPING, false);
    }

    public void setLeaping(boolean leaping) {
        this.entityData.set(IS_LEAPING, leaping);
        this.leapTicks = leaping ? 15 : 0;
    }

    public boolean getLeaping() {
        return this.entityData.get(IS_LEAPING);
    }

    @Override
    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5f;
        this.oSquish = this.squish;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            for (int j = 0; j < 3 * 8; ++j) {
                float f = this.random.nextFloat() * ((float) Math.PI * 2);
                float g = this.random.nextFloat() * 0.5f + 0.5f;
                float xOffset = Mth.sin(f) * (float) 3 * 0.5f * g;
                float zOffset = Mth.cos(f) * (float) 3 * 0.5f * g;
                this.level.addParticle(this.getParticleType(), this.getX() + (double) xOffset, this.getY(), this.getZ() + (double) zOffset, 0.0, 0.0, 0.0);
                this.targetSquish = -0.5f;
            }
            this.playSound(SoundEvents.SLIME_SQUISH, this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
        } else if (!this.onGround && this.wasOnGround) {
            this.targetSquish = 1.0f;
        }
        this.wasOnGround = this.onGround;
        this.decreaseSquish();

        if (this.getTarget() == null || this.distanceToSqr(this.getTarget()) > 100.0F || Math.abs(this.getY() - this.getTarget().getY()) / this.distanceToSqr(this.getTarget().getX(), this.getY(), this.getTarget().getZ()) > 0.5f) {
            this.moveControl = this.slimeMoveControl;
        } else {
            this.moveControl = this.slidingMoveControl;
        }
    }

    private void decreaseSquish() {
        this.targetSquish *= 0.6f;
    }

    /**
     * Runs around start of tick().
     * Used for changing mob behaviors, state, effects, etc.
     */
    @Override
    public void aiStep() {
        if (this.isAlive()) {
            // TODO - melt when near lava/fire/daylight?
        }

        if (!this.level.isClientSide() && this.getLeaping() && this.leapTicks-- <= 0) {
            this.setLeaping(false);
        }

        super.aiStep();
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    @Override
    public void playerTouch(Player player) {
        this.dealDamage(player);
    }

    public int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    @Override
    public void refreshDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.refreshDimensions();
        this.setPos(d, e, f);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        this.refreshDimensions();
        this.setYRot(this.yHeadRot);
        this.yBodyRot = this.yHeadRot;
        if (this.isInWater() && this.random.nextInt(20) == 0) {
            this.doWaterSplashEffect();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    protected void dealDamage(LivingEntity livingEntity) {
        if (this.isAlive()) {
            if (this.distanceToSqr(livingEntity) < 2.5 && this.hasLineOfSight(livingEntity) && livingEntity.hurt(DamageSource.mobAttack(this), this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                this.doEnchantDamageEffects(this, livingEntity);
                livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + 200);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.15f;
    }

    protected float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }

    float getSoundPitch() {
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.GLASS_STEP;
    }

    public SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP;
    }

    @Override
    public int getMaxHeadXRot() {
        return 0;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.SNOWFLAKE;
    }

    private static class IceCubeSlimeMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final IceCubeEntity iceCube;
        private boolean isAggressive;

        public IceCubeSlimeMoveControl(IceCubeEntity iceCube) {
            super(iceCube);
            this.iceCube = iceCube;
            this.yRot = 180.0F * iceCube.getYRot() / Mth.PI;
        }

        public void setDirection(float yRot, boolean isAggressive) {
            this.yRot = yRot;
            this.isAggressive = isAggressive;
        }

        public void setWantedMovement(double speedModifier) {
            this.speedModifier = speedModifier;
            this.operation = Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.isOnGround()) {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.iceCube.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.iceCube.getJumpControl().jump();
                        this.iceCube.playSound(this.iceCube.getJumpSound(), this.iceCube.getSoundVolume(), this.iceCube.getSoundPitch());
                    } else {
                        this.iceCube.xxa = 0.0F;
                        this.iceCube.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }
}
