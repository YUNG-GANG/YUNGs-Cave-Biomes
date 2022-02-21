package com.yungnickyoung.minecraft.yungscavebiomes.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class IceCubeEntity extends Monster {
    private boolean wasOnGround;
    public float targetSquish;
    public float squish;
    public float oSquish;

    public IceCubeEntity(EntityType<? extends IceCubeEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new IceCubeMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new IceCubeFloatGoal(this));
        this.goalSelector.addGoal(2, new IceCubeAttackGoal(this));
        this.goalSelector.addGoal(3, new IceCubeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new IceCubeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0f)
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
    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5f;
        this.oSquish = this.squish;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            for (int j = 0; j < 3 * 8; ++j) {
                float f = this.random.nextFloat() * ((float)Math.PI * 2);
                float g = this.random.nextFloat() * 0.5f + 0.5f;
                float xOffset = Mth.sin(f) * (float)3 * 0.5f * g;
                float zOffset = Mth.cos(f) * (float)3 * 0.5f * g;
                this.level.addParticle(this.getParticleType(), this.getX() + (double)xOffset, this.getY(), this.getZ() + (double)zOffset, 0.0, 0.0, 0.0);
                this.targetSquish = -0.5f;
            }
            this.playSound(SoundEvents.SLIME_SQUISH, this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
        } else if (!this.onGround && this.wasOnGround) {
            this.targetSquish = 1.0f;
        }
        this.wasOnGround = this.onGround;
        this.decreaseSquish();
    }

    protected void decreaseSquish() {
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
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.15f;
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    float getSoundPitch() {
        float f = 0.8f;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * f;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.GLASS_STEP;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP;
    }

    @Override
    public int getMaxHeadXRot() {
        return 0;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.SNOWFLAKE;
    }

    static class IceCubeMoveControl extends MoveControl {
        private final IceCubeEntity iceCube;
        private float yRotDegrees;
        private boolean isAggressive;
        private int jumpDelay;

        public IceCubeMoveControl(IceCubeEntity iceCube) {
            super(iceCube);
            this.iceCube = iceCube;
            this.yRotDegrees = 180.0f * iceCube.getYRot() / (float)Math.PI;
        }

        public void setDirection(float yRot, boolean isAggressive) {
            this.yRotDegrees = yRot;
            this.isAggressive = isAggressive;
        }

        public void setWantedMovement(double speedModifier) {
            this.speedModifier = speedModifier;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        @Override
        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRotDegrees, 90.0f));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0f);
                return;
            }
            this.operation = MoveControl.Operation.WAIT;
            if (this.mob.isOnGround()) {
                this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = this.iceCube.getJumpDelay();
                    if (this.isAggressive) {
                        this.jumpDelay /= 3;
                    }
                    this.iceCube.getJumpControl().jump();
                    this.iceCube.playSound(this.iceCube.getJumpSound(), this.iceCube.getSoundVolume(), this.iceCube.getSoundPitch());
                } else {
                    this.iceCube.xxa = 0.0f;
                    this.iceCube.zza = 0.0f;
                    this.mob.setSpeed(0.0f);
                }
            } else {
                this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }
    }

    static class IceCubeFloatGoal extends Goal {
        private final IceCubeEntity iceCube;

        public IceCubeFloatGoal(IceCubeEntity iceCube) {
            this.iceCube = iceCube;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            iceCube.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return (this.iceCube.isInWater() || this.iceCube.isInLava()) && this.iceCube.getMoveControl() instanceof IceCubeMoveControl;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.iceCube.getRandom().nextFloat() < 0.8f) {
                this.iceCube.getJumpControl().jump();
            }
            ((IceCubeMoveControl)this.iceCube.getMoveControl()).setWantedMovement(1.2);
        }
    }

    static class IceCubeAttackGoal extends Goal {
        private final IceCubeEntity iceCube;
        private int growTiredTimer;

        public IceCubeAttackGoal(IceCubeEntity iceCube) {
            this.iceCube = iceCube;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.iceCube.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!this.iceCube.canAttack(livingEntity)) {
                return false;
            }
            return this.iceCube.getMoveControl() instanceof IceCubeMoveControl;
        }

        @Override
        public void start() {
            this.growTiredTimer = IceCubeEntity.IceCubeAttackGoal.reducedTickDelay(300);
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.iceCube.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!this.iceCube.canAttack(livingEntity)) {
                return false;
            }
            return --this.growTiredTimer > 0;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.iceCube.getTarget();
            if (livingEntity != null) {
                this.iceCube.lookAt(livingEntity, 10.0f, 10.0f);
            }
            ((IceCubeMoveControl)this.iceCube.getMoveControl()).setDirection(this.iceCube.getYRot(), true);
        }
    }

    static class IceCubeRandomDirectionGoal extends Goal {
        private final IceCubeEntity iceCube;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public IceCubeRandomDirectionGoal(IceCubeEntity iceCube) {
            this.iceCube = iceCube;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.iceCube.getTarget() == null
                    && (this.iceCube.onGround || this.iceCube.isInWater() || this.iceCube.isInLava() || this.iceCube.hasEffect(MobEffects.LEVITATION))
                    && this.iceCube.getMoveControl() instanceof IceCubeMoveControl;
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.iceCube.getRandom().nextInt(60));
                this.chosenDegrees = this.iceCube.getRandom().nextInt(360);
            }
            ((IceCubeMoveControl)this.iceCube.getMoveControl()).setDirection(this.chosenDegrees, false);
        }
    }

    static class IceCubeKeepOnJumpingGoal extends Goal {
        private final IceCubeEntity iceCube;

        public IceCubeKeepOnJumpingGoal(IceCubeEntity iceCube) {
            this.iceCube = iceCube;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.iceCube.isPassenger();
        }

        @Override
        public void tick() {
            ((IceCubeMoveControl)this.iceCube.getMoveControl()).setWantedMovement(1.0);
        }
    }
}
