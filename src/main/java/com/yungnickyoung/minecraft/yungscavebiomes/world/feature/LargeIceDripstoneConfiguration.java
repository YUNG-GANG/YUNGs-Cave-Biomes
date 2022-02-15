package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class LargeIceDripstoneConfiguration implements FeatureConfiguration {
    public static final Codec<LargeIceDripstoneConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            (Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range")).orElse(30).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.floorToCeilingSearchRange),
            (IntProvider.codec(1, 60).fieldOf("column_radius")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.columnRadius),
            (FloatProvider.codec(0.0f, 20.0f).fieldOf("height_scale")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.heightScale),
            (Codec.floatRange(0.1f, 1.0f).fieldOf("max_column_radius_to_cave_height_ratio")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.maxColumnRadiusToCaveHeightRatio),
            (FloatProvider.codec(0.1f, 10.0f).fieldOf("stalactite_bluntness")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.stalactiteBluntness),
            (FloatProvider.codec(0.1f, 10.0f).fieldOf("stalagmite_bluntness")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.stalagmiteBluntness),
            (FloatProvider.codec(0.0f, 2.0f).fieldOf("wind_speed")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.windSpeed),
            (Codec.intRange(0, 100).fieldOf("min_radius_for_wind")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.minRadiusForWind),
            (Codec.floatRange(0.0f, 5.0f).fieldOf("min_bluntness_for_wind")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.minBluntnessForWind),
            (FloatProvider.codec(0.0f, 6.2832f).fieldOf("angle")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.angle),
            (Codec.floatRange(0.0f, 1.0f).fieldOf("rareIceChance")).forGetter(largeDripstoneConfiguration -> largeDripstoneConfiguration.rareIceChance)
    ).apply(instance, LargeIceDripstoneConfiguration::new));

    public final int floorToCeilingSearchRange;
    public final IntProvider columnRadius;
    public final FloatProvider heightScale;
    public final float maxColumnRadiusToCaveHeightRatio;
    public final FloatProvider stalactiteBluntness;
    public final FloatProvider stalagmiteBluntness;
    public final FloatProvider windSpeed;
    public final int minRadiusForWind;
    public final float minBluntnessForWind;

    /** Range of possible angles for wind offset, in radians. Should be between 0 and 2pi inclusive. */
    public final FloatProvider angle;

    /** Chance of a large ice dripstone containing rare ice. */
    public final float rareIceChance;

    public LargeIceDripstoneConfiguration(int floorToCeilingSearchRange,
                                          IntProvider columnRadius,
                                          FloatProvider heightScale,
                                          float maxColumnRadiusToCaveHeightRatio,
                                          FloatProvider stalactiteBluntess,
                                          FloatProvider stalagmiteBluntness,
                                          FloatProvider windSpeed,
                                          int minRadiusForWind,
                                          float minBluntnessForWind,
                                          FloatProvider angle,
                                          float rareIceChance
    ) {
        this.floorToCeilingSearchRange = floorToCeilingSearchRange;
        this.columnRadius = columnRadius;
        this.heightScale = heightScale;
        this.maxColumnRadiusToCaveHeightRatio = maxColumnRadiusToCaveHeightRatio;
        this.stalactiteBluntness = stalactiteBluntess;
        this.stalagmiteBluntness = stalagmiteBluntness;
        this.windSpeed = windSpeed;
        this.minRadiusForWind = minRadiusForWind;
        this.minBluntnessForWind = minBluntnessForWind;
        this.angle = angle;
        this.rareIceChance = rareIceChance;
    }
}
