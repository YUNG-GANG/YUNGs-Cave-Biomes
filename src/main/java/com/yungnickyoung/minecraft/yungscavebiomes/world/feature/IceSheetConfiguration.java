package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IceSheetConfiguration implements FeatureConfiguration {
    public static final Codec<IceSheetConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(config -> config.searchRange)
    ).apply(instance, IceSheetConfiguration::new));

    public final int searchRange;
    public final List<Direction> validDirections;

    public IceSheetConfiguration(int searchRange) {
        this.searchRange = searchRange;
        ArrayList<Direction> directions = Lists.newArrayList();
        directions.addAll(Arrays.asList(Direction.values()));
        this.validDirections = Collections.unmodifiableList(directions);
    }
}
