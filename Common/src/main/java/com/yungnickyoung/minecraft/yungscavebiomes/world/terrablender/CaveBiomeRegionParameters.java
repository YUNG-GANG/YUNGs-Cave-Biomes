package com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender;

import net.minecraft.world.level.biome.Climate;

/**
 * Parameters used for creating TerraBlender region.
 * The actual creation of TerraBlender region is handled in loader-specific modules.
 */
public class CaveBiomeRegionParameters {
    ////////////////////////////// CLIMATE PARAMETERS //////////////////////////////
    // Most of these are duplicates of OverworldBiomeBuilder fields.

    private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

    // Temperature
    private static final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private static final Climate.Parameter frozenTemp = temperatures[0];
    private static final Climate.Parameter unfrozenTemp = Climate.Parameter.span(temperatures[1], temperatures[4]);

    // Humidity
    private static final Climate.Parameter[] humidities = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.35F),
            Climate.Parameter.span(-0.35F, -0.1F),
            Climate.Parameter.span(-0.1F, 0.1F),
            Climate.Parameter.span(0.1F, 0.3F),
            Climate.Parameter.span(0.3F, 1.0F)
    };

    // Continentalness
    private static final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
    private static final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
    private static final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
    private static final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
    private static final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
    private static final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
    private static final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);

    // Depth
    private static final Climate.Parameter undergroundDepth = Climate.Parameter.span(0.2F, 0.9F);

    // Dripstone modifications.
    // Dripstone caves are modified to only spawn in the unfrozen temperature range,
    // with the frozen temperature range being replaced by Frosted Caves.
    public static final Climate.ParameterPoint DRIPSTONE_OLD = new ParameterPointBuilder()
            .temperature(FULL_RANGE)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(0.8F, 1.0F))
            .erosion(FULL_RANGE)
            .depth(undergroundDepth)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();
    public static final Climate.ParameterPoint DRIPSTONE_NEW = new ParameterPointBuilder()
            .temperature(unfrozenTemp)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(0.8F, 1.0F))
            .erosion(FULL_RANGE)
            .depth(undergroundDepth)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();

    // New cave biomes
    public static final Climate.ParameterPoint FROSTED_CAVES = new ParameterPointBuilder()
            .temperature(frozenTemp)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(coastContinentalness, farInlandContinentalness))
            .erosion(FULL_RANGE)
            .depth(undergroundDepth)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();
    public static final Climate.ParameterPoint MARBLE_CAVES = new ParameterPointBuilder()
            .temperature(frozenTemp)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(deepOceanContinentalness, oceanContinentalness))
            .erosion(FULL_RANGE)
            .depth(undergroundDepth)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();
    public static final Climate.ParameterPoint LOST_CAVES = new ParameterPointBuilder()
            .temperature(temperatures[4])
            .humidity(Climate.Parameter.span(humidities[0], humidities[3]))
            .continentalness(Climate.Parameter.span(inlandContinentalness, farInlandContinentalness))
            .erosion(FULL_RANGE)
            .depth(undergroundDepth)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();

    /**
     * Convenience builder for creating new {@link Climate.ParameterPoint}s.
     */
    private static class ParameterPointBuilder {
        Climate.Parameter temperature, humidity, continentalness, erosion, depth, weirdness;
        float offset;

        public ParameterPointBuilder temperature(Climate.Parameter temperature) {
            this.temperature = temperature;
            return this;
        }

        public ParameterPointBuilder humidity(Climate.Parameter humidity) {
            this.humidity = humidity;
            return this;
        }

        public ParameterPointBuilder continentalness(Climate.Parameter continentalness) {
            this.continentalness = continentalness;
            return this;
        }

        public ParameterPointBuilder erosion(Climate.Parameter erosion) {
            this.erosion = erosion;
            return this;
        }

        public ParameterPointBuilder depth(Climate.Parameter depth) {
            this.depth = depth;
            return this;
        }

        public ParameterPointBuilder weirdness(Climate.Parameter weirdness) {
            this.weirdness = weirdness;
            return this;
        }

        public ParameterPointBuilder offset(float offset) {
            this.offset = offset;
            return this;
        }

        public Climate.ParameterPoint build() {
            return Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
        }
    }
}
