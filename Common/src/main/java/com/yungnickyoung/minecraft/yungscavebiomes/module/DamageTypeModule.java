package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class DamageTypeModule {
    public static final ResourceKey<DamageType> PRICKLY_VINES = ResourceKey.create(Registries.DAMAGE_TYPE, YungsCaveBiomesCommon.id("prickly_vines"));
    public static final ResourceKey<DamageType> FALLING_ICICLE = ResourceKey.create(Registries.DAMAGE_TYPE, YungsCaveBiomesCommon.id("falling_icicle"));
}
