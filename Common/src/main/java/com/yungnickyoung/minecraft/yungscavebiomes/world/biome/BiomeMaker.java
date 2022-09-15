package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

import com.yungnickyoung.minecraft.yungscavebiomes.module.PlacedFeatureModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Utilities for creating new biomes.
 */
public class BiomeMaker {
    public static Biome frostedCaves() {
        // Mob spawns
        MobSpawnSettings.Builder mobSettings = new MobSpawnSettings.Builder();
        mobSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        mobSettings.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityTypeModule.ICE_CUBE.get(), 10, 1, 1));

        /* Begin adding biome settings */
        BiomeGenerationSettings.Builder biomeSettings = new BiomeGenerationSettings.Builder();

        // Carvers
        biomeSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        biomeSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        biomeSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);

        // Local modifications
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeSettings);

        // Structures
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeSettings);

        // Ores
        addUndergroundVarietyNoGlowLichen(biomeSettings);
        BiomeDefaultFeatures.addDefaultOres(biomeSettings, false);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettings);

        // Springs
        biomeSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);

        // Top layer modifications
        BiomeDefaultFeatures.addSurfaceFreezing(biomeSettings);

        // Modded features
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.LARGE_ICICLE));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.TILTED_ICICLE));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SMALL_ICICLE));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICE_PATCH));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICE_PATCH_CEILING));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.FROST_LILY));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.WATER_SURFACE_ICE_FRAGMENT));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.WATER_SURFACE_ICE_FRAGMENT2));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICICLES));
        biomeSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, makeHolder(PlacedFeatureModule.ICE_SHEET_REPLACE));
        biomeSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, makeHolder(PlacedFeatureModule.ICE_SHEET_REPLACE_2));

        // Music
        Music music = Musics.createGameMusic(SoundModule.MUSIC_BIOME_ICE_CAVES.get());

        // Build biome
        return biome(Biome.Precipitation.SNOW, 0.8F, 0.4F, mobSettings, biomeSettings, music);
    }

    public static Biome marbleCaves() {
        // Mob spawns
        MobSpawnSettings.Builder mobSettings = new MobSpawnSettings.Builder();
        mobSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        mobSettings.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));

        /* Begin adding biome settings */
        BiomeGenerationSettings.Builder biomeSettings = new BiomeGenerationSettings.Builder();

        // Carvers
        biomeSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        biomeSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        biomeSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);

        // Local modifications
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeSettings);

        // Structures
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeSettings);

        // Ores (plus glow lichen)
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeSettings);
        BiomeDefaultFeatures.addDefaultOres(biomeSettings, false);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettings);

        // Springs
        biomeSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);

        // Vegetal decoration
        BiomeDefaultFeatures.addPlainGrass(biomeSettings);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettings);

        // Top layer modifications
        BiomeDefaultFeatures.addSurfaceFreezing(biomeSettings);

        // Modded features
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_CAVE_WATER_POOL));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_WATER_SPRING));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_PATCH));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.TRAVERTINE_PATCH));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.MARBLE_GLOW_LICHEN));

        // Music
//        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);

        // Build biome
        return biome(Biome.Precipitation.RAIN, 0.8F, 0.4F, mobSettings, biomeSettings, null);
    }

    public static Biome ancientCaves() {
        // Mob spawns
        MobSpawnSettings.Builder mobSettings = new MobSpawnSettings.Builder();
        mobSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));


        /* Begin adding biome settings */
        BiomeGenerationSettings.Builder biomeSettings = new BiomeGenerationSettings.Builder();

        // Carvers
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeSettings);

        // Local modifications
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeSettings);

        // Structures
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeSettings);

        // Ores (plus glow lichen)
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeSettings);
        BiomeDefaultFeatures.addDefaultOres(biomeSettings, false);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettings);

        // Springs
        BiomeDefaultFeatures.addDefaultSprings(biomeSettings);

        // Vegetal decoration
        BiomeDefaultFeatures.addPlainGrass(biomeSettings);
        BiomeDefaultFeatures.addPlainVegetation(biomeSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettings);

        // Top layer modifications
        BiomeDefaultFeatures.addSurfaceFreezing(biomeSettings);

        // Modded features
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_PATCH));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_PATCH2));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.CACTUS_PATCH));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.PRICKLY_PEACH_CACTUS_PATCH));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_GLOW_LICHEN));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.BRITTLE_SANDSTONE_REPLACE));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.DEAD_BUSH_SPREAD));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.PRICKLY_VINES));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.LAYERED_SANDSTONE_PILLAR));

        // Music
        Music music = Musics.createGameMusic(SoundModule.MUSIC_BIOME_DESERT_CAVES.get());

        // Build biome
        return biome(Biome.Precipitation.NONE, 0.8F, 0.4F, mobSettings, biomeSettings, music);
    }

    protected static int calculateSkyColor(float temperature) {
        float g = temperature / 3.0F;
        g = Mth.clamp(g, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - g * 0.05F, 0.5F + g * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation,
                               float temperature,
                               float downfall,
                               MobSpawnSettings.Builder mobSpawnSettingsBuilder,
                               BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder,
                               @Nullable Music music
    ) {
        return biome(precipitation, temperature, downfall, 4159204, 329011,
                mobSpawnSettingsBuilder, biomeGenerationSettingsBuilder, music);
    }

    private static Biome biome(Biome.Precipitation precipitation,
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
                .biomeCategory(Biome.BiomeCategory.UNDERGROUND)
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

    private static Holder<PlacedFeature> makeHolder(PlacedFeature placed) {
        Optional<ResourceKey<PlacedFeature>> optional = BuiltinRegistries.PLACED_FEATURE.getResourceKey(placed);
        ResourceKey<PlacedFeature> key = optional.get();
        return BuiltinRegistries.PLACED_FEATURE.getHolderOrThrow(key);
    }
}
