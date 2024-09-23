package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Unique
    private static final int BUFFETED_EFFECT_DURATION = 100; // In ticks

    @Unique
    private static final int BUFFETED_EFFECT_REAPPLY_THRESHOLD = 60; // In ticks

    @Shadow
    @Nullable
    public abstract MobEffectInstance getEffect(MobEffect mobEffect);

    @Shadow
    public abstract boolean addEffect(MobEffectInstance mobEffectInstance);

    public MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Applies the Buffeted effect to entities in the Lost Caves during sandstorms.
     */
    @Inject(method = "tick", at = @At("RETURN"))
    public void yungscavebiomes_buffetEntitiesInSandstorm(CallbackInfo ci) {
        if (YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms
                && this.isPlayer(this)
                && !this.level().isClientSide
                && !this.isSpectator()
                && this.tickCount % 10 == 0
                && this.level() instanceof ServerLevel serverLevel
                && ((ISandstormServerDataProvider) serverLevel).getSandstormServerData().isSandstormActive()
                && this.level().getBiome(this.blockPosition()).is(BiomeModule.LOST_CAVES)
        ) {
            MobEffectInstance buffetedEffect = this.getEffect(MobEffectModule.BUFFETED_EFFECT.get());
            if (buffetedEffect == null || buffetedEffect.getDuration() < BUFFETED_EFFECT_REAPPLY_THRESHOLD) {
                this.addEffect(new MobEffectInstance(MobEffectModule.BUFFETED_EFFECT.get(), BUFFETED_EFFECT_DURATION, 0, false, false, true));
            }
        }
    }

    @Unique
    private boolean isPlayer(Object object) {
        return object instanceof Player;
    }
}
