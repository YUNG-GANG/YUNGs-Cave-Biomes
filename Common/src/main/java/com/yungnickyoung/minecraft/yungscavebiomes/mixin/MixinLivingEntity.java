package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow @Nullable public abstract MobEffectInstance getEffect(MobEffect mobEffect);

    @Shadow public abstract boolean addEffect(MobEffectInstance mobEffectInstance);

    public MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void yungscavebiomes_buffetEntitiesInSandstorm(CallbackInfo ci) {
        if (YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms
                && !this.level.isClientSide
                && !this.isSpectator()
                && this.tickCount % 10 == 0
                && this.level.getBiome(this.blockPosition()).is(BiomeModule.LOST_CAVES.getResourceKey())
        ) {
            MobEffectInstance buffetedEffect = this.getEffect(MobEffectModule.BUFFETED_EFFECT.get());
            if (buffetedEffect == null || buffetedEffect.getDuration() < 60) {
                this.addEffect(new MobEffectInstance(MobEffectModule.BUFFETED_EFFECT.get(), 100, 0, false, false, true));
            }
        }
    }
}
