package com.yungnickyoung.minecraft.yungscavebiomes.world;

import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class CaveBiomeRegion extends Region {
    // Duplicate of OverworldBoimeBuilder params
    private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    private final Climate.Parameter FROZEN_RANGE = this.temperatures[0];
    private final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(this.temperatures[1], this.temperatures[4]);

    public CaveBiomeRegion(ResourceLocation name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        // We also want vanilla biomes in our TerraBlender region
        addModifiedVanillaOverworldBiomes(mapper, b -> {
            // Modify dripstone caves to only spawn in the unfrozen temperature range.
            // The frozen range will be occupied by frosted caves
            b.replaceParameter(
                    Climate.parameters(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, Climate.Parameter.span(0.2F, 0.9F), this.FULL_RANGE, 0.0F),
                    Climate.parameters(this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, Climate.Parameter.span(0.2F, 0.9F), this.FULL_RANGE, 0.0F));
        });

        if (YungsCaveBiomesCommon.FROSTED_CAVES_ENABLED) {
            this.addBiome(mapper,
                    this.FROZEN_RANGE, // temperature
                    this.FULL_RANGE, // humidity
//                    Climate.Parameter.span(0.6F, 1.0F), // continentalness
                    Climate.Parameter.span(0.1F, 1.0F), // continentalness
                    this.FULL_RANGE, // erosion
                    this.FULL_RANGE, // weirdness
                    Climate.Parameter.span(0.2F, 0.9F), // depth
                    0.0F, // offset
                    BiomeModule.FROSTED_CAVES.getResourceKey());
        }
        if (YungsCaveBiomesCommon.MARBLE_CAVES_ENABLED) {
            this.addBiome(mapper, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(-1.05F, -0.19F), this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.2F, 0.9F), 0.0F, BiomeModule.MARBLE_CAVES.getResourceKey());
        }
        if (YungsCaveBiomesCommon.ANCIENT_CAVES_ENABLED) {
            this.addBiome(mapper, this.temperatures[4], this.FULL_RANGE, Climate.Parameter.span(0.2F, 0.7F), Climate.Parameter.span(0.2F, 1.0F), this.FULL_RANGE, Climate.Parameter.span(0.2F, 0.9F), 0.0F, BiomeModule.ANCIENT_CAVES.getResourceKey());
        }
    }
}
