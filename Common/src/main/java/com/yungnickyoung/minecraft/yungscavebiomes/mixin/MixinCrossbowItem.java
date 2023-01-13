package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CrossbowItem.class)
public abstract class MixinCrossbowItem {
    @Inject(method = "shootProjectile", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_shootIcicleProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack bowItemStack, ItemStack projectileItemStack, float $$5, boolean creative, float $$7, float $$8, float $$9, CallbackInfo ci) {
        if (!level.isClientSide && projectileItemStack.is(BlockModule.ICICLE.get().asItem())) {
            AbstractArrow projectile = getProjectile(level, shooter, bowItemStack, projectileItemStack);

//            if (creative || $$9 != 0.0F) {
//                projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
//            }

            if (shooter instanceof CrossbowAttackMob crossbowAttackMob && crossbowAttackMob.getTarget() != null) {
                crossbowAttackMob.shootCrossbowProjectile(crossbowAttackMob.getTarget(), bowItemStack, projectile, $$9);
            } else {
                Vec3 upVector = shooter.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(upVector), $$9, true);
                Vec3 viewVector = shooter.getViewVector(1.0F);
                Vector3f vector3f = new Vector3f(viewVector);
                vector3f.transform(quaternion);
                projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), $$7, $$8);
            }

            bowItemStack.hurtAndBreak(1, shooter, ($$1x) -> $$1x.broadcastBreakEvent(hand));
            level.addFreshEntity(projectile);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, $$5);
            ci.cancel();
        }
    }

    @Inject(method = "getSupportedHeldProjectiles", at = @At("RETURN"), cancellable = true)
    private void yungscavebiomes_supportIciclesInCrossbows(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(cir.getReturnValue().or((itemStack) -> itemStack.is(BlockModule.ICICLE.get().asItem())));
    }

    @Inject(method = "getAllSupportedProjectiles", at = @At("RETURN"), cancellable = true)
    private void yungscavebiomes_supportIciclesInCrossbows2(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(cir.getReturnValue().or((itemStack) -> itemStack.is(BlockModule.ICICLE.get().asItem())));
    }

    private static AbstractArrow getProjectile(Level level, LivingEntity shooter, ItemStack $$2, ItemStack $$3) {
        IcicleProjectileEntity icicle = IcicleProjectileEntity.create(level, shooter);
        icicle.setSoundEvent(SoundEvents.GLASS_BREAK);
        icicle.setShotFromCrossbow(true);
        return icicle;
    }
}
