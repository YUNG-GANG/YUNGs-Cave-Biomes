package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.PotionBrewingAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class PotionModule {
    public static final Potion FROST_POTION = new Potion(YungsCaveBiomesCommon.MOD_ID + ".frost",
            new MobEffectInstance(MobEffectModule.FROZEN_EFFECT, 0, 0, false, true, false));
    public static final Potion STRONG_FROST_POTION = new Potion(YungsCaveBiomesCommon.MOD_ID + ".strong_frost",
            new MobEffectInstance(MobEffectModule.FROZEN_EFFECT, 0, 1, false, true, false));

    public static void init() {
        Services.REGISTRY.registerPotion(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "frost"), FROST_POTION);
        Services.REGISTRY.registerPotion(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "strong_frost"), STRONG_FROST_POTION);
        PotionBrewingAccessor.callAddMix(Potions.AWKWARD, BlockModule.FROST_LILY.get().asItem(), FROST_POTION);
        PotionBrewingAccessor.callAddMix(FROST_POTION, Items.FERMENTED_SPIDER_EYE, Potions.FIRE_RESISTANCE);
        PotionBrewingAccessor.callAddMix(Potions.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, FROST_POTION);
        PotionBrewingAccessor.callAddMix(FROST_POTION, Items.GLOWSTONE_DUST, STRONG_FROST_POTION);
    }
}
