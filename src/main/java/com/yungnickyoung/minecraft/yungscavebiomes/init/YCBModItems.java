package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.effect.FrostMobEffect;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.PotionBrewingAccessor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class YCBModItems {
    public static final Item ICE_CUBE_SPAWN_EGG = new SpawnEggItem(YCBModEntities.ICE_CUBE, 10798332, 15002876, new FabricItemSettings().group(CreativeModeTab.TAB_MISC));
    public static final MobEffect FROZEN_EFFECT = new FrostMobEffect(200, 100, 600);

    public static final Potion FROST_POTION = new Potion(YungsCaveBiomes.MOD_ID + ".frost", new MobEffectInstance(FROZEN_EFFECT, 0, 0, false, true, false));
    public static final Potion STRONG_FROST_POTION = new Potion(YungsCaveBiomes.MOD_ID + ".strong_frost", new MobEffectInstance(FROZEN_EFFECT, 0, 1, false, true, false));

    public static void init() {
        // Items
        Registry.register(Registry.ITEM, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube_spawn_egg"), ICE_CUBE_SPAWN_EGG);

        // Mob effects
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(YungsCaveBiomes.MOD_ID, "frost"), FROZEN_EFFECT);

        // Potions
        Registry.register(Registry.POTION, new ResourceLocation(YungsCaveBiomes.MOD_ID, "frost"), FROST_POTION);
        Registry.register(Registry.POTION, new ResourceLocation(YungsCaveBiomes.MOD_ID, "strong_frost"), STRONG_FROST_POTION);
        PotionBrewingAccessor.callAddMix(Potions.AWKWARD, YCBModBlocks.FROST_LILY.asItem(), FROST_POTION);
        PotionBrewingAccessor.callAddMix(FROST_POTION, Items.FERMENTED_SPIDER_EYE, Potions.FIRE_RESISTANCE);
        PotionBrewingAccessor.callAddMix(Potions.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, FROST_POTION);
        PotionBrewingAccessor.callAddMix(FROST_POTION, Items.GLOWSTONE_DUST, STRONG_FROST_POTION);
    }
}
