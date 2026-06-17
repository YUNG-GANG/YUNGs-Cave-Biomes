package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "onEffectsRemoved", at = @At("HEAD"))
    private void yungscavebiomes_resetFrostTicksWhenClearingEffect(final Collection<MobEffectInstance> effects, final CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            if (effects.stream().anyMatch(e -> e.is(MobEffectModule.FROZEN_EFFECT.getHolder()))) {
                this.setTicksFrozen(1);
            }
        }
    }
}
