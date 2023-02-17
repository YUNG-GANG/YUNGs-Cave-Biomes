package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.criteria.BreakEnchantedIceTrigger;
import com.yungnickyoung.minecraft.yungscavebiomes.criteria.EatPricklyPeachTrigger;
import com.yungnickyoung.minecraft.yungscavebiomes.criteria.InteractEmptyPricklyCactusTrigger;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class CriteriaModule {
    @AutoRegister("interact_empty_prickly_cactus")
    public static final InteractEmptyPricklyCactusTrigger INTERACT_EMPTY_PRICKLY_CACTUS = new InteractEmptyPricklyCactusTrigger();

    @AutoRegister("eat_prickly_peach")
    public static final EatPricklyPeachTrigger EAT_PRICKLY_PEACH = new EatPricklyPeachTrigger();

    @AutoRegister("break_enchanted_ice")
    public static final BreakEnchantedIceTrigger BREAK_ENCHANTED_ICE = new BreakEnchantedIceTrigger();
}