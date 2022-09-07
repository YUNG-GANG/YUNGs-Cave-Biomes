package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterMobEffect;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.effect.FrostMobEffect;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class MobEffectModule {
    @AutoRegister("frost")
    public static final AutoRegisterMobEffect FROZEN_EFFECT = AutoRegisterMobEffect
            .of(() -> new FrostMobEffect(200, 100, 600));
}
