package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Optional;

public class ThreeLayerNoisySphereReplaceConfig implements FeatureConfiguration {
    public static final Codec<ThreeLayerNoisySphereReplaceConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.byNameCodec().listOf().fieldOf("matches").forGetter(c -> c.matches),
            BlockState.CODEC.optionalFieldOf("floor").forGetter(c -> c.floor),
            BlockState.CODEC.fieldOf("regular").forGetter(c -> c.regular),
            BlockState.CODEC.optionalFieldOf("ceiling").forGetter(c -> c.ceiling),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius_min").forGetter(c -> c.radiusMin),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius_max").forGetter(c -> c.radiusMax),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("floor_width", 1).forGetter(c -> c.floorWidth),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("ceiling_width", 1).forGetter(c -> c.ceilingWidth)
    ).apply(instance, ThreeLayerNoisySphereReplaceConfig::new));

    public List<Block> matches;
    public final Optional<BlockState> floor;
    public final BlockState regular;
    public final Optional<BlockState> ceiling;
    public final int radiusMin, radiusMax;
    public final int floorWidth, ceilingWidth;

    public ThreeLayerNoisySphereReplaceConfig(List<Block> matches,
                                              Optional<BlockState> floor,
                                              BlockState regular,
                                              Optional<BlockState> ceiling,
                                              int radiusMin,
                                              int radiusMax,
                                              int floorWidth,
                                              int ceilingWidth
    ) {
        this.matches = matches;
        this.floor = floor;
        this.regular = regular;
        this.ceiling = ceiling;
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
        this.floorWidth = floorWidth;
        this.ceilingWidth = ceilingWidth;
    }

    public ThreeLayerNoisySphereReplaceConfig(Builder builder) {
        this.matches = builder.matches;
        this.floor = builder.floor;
        this.regular = builder.regular;
        this.ceiling = builder.ceiling;
        this.radiusMin = builder.radiusMin;
        this.radiusMax = builder.radiusMax;
        this.floorWidth = builder.floorWidth;
        this.ceilingWidth = builder.ceilingWidth;
    }

    public static class Builder {
        public final List<Block> matches;
        public final BlockState regular;
        public final int radiusMin, radiusMax;
        public Optional<BlockState> floor;
        public Optional<BlockState> ceiling;
        public int floorWidth;
        public int ceilingWidth;

        public Builder(List<Block> matches, BlockState regular, int radiusMin, int radiusMax) {
            this.matches = matches;
            this.regular = regular;
            this.radiusMin = radiusMin;
            this.radiusMax = radiusMax;
            this.floor = Optional.empty();
            this.ceiling = Optional.empty();
            this.floorWidth = 1;
            this.ceilingWidth = 1;
        }

        public Builder floor(BlockState blockState) {
            this.floor = Optional.of(blockState);
            return this;
        }

        public Builder ceiling(BlockState blockState) {
            this.ceiling = Optional.of(blockState);
            return this;
        }

        public Builder floorWidth(int width) {
            this.floorWidth = width;
            return this;
        }

        public Builder ceilingWidth(int width) {
            this.ceilingWidth = width;
            return this;
        }

        public ThreeLayerNoisySphereReplaceConfig build() {
            return new ThreeLayerNoisySphereReplaceConfig(this);
        }
    }
}
