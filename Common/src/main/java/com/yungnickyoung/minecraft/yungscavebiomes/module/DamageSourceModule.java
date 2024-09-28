package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class DamageSourceModule {
    public static DamageSource PRICKLY_VINES;
    public static DamageSource FALLING_ICICLE;

    public static void init(RegistryAccess registryAccess) {
        PRICKLY_VINES = new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypeModule.PRICKLY_VINES));
        FALLING_ICICLE = new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypeModule.FALLING_ICICLE));
    }
}
