package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.PlacedFeatureModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class MarbleCavesBiomeMaker extends BiomeMaker {
    public Biome make() {
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
}
