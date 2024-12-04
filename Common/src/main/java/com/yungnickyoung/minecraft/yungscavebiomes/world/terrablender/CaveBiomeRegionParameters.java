package com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.level.biome.Climate;

/**
 * Parameters used for creating TerraBlender region.
 * The actual creation of TerraBlender region is handled in loader-specific modules.
 */
public class CaveBiomeRegionParameters {
    private static final Climate.Parameter FULL_RANGE = span(-1.0F, 1.0F);

    // Dripstone modifications.
    // Dripstone caves are modified to only spawn in the unfrozen temperature range,
    // with the frozen temperature range being replaced by Frosted Caves.
    public static final Climate.ParameterPoint DRIPSTONE_OLD = new ParameterPointBuilder()
            .temperature(FULL_RANGE)
            .humidity(FULL_RANGE)
            .continentalness(span(0.8F, 1.0F))
            .erosion(FULL_RANGE)
            .depth(span(0.2F, 0.9F))
            .weirdness(FULL_RANGE)
            .offset(0.0F)
            .build();
    public static final Climate.ParameterPoint DRIPSTONE_NEW = new ParameterPointBuilder()
            .temperature(span(
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin,
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax))
            .humidity(span(
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMin,
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMax))
            .continentalness(span(
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin,
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax))
            .erosion(span(
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMin,
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMax))
            .depth(span(
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMin,
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMax))
            .weirdness(span(
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin,
                    YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax))
            .offset(YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.offset)
            .build();

    // New cave biomes
    public static final Climate.ParameterPoint FROSTED_CAVES = new ParameterPointBuilder()
            .temperature(span(
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMin,
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMax))
            .humidity(span(
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMin,
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMax))
            .continentalness(span(
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMin,
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMax))
            .erosion(span(
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMin,
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMax))
            .depth(span(
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMin,
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMax))
            .weirdness(span(
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMin,
                    YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMax))
            .offset(YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.offset)
            .build();

    public static final Climate.ParameterPoint LOST_CAVES = new ParameterPointBuilder()
            .temperature(span(
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMin,
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMax))
            .humidity(span(
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMin,
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMax))
            .continentalness(span(
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMin,
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMax))
            .erosion(span(
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMin,
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMax))
            .depth(span(
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMin,
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMax))
            .weirdness(span(
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMin,
                    YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMax))
            .offset(YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.offset)
            .build();

    private static Climate.Parameter span(float min, float max) {
        return Climate.Parameter.span(min, max);
    }

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
