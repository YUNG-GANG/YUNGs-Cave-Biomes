package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.effect.FrostMobEffect;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class MobEffectModule {
    public static final MobEffect FROZEN_EFFECT = new FrostMobEffect(200, 100, 600);

    public static void init() {
        Services.REGISTRY.registerMobEffect(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "frost"), FROZEN_EFFECT);
    }
}
