package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.DamageSourceAccessor;
import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceModule {
    public static final DamageSource PRICKLY_VINES = DamageSourceAccessor.createDamageSource("pricklyVines");

    public static void init() {} // Initializes static fields when called
}
