package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

import com.yungnickyoung.minecraft.yungscavebiomes.module.PlacedFeatureModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FrostedCavesBiomeMaker extends BiomeMaker {
    public Biome make() {
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
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.WATER_SURFACE_ICE_FRAGMENT_2));
        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.ICICLES));
        biomeSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, makeHolder(PlacedFeatureModule.ICE_SHEET_REPLACE));
        biomeSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, makeHolder(PlacedFeatureModule.ICE_SHEET_REPLACE_2));

        // Music
        Music music = Musics.createGameMusic(SoundModule.MUSIC_BIOME_ICE_CAVES.get());

        // Build biome
        return biome(Biome.Precipitation.SNOW, 0.8F, 0.4F, mobSettings, biomeSettings, music);
    }
}
