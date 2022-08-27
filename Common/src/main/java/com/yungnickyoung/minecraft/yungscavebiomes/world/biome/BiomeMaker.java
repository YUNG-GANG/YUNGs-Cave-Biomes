package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

import com.yungnickyoung.minecraft.yungscavebiomes.module.PlacedFeatureModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities for creating new biomes.
 */
public class BiomeMaker {
    protected static int calculateSkyColor(float temperature) {
        float g = temperature / 3.0F;
        g = Mth.clamp(g, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - g * 0.05F, 0.5F + g * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation,
                               Biome.BiomeCategory biomeCategory,
                               float temperature,
                               float downfall,
                               MobSpawnSettings.Builder mobSpawnSettingsBuilder,
                               BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder,
                               @Nullable Music music
    ) {
        return biome(precipitation, biomeCategory, temperature, downfall, 4159204, 329011,
                mobSpawnSettingsBuilder, biomeGenerationSettingsBuilder, music);
    }

    private static Biome biome(Biome.Precipitation precipitation,
                               Biome.BiomeCategory biomeCategory,
                               float temperature,
                               float downfall,
                               int waterColor,
                               int waterFogColor,
                               MobSpawnSettings.Builder mobSpawnSettingsBuilder,
                               BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder,
                               @Nullable Music music
    ) {
        return new Biome.BiomeBuilder()
                .precipitation(precipitation)
                .biomeCategory(biomeCategory)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(12638463)
                        .skyColor(calculateSkyColor(temperature))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(music).build())
                .mobSpawnSettings(mobSpawnSettingsBuilder.build())
                .generationSettings(biomeGenerationSettingsBuilder.build())
                .build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    private static void addUndergroundVarietyNoGlowLichen(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIRT);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRAVEL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF);
    }

    public static Biome iceCaves() {
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(mobSpawnSettingsBuilder);
        BiomeGenerationSettings.Builder biomeSettingsBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeSettingsBuilder);
        addUndergroundVarietyNoGlowLichen(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.LARGE_ICICLE));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.TILTED_ICICLE));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SMALL_ICICLE));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICE_PATCH));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICE_PATCH_CEILING));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.FROST_LILY));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.WATER_SURFACE_ICE_FRAGMENT));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.WATER_SURFACE_ICE_FRAGMENT2));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICICLES));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, makeHolder(PlacedFeatureModule.ICE_SHEET_REPLACE));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, makeHolder(PlacedFeatureModule.ICE_SHEET_REPLACE_2));

        Music music = Musics.createGameMusic(SoundModule.MUSIC_BIOME_ICE_CAVES);
        return biome(Biome.Precipitation.SNOW, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F,
                mobSpawnSettingsBuilder, biomeSettingsBuilder, music);
    }

    public static Biome marbleCaves() {
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(mobSpawnSettingsBuilder);
        BiomeGenerationSettings.Builder biomeSettingsBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_WATER_POOL));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_WATER_SPRING));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_PATCH));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.TRAVERTINE_PATCH));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_GLOW_LICHEN));

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F,
                mobSpawnSettingsBuilder, biomeSettingsBuilder, music);
    }

    public static Biome ancientCaves() {
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(mobSpawnSettingsBuilder);
        BiomeGenerationSettings.Builder biomeSettingsBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_PATCH));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_PATCH2));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.CACTUS_PATCH));

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.PRICKLY_PEAR_CACTUS_PATCH));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_GLOW_LICHEN));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.BRITTLE_SANDSTONE_REPLACE));

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.DEAD_BUSH_SPREAD));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.PRICKLY_VINES));
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.DISK_ROCK));

        Music music = Musics.createGameMusic(SoundModule.MUSIC_BIOME_DESERT_CAVES);
        return biome(Biome.Precipitation.NONE, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F,
                mobSpawnSettingsBuilder, biomeSettingsBuilder, music);
    }

    @NotNull
    private static Holder<PlacedFeature> makeHolder(PlacedFeature placed) {
        return BuiltinRegistries.PLACED_FEATURE.getHolderOrThrow(BuiltinRegistries.PLACED_FEATURE.getResourceKey(placed).get());
    }
}
