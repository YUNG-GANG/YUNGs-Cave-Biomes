package com.yungnickyoung.minecraft.yungscavebiomes.world.biome;

import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.PlacedFeatureModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
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

public class LostCavesBiomeMaker extends BiomeMaker {
//    public Biome make() {
//        // Mob spawns
//        MobSpawnSettings.Builder mobSettings = new MobSpawnSettings.Builder();
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 100, 4, 4));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
//        mobSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
//        mobSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
//        mobSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityTypeModule.SAND_SNAPPER.get(), 50, 1, 1));
//
//        /* Begin adding biome settings */
//        BiomeGenerationSettings.Builder biomeSettings = new BiomeGenerationSettings.Builder();
//
//        // Carvers
//        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeSettings);
//
//        // Local modifications
//        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeSettings);
//
//        // Structures
//        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeSettings);
//
//        // Ores (plus glow lichen)
//        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeSettings);
//        BiomeDefaultFeatures.addDefaultOres(biomeSettings, false);
//        BiomeDefaultFeatures.addDefaultSoftDisks(biomeSettings);
//
//        // Springs
//        BiomeDefaultFeatures.addDefaultSprings(biomeSettings);
//
//        // Vegetal decoration
//        BiomeDefaultFeatures.addPlainGrass(biomeSettings);
//        BiomeDefaultFeatures.addPlainVegetation(biomeSettings);
//        BiomeDefaultFeatures.addDefaultMushrooms(biomeSettings);
//        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeSettings);
//
//        // Top layer modifications
//        BiomeDefaultFeatures.addSurfaceFreezing(biomeSettings);
//
//        // Modded features
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.LOST_CAVES_SURFACE_REPLACE));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.LOST_CAVES_SURFACE_REPLACE_2));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.BRITTLE_SANDSTONE_CEILING_PATCH));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.SANDSTONE_GLOW_LICHEN));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.CACTUS_PATCH));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.PRICKLY_PEACH_CACTUS_PATCH));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.DEAD_BUSH_SPREAD));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.PRICKLY_VINES));
//        biomeSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, makeHolder(PlacedFeatureModule.LAYERED_SANDSTONE_PILLAR));
//
//        // Music & sounds
//        Music music = Musics.createGameMusic(SoundModule.MUSIC_BIOME_LOST_CAVES.get());
//
//        // Build biome
//        return new Biome.BiomeBuilder()
//                .precipitation(Biome.Precipitation.NONE)
//                .biomeCategory(Biome.BiomeCategory.UNDERGROUND)
//                .temperature(0.8F)
//                .downfall(0.4F)
//                .specialEffects(new BiomeSpecialEffects.Builder()
//                        .waterColor(0x332c0a)
//                        .waterFogColor(0x1c1606)
//                        .fogColor(0xc88027)
//                        .skyColor(calculateSkyColor(0.8F))
////                        .ambientLoopSound(loopSound)
//                        .backgroundMusic(music)
//                        .ambientParticle(new AmbientParticleSettings((ParticleOptions) ParticleTypeModule.LOST_CAVES_AMBIENT.get(), 0.0015f))
//                        .build())
//                .mobSpawnSettings(mobSettings.build())
//                .generationSettings(biomeSettings.build())
//                .build();
//    }
}
