//package com.yungnickyoung.minecraft.yungscavebiomes.entity;
//
//import com.google.common.collect.Lists;
//import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
//import net.minecraft.advancements.CriteriaTriggers;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.util.Mth;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.damagesource.IndirectEntityDamageSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.AbstractArrow;
//import net.minecraft.world.entity.projectile.Projectile;
//import net.minecraft.world.entity.projectile.ProjectileUtil;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraft.world.level.ClipContext;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.EntityHitResult;
//import net.minecraft.world.phys.HitResult;
//import net.minecraft.world.phys.Vec3;
//import net.minecraft.world.phys.shapes.VoxelShape;
//
//import javax.annotation.Nullable;
//import java.util.List;
//
//public class IcicleProjectileEntityOld extends Projectile {
//    private double baseDamage = 1.0;
//    private int knockback = 0;
//
//    @Nullable
//    private IntOpenHashSet piercingIgnoreEntityIds;
//
//    @Nullable
//    private List<Entity> piercedAndKilledEntities;
//
//    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(IcicleProjectileEntityOld.class, EntityDataSerializers.BYTE);
//
//    public IcicleProjectileEntityOld(EntityType<? extends IcicleProjectileEntityOld> entityType, Level level) {
//        super(entityType, level);
//    }
//
//    protected IcicleProjectileEntityOld(EntityType<? extends IcicleProjectileEntityOld> entityType, double x, double y, double z, Level level) {
//        this(entityType, level);
//        this.setPos(x, y, z);
//    }
//
//    protected IcicleProjectileEntityOld(EntityType<? extends IcicleProjectileEntityOld> entityType, LivingEntity livingEntity, Level level) {
//        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - (double)0.1f, livingEntity.getZ(), level);
//        this.setOwner(livingEntity);
//    }
//
//    @Override
//    public boolean shouldRenderAtSqrDistance(double distance) {
//        double e = this.getBoundingBox().getSize() * 10.0;
//        if (Double.isNaN(e)) {
//            e = 1.0;
//        }
//        return distance < (e *= 64.0 * IcicleProjectileEntityOld.getViewScale()) * e;
//    }
//
//    @Override
//    protected void defineSynchedData() {
//        this.entityData.define(PIERCE_LEVEL, (byte) 0);
//    }
//
//    public void setPierceLevel(byte $$0) {
//        this.entityData.set(PIERCE_LEVEL, $$0);
//    }
//
//    public byte getPierceLevel() {
//        return this.entityData.get(PIERCE_LEVEL);
//    }
//
//    @Override
//    public boolean alwaysAccepts() {
//        return super.alwaysAccepts();
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        Vec3 currPosition;
//        BlockPos blockPos = this.blockPosition();
//        BlockState blockState = this.level.getBlockState(blockPos);
//        VoxelShape blockCollisionShape = blockState.getCollisionShape(this.level, blockPos);
//        Vec3 deltaMovement = this.getDeltaMovement();
//
//        if (this.xRotO == 0.0f && this.yRotO == 0.0f) {
//            double d2 = deltaMovement.horizontalDistance();
//            this.setYRot((float)(Mth.atan2(deltaMovement.x, deltaMovement.z) * 57.2957763671875));
//            this.setXRot((float)(Mth.atan2(deltaMovement.y, d2) * 57.2957763671875));
//            this.xRotO = this.getXRot();
//            this.yRotO = this.getYRot();
//        }
//
//        // Check if in ground
////        if (!blockState.isAir() && !noPhysics && !blockCollisionShape.isEmpty()) {
////            currPosition = this.position();
////            for (AABB aABB : blockCollisionShape.toAabbs()) {
////                if (!aABB.move(blockPos).contains(currPosition)) continue;
////                this.inGround = true;
////                break;
////            }
////        }
//
//        // Update state if in ground
////        if (this.inGround && !noPhysics) {
////            if (this.lastState != blockState && this.shouldFall()) {
////                this.startFalling();
////            } else if (!this.level.isClientSide) {
////                this.tickDespawn();
////            }
////            ++this.inGroundTime;
////            return;
////        }
////        this.inGroundTime = 0;
//
//        Vec3 oldPosition = this.position();
//        currPosition = oldPosition.add(deltaMovement);
//
//        HitResult hitResult = this.level.clip(new ClipContext(oldPosition, currPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
//        if (hitResult.getType() != HitResult.Type.MISS) {
//            currPosition = hitResult.getLocation();
//        }
//
//        while (!this.isRemoved()) {
//            EntityHitResult entityHitResult = this.findHitEntity(oldPosition, currPosition);
//            if (entityHitResult != null) {
//                hitResult = entityHitResult;
//            }
//            if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
//                Entity entityHit = ((EntityHitResult)hitResult).getEntity();
//                Entity owner = this.getOwner();
//                if (entityHit instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)entityHit)) {
//                    hitResult = null;
//                    entityHitResult = null;
//                }
//            }
//            if (hitResult != null && !noPhysics) {
//                this.onHit(hitResult);
//                this.hasImpulse = true;
//            }
//            if (entityHitResult == null || this.getPierceLevel() <= 0) {
//                break;
//            }
//            hitResult = null;
//        }
//
//        deltaMovement = this.getDeltaMovement();
//        double dx = deltaMovement.x;
//        double dy = deltaMovement.y;
//        double dz = deltaMovement.z;
//
////        if (this.isCritArrow()) {
////            for (int i = 0; i < 4; ++i) {
////                this.level.addParticle(ParticleTypes.CRIT, this.getX() + dx * (double)i / 4.0, this.getY() + dy * (double)i / 4.0, this.getZ() + dz * (double)i / 4.0, -dx, -dy + 0.2, -dz);
////            }
////        }
//
//        double newX = this.getX() + dx;
//        double newY = this.getY() + dy;
//        double newZ = this.getZ() + dz;
//        double deltaMovementHorizontal = deltaMovement.horizontalDistance();
//
//        if (noPhysics) {
//            this.setYRot((float)(Mth.atan2(-dx, -dz) * 57.2957763671875));
//        } else {
//            this.setYRot((float)(Mth.atan2(dx, dz) * 57.2957763671875));
//        }
//
//        this.setXRot((float)(Mth.atan2(dy, deltaMovementHorizontal) * 57.2957763671875));
//        this.setXRot(AbstractArrow.lerpRotation(this.xRotO, this.getXRot()));
//        this.setYRot(AbstractArrow.lerpRotation(this.yRotO, this.getYRot()));
//
//        float velocityAcceleration = 0.99f;
//
//        // If in water, create bubble particles & decelerate
//        if (this.isInWater()) {
//            for (int l = 0; l < 4; ++l) {
//                float m = 0.25f;
//                this.level.addParticle(ParticleTypes.BUBBLE, newX - dx * m, newY - dy * m, newZ - dz * m, dx, dy, dz);
//            }
//            velocityAcceleration = this.getWaterInertia();
//        }
//
//        // Update horizontal velocity
//        this.setDeltaMovement(deltaMovement.scale(velocityAcceleration));
//
//        // Update vertical velocity
//        if (!this.isNoGravity() && !noPhysics) {
//            Vec3 l = this.getDeltaMovement();
//            this.setDeltaMovement(l.x, l.y - 0.05, l.z);
//        }
//
//        this.setPos(newX, newY, newZ);
//        this.checkInsideBlocks();
//    }
//
//    protected float getWaterInertia() {
//        return 0.6F;
//    }
//
//    @Nullable
//    protected EntityHitResult findHitEntity(Vec3 vec3, Vec3 vec32) {
//        return ProjectileUtil.getEntityHitResult(this.level, this, vec3, vec32, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), this::canHitEntity);
//    }
//
//    private void resetPiercedEntities() {
//        if (this.piercedAndKilledEntities != null) {
//            this.piercedAndKilledEntities.clear();
//        }
//
//        if (this.piercingIgnoreEntityIds != null) {
//            this.piercingIgnoreEntityIds.clear();
//        }
//
//    }
//
//    protected void onHitEntity(EntityHitResult entityHitResult) {
//        super.onHitEntity(entityHitResult);
//        Entity entityHit = entityHitResult.getEntity();
//        float deltaMovement = (float)this.getDeltaMovement().length();
//        int damage = Mth.ceil(Mth.clamp((double)deltaMovement * this.baseDamage, 0.0D, 2.147483647E9D));
//        if (this.getPierceLevel() > 0) {
//            if (this.piercingIgnoreEntityIds == null) {
//                this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
//            }
//
//            if (this.piercedAndKilledEntities == null) {
//                this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
//            }
//
//            if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
//                this.discard();
//                return;
//            }
//
//            this.piercingIgnoreEntityIds.add(entityHit.getId());
//        }
//
//        if (this.isCritArrow()) {
//            long $$4 = (long)this.random.nextInt($$3 / 2 + 2);
//            $$3 = (int)Math.min($$4 + (long)$$3, 2147483647L);
//        }
//
//        Entity owner = this.getOwner();
//        DamageSource damageSource;
//        if (owner == null) {
//            damageSource = new IndirectEntityDamageSource("icicle", this, this).setProjectile();
//        } else {
//            damageSource = new IndirectEntityDamageSource("icicle", this, owner).setProjectile();
//            if (owner instanceof LivingEntity) {
//                ((LivingEntity) owner).setLastHurtMob(entityHit);
//            }
//        }
//
//        boolean isHitEnderman = entityHit.getType() == EntityType.ENDERMAN;
//        int entityHitRemainingFireTicks = entityHit.getRemainingFireTicks();
//
//        if (entityHit.hurt(damageSource, (float)damage)) {
//            if (isHitEnderman) {
//                return;
//            }
//
//            if (entityHit instanceof LivingEntity livingEntityHit) {
//                // Apply knockback to target
//                if (this.knockback > 0) {
//                    Vec3 icicleKnockback = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D);
//                    if (icicleKnockback.lengthSqr() > 0.0D) {
//                        livingEntityHit.push(icicleKnockback.x, 0.1D, icicleKnockback.z);
//                    }
//                }
//
//                if (!this.level.isClientSide && owner instanceof LivingEntity livingEntityOwner) {
//                    EnchantmentHelper.doPostHurtEffects(livingEntityHit, owner);
//                    EnchantmentHelper.doPostDamageEffects(livingEntityOwner, livingEntityHit);
//                }
//
//                this.doPostHurtEffects(livingEntityHit);
//                if (livingEntityHit != owner && livingEntityHit instanceof Player && owner instanceof ServerPlayer && !this.isSilent()) {
//                    ((ServerPlayer) owner).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F)); //TODO - packet that sends icicle sound
//                }
//
//                if (!entityHit.isAlive() && this.piercedAndKilledEntities != null) {
//                    this.piercedAndKilledEntities.add(livingEntityHit);
//                }
//
//                if (!this.level.isClientSide && owner instanceof ServerPlayer serverPlayerOwner) {
//                    if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
//                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverPlayerOwner, this.piercedAndKilledEntities);
//                    } else if (!entityHit.isAlive() && this.shotFromCrossbow()) {
//                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverPlayerOwner, List.of(entityHit));
//                    }
//                }
//            }
//
//            this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
//            if (this.getPierceLevel() <= 0) {
//                this.discard();
//            }
//        } else {
//            entityHit.setRemainingFireTicks(entityHitRemainingFireTicks);
//            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
//            this.setYRot(this.getYRot() + 180.0F);
//            this.yRotO += 180.0F;
//            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
////                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
////                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
////                }
//
//                this.discard();
//            }
//        }
//    }
//
//    protected void onHitBlock(BlockHitResult $$0) {
////        this.lastState = this.level.getBlockState($$0.getBlockPos());
//        super.onHitBlock($$0);
//        Vec3 deltaMovement = $$0.getLocation().subtract(this.getX(), this.getY(), this.getZ());
//        this.setDeltaMovement(deltaMovement);
//        Vec3 $$2 = deltaMovement.normalize().scale(0.05F);
//        this.setPosRaw(this.getX() - $$2.x, this.getY() - $$2.y, this.getZ() - $$2.z);
//        this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
////        this.inGround = true;
////        this.shakeTime = 7;
////        this.setCritArrow(false);
//        this.setPierceLevel((byte)0);
//        this.setSoundEvent(SoundEvents.GLASS_BREAK);
//        this.setShotFromCrossbow(false);
//        this.resetPiercedEntities();
//    }
//
//    protected boolean canHitEntity(Entity $$0x) {
//        return super.canHitEntity($$0x) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains($$0x.getId()));
//    }
//
//
//}
