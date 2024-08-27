package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DamageSourceModule;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer {
    /**
     * Uses the sweet berry bush hurt sound for prickly vines damage.
     * NOTE -- currently does nothing since we do not emit a distinct entity event for Prickly Vines damage
     * (that system is incredibly stupid and not easily extensible for modders)
     */
    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    public void yungscavebiomes_playerPricklyVinesSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (damageSource == DamageSourceModule.PRICKLY_VINES) {
            cir.setReturnValue(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH);
        }
    }
}
