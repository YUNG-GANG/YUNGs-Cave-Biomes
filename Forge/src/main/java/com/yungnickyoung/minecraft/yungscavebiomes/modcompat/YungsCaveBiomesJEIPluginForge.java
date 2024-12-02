package com.yungnickyoung.minecraft.yungscavebiomes.modcompat;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.PotionModule;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class YungsCaveBiomesJEIPluginForge implements IModPlugin {
    private static final ResourceLocation PLUGIN_UID = YungsCaveBiomesCommon.id("jei_plugin");

    public YungsCaveBiomesJEIPluginForge() {
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<IJeiBrewingRecipe> recipes = List.of(
                // Frost Potion
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(BlockModule.FROST_LILY.get().asItem().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), PotionModule.FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("frost_potion_from_frost_lily")),
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.FERMENTED_SPIDER_EYE.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), PotionModule.FROST_POTION.get()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE),
                        YungsCaveBiomesCommon.id("fire_resistance_potion_from_frost_potion")),
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.FERMENTED_SPIDER_EYE.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), PotionModule.FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("frost_potion_from_fire_resistance_potion")),

                // Strong Frost Potion
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.GLOWSTONE_DUST.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), PotionModule.FROST_POTION.get()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), PotionModule.STRONG_FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("strong_frost_potion_from_frost_potion")),

                // Frost Splash Potion
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(BlockModule.FROST_LILY.get().asItem().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD),
                        PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionModule.FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("frost_splash_potion_from_awkward_splash_potion")),
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.FERMENTED_SPIDER_EYE.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.FIRE_RESISTANCE),
                        PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionModule.FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("frost_splash_potion_from_fire_resistance_splash_potion")),

                // Strong Frost Splash Potion
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.GLOWSTONE_DUST.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionModule.FROST_POTION.get()),
                        PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionModule.STRONG_FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("strong_frost_splash_potion_from_frost_splash_potion")),

                // Lingering Frost Potion
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(BlockModule.FROST_LILY.get().asItem().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD),
                        PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionModule.FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("frost_lingering_potion_from_awkward_lingering_potion")),
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.FERMENTED_SPIDER_EYE.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.FIRE_RESISTANCE),
                        PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionModule.FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("frost_lingering_potion_from_fire_resistance_lingering_potion")),

                // Strong Frost Lingering Potion
                registration.getVanillaRecipeFactory().createBrewingRecipe(
                        List.of(Items.GLOWSTONE_DUST.getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionModule.FROST_POTION.get()),
                        PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionModule.STRONG_FROST_POTION.get()),
                        YungsCaveBiomesCommon.id("strong_frost_lingering_potion_from_frost_lingering_potion"))
        );
        registration.addRecipes(RecipeTypes.BREWING, recipes);
    }
}
