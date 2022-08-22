package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class MultisurfaceSphereReplaceConfig implements FeatureConfiguration {
    public static final Codec<MultisurfaceSphereReplaceConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.byNameCodec().listOf().fieldOf("matches").forGetter(c -> c.matches),
            BlockState.CODEC.fieldOf("floor").forGetter(c -> c.floor),
            BlockState.CODEC.fieldOf("wall").forGetter(c -> c.wall),
            BlockState.CODEC.fieldOf("ceiling").forGetter(c -> c.ceiling),
            Codec.INT.fieldOf("radius").forGetter(c -> c.radius)
    ).apply(instance, MultisurfaceSphereReplaceConfig::new));
    public final List<Block> matches;
    public final BlockState floor;
    public final BlockState wall;
    public final BlockState ceiling;
    public final int radius;

    public MultisurfaceSphereReplaceConfig(List<Block> matches, BlockState floor, BlockState wall, BlockState ceiling, int radius) {
        this.matches = matches;
        this.floor = floor;
        this.wall = wall;
        this.ceiling = ceiling;
        this.radius = radius;
    }
}
