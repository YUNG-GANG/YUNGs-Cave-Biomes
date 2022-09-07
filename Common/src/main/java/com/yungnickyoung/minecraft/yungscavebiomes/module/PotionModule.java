package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterPotion;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class PotionModule {
    @AutoRegister("frost")
    public static final AutoRegisterPotion FROST_POTION = AutoRegisterPotion
            .mobEffect(new MobEffectInstance(MobEffectModule.FROZEN_EFFECT.get(), 0, 0, false, true, false));

    @AutoRegister("strong_frost")
    public static final AutoRegisterPotion STRONG_FROST_POTION = AutoRegisterPotion
            .mobEffect(new MobEffectInstance(MobEffectModule.FROZEN_EFFECT.get(), 0, 1, false, true, false));

    /**
     * Methods with the AutoRegister annotations will be executed after registration.
     * For Fabric, this means the method is executed during mod initialization as normal.
     * For Forge, the method is queued to execute in common setup.
     *
     * Any methods used with the AutoRegister annotation must be static and take no arguments.
     * Note that the annotation value is ignored.
     */
    @AutoRegister("_ignored")
    private static void init() {
        AutoRegisterUtils.registerBrewingRecipe(() -> Potions.AWKWARD, () -> BlockModule.FROST_LILY.get().asItem(), FROST_POTION.getSupplier());
        AutoRegisterUtils.registerBrewingRecipe(FROST_POTION.getSupplier(), () -> Items.FERMENTED_SPIDER_EYE, () -> Potions.FIRE_RESISTANCE);
        AutoRegisterUtils.registerBrewingRecipe(() -> Potions.FIRE_RESISTANCE, () -> Items.FERMENTED_SPIDER_EYE, FROST_POTION.getSupplier());
        AutoRegisterUtils.registerBrewingRecipe(FROST_POTION.getSupplier(), () -> Items.GLOWSTONE_DUST, STRONG_FROST_POTION.getSupplier());
    }
}
