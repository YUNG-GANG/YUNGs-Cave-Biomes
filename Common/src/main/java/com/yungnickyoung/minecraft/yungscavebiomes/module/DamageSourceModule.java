package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class DamageSourceModule {
    private static final ResourceKey<DamageType> PRICKLY_VINES_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, YungsCaveBiomesCommon.id("pricklyVines"));
    private static final ResourceKey<DamageType> ICICLE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, YungsCaveBiomesCommon.id("icicle"));

    public static DamageSource PRICKLY_VINES;
    public static DamageSource ICICLE;

    public static void init(RegistryAccess registryAccess) {
        PRICKLY_VINES = new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(PRICKLY_VINES_KEY));
        ICICLE = new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ICICLE_KEY));
    }
}
