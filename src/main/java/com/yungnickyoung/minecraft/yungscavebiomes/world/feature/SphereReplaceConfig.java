package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class SphereReplaceConfig implements FeatureConfiguration {
    public static final Codec<SphereReplaceConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.byNameCodec().listOf().fieldOf("matches").forGetter(c -> c.matches),
            BlockState.CODEC.fieldOf("place").forGetter(c -> c.place),
            Codec.INT.fieldOf("radius").forGetter(c -> c.radius)
    ).apply(instance, SphereReplaceConfig::new));
    public final List<Block> matches;
    public final BlockState place;
    public final int radius;

    public SphereReplaceConfig(List<Block> matches, BlockState place, int radius) {
        this.matches = matches;
        this.place = place;
        this.radius = radius;
    }
}
