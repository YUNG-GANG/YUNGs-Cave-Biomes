package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class CeilingReplaceConfig implements FeatureConfiguration {
    public static final Codec<CeilingReplaceConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().listOf().fieldOf("matches").forGetter(c -> c.matches),
            BlockState.CODEC.fieldOf("place").forGetter(c -> c.place),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("width", 1).forGetter(c -> c.width),
            Codec.INT.fieldOf("radiusMin").forGetter(c -> c.radiusMin),
            Codec.INT.fieldOf("radiusMax").forGetter(c -> c.radiusMax)
    ).apply(instance, CeilingReplaceConfig::new));
    public final List<Block> matches;
    public final BlockState place;
    public final int width;
    public final int radiusMin, radiusMax;

    public CeilingReplaceConfig(List<Block> matches, BlockState place, int width, int radiusMin, int radiusMax) {
        this.matches = matches;
        this.place = place;
        this.width = width;
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
    }
}
