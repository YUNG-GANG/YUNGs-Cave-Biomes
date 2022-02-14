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

public class BiomeMaker {
    protected static int calculateSkyColor(float f) {
        float g = f / 3.0F;
        g = Mth.clamp(g, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - g * 0.05F, 0.5F + g * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory biomeCategory, float f, float g, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, @Nullable Music music) {
        return biome(precipitation, biomeCategory, f, g, 4159204, 329011, builder, builder2, music);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory biomeCategory, float f, float g, int i, int j, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).biomeCategory(biomeCategory).temperature(f).downfall(g).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(i).waterFogColor(j).fogColor(12638463).skyColor(calculateSkyColor(f)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(builder.build()).generationSettings(builder2.build()).build();
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
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(builder2);
        BiomeDefaultFeatures.addPlainGrass(builder2);
        BiomeDefaultFeatures.addDefaultOres(builder2, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder2);
        BiomeDefaultFeatures.addPlainVegetation(builder2);
        BiomeDefaultFeatures.addDefaultMushrooms(builder2);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder2);

        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.LARGE_ICICLE);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.TILTED_ICICLE);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.SMALL_ICICLE);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.ICE_PATCH);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.ICE_PATCH_CEILING);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.ICICLES);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.FROST_LILY);

        Music music = Musics.createGameMusic(YCBModSounds.MUSIC_BIOME_ICE_CAVES);
        return biome(Biome.Precipitation.SNOW, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F, builder, builder2, music);
    }

    public static Biome marbleCaves() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(builder2);
        BiomeDefaultFeatures.addPlainGrass(builder2);
        BiomeDefaultFeatures.addDefaultOres(builder2, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder2);
        BiomeDefaultFeatures.addPlainVegetation(builder2);
        BiomeDefaultFeatures.addDefaultMushrooms(builder2);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder2);

        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_WATER_POOL);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_WATER_SPRING);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_PATCH);
        builder2.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, YCBModPlacedFeatures.MARBLE_PATCH_CEILING);

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.UNDERGROUND, 0.8F, 0.4F, builder, builder2, music);
    }
}
