package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DamageSourceModule;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public class DamageSourcesMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void yungscavebiomes_customDamageSources(RegistryAccess registryAccess, CallbackInfo ci) {
        DamageSourceModule.init(registryAccess);
    }
}
