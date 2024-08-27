package com.yungnickyoung.minecraft.yungscavebiomes.world;

import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender.CaveBiomeRegionParameters;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class CaveBiomeRegion extends Region {
    public CaveBiomeRegion(ResourceLocation name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        // We also want vanilla biomes in our TerraBlender region
        addModifiedVanillaOverworldBiomes(mapper, b -> {
            // Modify dripstone caves to only spawn in the unfrozen temperature range.
            // The frozen range will be occupied by frosted caves
            b.replaceParameter(CaveBiomeRegionParameters.DRIPSTONE_OLD, CaveBiomeRegionParameters.DRIPSTONE_NEW);
        });

        this.addBiome(mapper, CaveBiomeRegionParameters.FROSTED_CAVES, BiomeModule.FROSTED_CAVES.getResourceKey());
        this.addBiome(mapper, CaveBiomeRegionParameters.LOST_CAVES, BiomeModule.LOST_CAVES.getResourceKey());
        if (YungsCaveBiomesCommon.MARBLE_CAVES_ENABLED) {
            this.addBiome(mapper, CaveBiomeRegionParameters.MARBLE_CAVES, BiomeModule.MARBLE_CAVES.getResourceKey());
        }
    }
}
