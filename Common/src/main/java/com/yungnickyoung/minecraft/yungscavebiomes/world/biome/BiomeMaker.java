package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

//import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterPlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

/**
 * Utilities for creating new biomes.
 */
public abstract class BiomeMaker {
//    public abstract Biome make();
//
//    protected static int calculateSkyColor(float temperature) {
//        float g = temperature / 3.0F;
//        g = Mth.clamp(g, -1.0F, 1.0F);
//        return Mth.hsvToRgb(0.62222224F - g * 0.05F, 0.5F + g * 0.1F, 1.0F);
//    }
//
//    protected static Biome biome(boolean hasPrecipitation,
//                               float temperature,
//                               float downfall,
//                               MobSpawnSettings.Builder mobSpawnSettingsBuilder,
//                               BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder,
//                               @Nullable Music music
//    ) {
//        return biome(hasPrecipitation, temperature, downfall, 0x3F76E4, 0x050533, 0xC0D8FF,
//                mobSpawnSettingsBuilder, biomeGenerationSettingsBuilder, music);
//    }
//
//    protected static Biome biome(boolean hasPrecipitation,
//                               float temperature,
//                               float downfall,
//                               int waterColor,
//                               int waterFogColor,
//                               int fogColor,
//                               MobSpawnSettings.Builder mobSpawnSettingsBuilder,
//                               BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder,
//                               @Nullable Music music
//    ) {
//        return new Biome.BiomeBuilder()
//                .hasPrecipitation(hasPrecipitation)
//                .temperature(temperature)
//                .downfall(downfall)
//                .specialEffects(new BiomeSpecialEffects.Builder()
//                        .waterColor(waterColor)
//                        .waterFogColor(waterFogColor)
//                        .fogColor(fogColor)
//                        .skyColor(calculateSkyColor(temperature))
//                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
//                        .backgroundMusic(music)
//                        .build())
//                .mobSpawnSettings(mobSpawnSettingsBuilder.build())
//                .generationSettings(biomeGenerationSettingsBuilder.build())
//                .build();
//    }
//
//    protected static void addUndergroundVarietyNoGlowLichen(BiomeGenerationSettings.Builder builder) {
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIRT);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRAVEL);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_UPPER);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_UPPER);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_UPPER);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER);
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF);
//    }
//
//    protected static Holder<PlacedFeature> makeHolder(AutoRegisterPlacedFeature autoRegisterPlacedFeature) {
//        return autoRegisterPlacedFeature.holder();
//    }
}
