package com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender;

import net.minecraft.world.level.biome.Climate;

/**
 * Parameters used for creating TerraBlender region.
 * The actual creation of TerraBlender region is handled in loader-dependent modules.
 */
public class CaveBiomeRegionParameters {
    // Duplicate of OverworldBoimeBuilder fields
    private static final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private static final Climate.Parameter fullRange = Climate.Parameter.span(-1.0F, 1.0F);
    private static final Climate.Parameter frozenRange = temperatures[0];
    private static final Climate.Parameter unfrozenRange = Climate.Parameter.span(temperatures[1], temperatures[4]);

    // Dripstone modifications
    public static final Climate.ParameterPoint DRIPSTONE_OLD = Climate.parameters(fullRange, fullRange, Climate.Parameter.span(0.8F, 1.0F), fullRange, Climate.Parameter.span(0.2F, 0.9F), fullRange, 0.0F);
    public static final Climate.ParameterPoint DRIPSTONE_NEW = Climate.parameters(unfrozenRange, fullRange, Climate.Parameter.span(0.8F, 1.0F), fullRange, Climate.Parameter.span(0.2F, 0.9F), fullRange, 0.0F);

    // Cave biomes
    public static final Climate.ParameterPoint FROSTED_CAVES = Climate.parameters(
            frozenRange, // temperature
            fullRange, // humidity
//                    Climate.Parameter.span(0.6F, 1.0F), // continentalness
            Climate.Parameter.span(0.1F, 1.0F), // continentalness
            fullRange, // erosion
            Climate.Parameter.span(0.2F, 0.9F), // depth
            fullRange, // weirdness
            0.0F // offset
    );
    public static final Climate.ParameterPoint MARBLE_CAVES = Climate.parameters(
            frozenRange, // temperature
            fullRange, // humidity
            Climate.Parameter.span(-1.05F, -0.19F), // continentalness
            fullRange, // erosion
            Climate.Parameter.span(0.2F, 0.9F), // depth
            fullRange, // weirdness
            0.0F // offset
    );
    public static final Climate.ParameterPoint LOST_CAVES = Climate.parameters(
            temperatures[4], // temperature
            fullRange, // humidity
            Climate.Parameter.span(0.2F, 0.7F), // continentalness
            Climate.Parameter.span(0.2F, 1.0F), // erosion
            Climate.Parameter.span(0.2F, 0.9F), // depth
            fullRange, // weirdness
            0.0F // offset
    );
}
