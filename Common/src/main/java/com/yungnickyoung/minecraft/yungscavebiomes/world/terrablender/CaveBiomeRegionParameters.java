package com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender;

import net.minecraft.world.level.biome.Climate;

/**
 * Parameters used for creating TerraBlender region.
 * The actual creation of TerraBlender region is handled in loader-dependent modules.
 */
public class CaveBiomeRegionParameters {
    // Temperature parameters.
    // Duplicate of OverworldBiomeBuilder fields.
    private static final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    private static final Climate.Parameter FROZEN_TEMP_RANGE = temperatures[0];
    private static final Climate.Parameter UNFROZEN_TEMP_RANGE = Climate.Parameter.span(temperatures[1], temperatures[4]);
    private static final Climate.Parameter UNDERGROUND_DEPTH_RANGE = Climate.Parameter.span(0.2F, 0.9F);

    // Dripstone modifications
    public static final Climate.ParameterPoint DRIPSTONE_OLD = new ParameterPointBuilder()
            .temperature(FULL_RANGE)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(0.8F, 1.0F))
            .erosion(FULL_RANGE)
            .depth(UNDERGROUND_DEPTH_RANGE)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();

    public static final Climate.ParameterPoint DRIPSTONE_NEW = new ParameterPointBuilder()
            .temperature(UNFROZEN_TEMP_RANGE)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(0.8F, 1.0F))
            .erosion(FULL_RANGE)
            .depth(UNDERGROUND_DEPTH_RANGE)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();

    // Cave biomes
    public static final Climate.ParameterPoint FROSTED_CAVES = new ParameterPointBuilder()
            .temperature(FROZEN_TEMP_RANGE)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(0.1F, 1.0F))
            .erosion(FULL_RANGE)
            .depth(UNDERGROUND_DEPTH_RANGE)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();

    public static final Climate.ParameterPoint MARBLE_CAVES = new ParameterPointBuilder()
            .temperature(FROZEN_TEMP_RANGE)
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(-1.05F, -0.19F))
            .erosion(FULL_RANGE)
            .depth(UNDERGROUND_DEPTH_RANGE)
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();

    public static final Climate.ParameterPoint LOST_CAVES = new ParameterPointBuilder()
            .temperature(temperatures[4])
            .humidity(FULL_RANGE)
            .continentalness(Climate.Parameter.span(0.2F, 0.7F))
            .erosion(Climate.Parameter.span(0.2F, 1.0F))
            .depth(UNDERGROUND_DEPTH_RANGE)
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
