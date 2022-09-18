package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class NoisySphereReplaceConfig implements FeatureConfiguration {
    public static final Codec<NoisySphereReplaceConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.byNameCodec().listOf().fieldOf("matches").forGetter(c -> c.matches),
            BlockState.CODEC.fieldOf("place").forGetter(c -> c.place),
            Codec.INT.fieldOf("radiusMin").forGetter(c -> c.radiusMin),
            Codec.INT.fieldOf("radiusMax").forGetter(c -> c.radiusMax)
    ).apply(instance, NoisySphereReplaceConfig::new));
    public final List<Block> matches;
    public final BlockState place;
    public final int radiusMin, radiusMax;

    public NoisySphereReplaceConfig(List<Block> matches, BlockState place, int radiusMin, int radiusMax) {
        this.matches = matches;
        this.place = place;
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
    }
}
