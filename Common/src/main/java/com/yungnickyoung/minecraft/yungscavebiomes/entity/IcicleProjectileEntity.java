package com.yungnickyoung.minecraft.yungscavebiomes.entity;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.AbstractArrowAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IcicleProjectileEntity extends AbstractArrow {
    public IcicleProjectileEntity(EntityType<IcicleProjectileEntity> entityType, Level level) {
        super(entityType, level);
        this.setBaseDamage(1.5); // Regular arrows deal 2 damage
        this.pickup = Pickup.DISALLOWED;
        this.setSoundEvent(SoundEvents.GLASS_BREAK);
    }

    public IcicleProjectileEntity(Level level, LivingEntity shooter, ItemStack arrowItemStack, @Nullable ItemStack weaponItemStack) {
        super(EntityTypeModule.ICICLE.get(), shooter, level, arrowItemStack, weaponItemStack);
        this.pickup = Pickup.DISALLOWED;
        this.setSoundEvent(SoundEvents.GLASS_BREAK);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(BlockModule.ICICLE.get());
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(BlockModule.ICICLE.get());
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState hitBlockState = this.level().getBlockState(blockHitResult.getBlockPos());
        hitBlockState.onProjectileHit(this.level(), hitBlockState, blockHitResult, this);
        Vec3 $$1 = blockHitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement($$1);
        Vec3 $$2 = $$1.normalize().scale(0.05F);
        this.setPosRaw(this.getX() - $$2.x, this.getY() - $$2.y, this.getZ() - $$2.z);
//        this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
//        this.inGround = true;
//        this.shakeTime = 7;
        this.setCritArrow(false);
//        this.setPierceLevel((byte)0);
        this.setSoundEvent(SoundEvents.ARROW_HIT);
//        this.setShotFromCrossbow(false);
        ((AbstractArrowAccessor) this).callResetPiercedEntities();
        if (this.level() instanceof ServerLevel serverLevel) {
            Services.PLATFORM.sendIcicleProjectileShatterS2CPacket(serverLevel, this.position());
        }
        this.playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.discard();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            Services.PLATFORM.sendIcicleProjectileShatterS2CPacket(serverLevel, this.position());
        }
    }

    @Override
    public void tick() {
        super.tick();
    }
}
