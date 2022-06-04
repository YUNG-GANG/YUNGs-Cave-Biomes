package com.yungnickyoung.minecraft.yungscavebiomes.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IcicleProjectileEntity extends Projectile {
    private double baseDamage = 1.0;
    private int knockback = 0;

    public IcicleProjectileEntity(EntityType<? extends IcicleProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    protected IcicleProjectileEntity(EntityType<? extends IcicleProjectileEntity> entityType, double x, double y, double z, Level level) {
        this(entityType, level);
        this.setPos(x, y, z);
    }

    protected IcicleProjectileEntity(EntityType<? extends IcicleProjectileEntity> entityType, LivingEntity livingEntity, Level level) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - (double)0.1f, livingEntity.getZ(), level);
        this.setOwner(livingEntity);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double e = this.getBoundingBox().getSize() * 10.0;
        if (Double.isNaN(e)) {
            e = 1.0;
        }
        return distance < (e *= 64.0 * IcicleProjectileEntity.getViewScale()) * e;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 currPosition;
        BlockPos blockPos = this.blockPosition();
        BlockState blockState = this.level.getBlockState(blockPos);
        VoxelShape blockCollisionShape = blockState.getCollisionShape(this.level, blockPos);
        Vec3 deltaMovement = this.getDeltaMovement();

        if (this.xRotO == 0.0f && this.yRotO == 0.0f) {
            double d2 = deltaMovement.horizontalDistance();
            this.setYRot((float)(Mth.atan2(deltaMovement.x, deltaMovement.z) * 57.2957763671875));
            this.setXRot((float)(Mth.atan2(deltaMovement.y, d2) * 57.2957763671875));
            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
        }

        // Check if in ground
//        if (!blockState.isAir() && !noPhysics && !blockCollisionShape.isEmpty()) {
//            currPosition = this.position();
//            for (AABB aABB : blockCollisionShape.toAabbs()) {
//                if (!aABB.move(blockPos).contains(currPosition)) continue;
//                this.inGround = true;
//                break;
//            }
//        }

        // Update state if in ground
//        if (this.inGround && !noPhysics) {
//            if (this.lastState != blockState && this.shouldFall()) {
//                this.startFalling();
//            } else if (!this.level.isClientSide) {
//                this.tickDespawn();
//            }
//            ++this.inGroundTime;
//            return;
//        }
//        this.inGroundTime = 0;

        Vec3 oldPosition = this.position();
        currPosition = oldPosition.add(deltaMovement);

        HitResult hitResult = this.level.clip(new ClipContext(oldPosition, currPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            currPosition = hitResult.getLocation();
        }

//        while (!this.isRemoved()) {
            EntityHitResult entityHitResult = this.findHitEntity(oldPosition, currPosition);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }
            if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                Entity entityHit = ((EntityHitResult)hitResult).getEntity();
                Entity owner = this.getOwner();
                if (entityHit instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)entityHit)) {
                    hitResult = null;
                }
            }
            if (hitResult != null && !noPhysics) {
                this.onHit(hitResult);
                this.hasImpulse = true;
            }
//            if (this.getPierceLevel() <= 0) break;
//            hitResult = null;
//        }

        deltaMovement = this.getDeltaMovement();
        double dx = deltaMovement.x;
        double dy = deltaMovement.y;
        double dz = deltaMovement.z;

//        if (this.isCritArrow()) {
//            for (int i = 0; i < 4; ++i) {
//                this.level.addParticle(ParticleTypes.CRIT, this.getX() + dx * (double)i / 4.0, this.getY() + dy * (double)i / 4.0, this.getZ() + dz * (double)i / 4.0, -dx, -dy + 0.2, -dz);
//            }
//        }

        double newX = this.getX() + dx;
        double newY = this.getY() + dy;
        double newZ = this.getZ() + dz;
        double deltaMovementHorizontal = deltaMovement.horizontalDistance();

        if (noPhysics) {
            this.setYRot((float)(Mth.atan2(-dx, -dz) * 57.2957763671875));
        } else {
            this.setYRot((float)(Mth.atan2(dx, dz) * 57.2957763671875));
        }

        this.setXRot((float)(Mth.atan2(dy, deltaMovementHorizontal) * 57.2957763671875));
        this.setXRot(AbstractArrow.lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(AbstractArrow.lerpRotation(this.yRotO, this.getYRot()));

        float velocityAcceleration = 0.99f;

        // If in water, create bubble particles & decelerate
        if (this.isInWater()) {
            for (int l = 0; l < 4; ++l) {
                float m = 0.25f;
                this.level.addParticle(ParticleTypes.BUBBLE, newX - dx * m, newY - dy * m, newZ - dz * m, dx, dy, dz);
            }
//            velocityAcceleration = this.getWaterInertia();
        }

        // Update horizontal velocity
        this.setDeltaMovement(deltaMovement.scale(velocityAcceleration));

        // Update vertical velocity
        if (!this.isNoGravity() && !noPhysics) {
            Vec3 l = this.getDeltaMovement();
            this.setDeltaMovement(l.x, l.y - 0.05, l.z);
        }

        this.setPos(newX, newY, newZ);
        this.checkInsideBlocks();
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 vec3, Vec3 vec32) {
        return ProjectileUtil.getEntityHitResult(this.level, this, vec3, vec32, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), this::canHitEntity);
    }
}
