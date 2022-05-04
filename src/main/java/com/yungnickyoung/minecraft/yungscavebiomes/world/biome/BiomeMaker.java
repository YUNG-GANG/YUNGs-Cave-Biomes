package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModPlacedFeatures;
import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModSounds;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
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
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome iceCaves() {
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(mobSpawnSettingsBuilder);
        BiomeGenerationSettings.Builder biomeSettingsBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.LARGE_ICICLE);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.TILTED_ICICLE);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.SMALL_ICICLE);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.ICE_PATCH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.ICE_PATCH_CEILING);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.FROST_LILY);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.WATER_SURFACE_ICE_FRAGMENT);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.WATER_SURFACE_ICE_FRAGMENT2);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.ICICLES);

        Music music = Musics.createGameMusic(YCBModSounds.MUSIC_BIOME_ICE_CAVES);
        return biome(Biome.Precipitation.SNOW, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F,
                mobSpawnSettingsBuilder, biomeSettingsBuilder, music);
    }

    public static Biome marbleCaves() {
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(mobSpawnSettingsBuilder);
        BiomeGenerationSettings.Builder biomeSettingsBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_WATER_POOL);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_WATER_SPRING);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_PATCH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.TRAVERTINE_PATCH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_GLOW_LICHEN);

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F,
                mobSpawnSettingsBuilder, biomeSettingsBuilder, music);
    }

    public static Biome ancientCaves() {
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(mobSpawnSettingsBuilder);
        BiomeGenerationSettings.Builder biomeSettingsBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeSettingsBuilder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettingsBuilder);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettingsBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettingsBuilder);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.SANDSTONE_PATCH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.SANDSTONE_PATCH2);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.CACTUS_PATCH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.PRICKLY_PEAR_CACTUS_PATCH);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.SANDSTONE_GLOW_LICHEN);

        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.BRITTLE_SANDSTONE_REPLACE);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.DEAD_BUSH_SPREAD);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.PRICKLY_VINES);
        biomeSettingsBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.DISK_ROCK);

        Music music = Musics.createGameMusic(YCBModSounds.MUSIC_BIOME_DESERT_CAVES);
        return biome(Biome.Precipitation.NONE, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F,
                mobSpawnSettingsBuilder, biomeSettingsBuilder, music);
    }
}
