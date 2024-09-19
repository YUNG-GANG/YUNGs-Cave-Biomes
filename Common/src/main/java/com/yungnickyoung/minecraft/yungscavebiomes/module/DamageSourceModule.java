package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.DamageSourceAccessor;
import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceModule {
    public static final DamageSource PRICKLY_VINES = DamageSourceAccessor.createDamageSource("ycb_pricklyVines");
    public static final DamageSource ICICLE = ((DamageSourceAccessor) DamageSourceAccessor.createDamageSource("ycb_icicle")).invokeDamageHelmet();

    public static void init() {} // Initializes static fields when called
}
