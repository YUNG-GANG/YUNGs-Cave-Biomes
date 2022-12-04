package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class ThreeLayerNoisySphereReplaceFeature extends Feature<ThreeLayerNoisySphereReplaceConfig> {

    private static final double NOISE_FREQUENCY_XZ = 0.15;
    private static final double NOISE_FREQUENCY_Y = 0.15;
    private static final long NOISE_SEED_FLIP_MASK = -0x6139D09B0B75F247L;

    public ThreeLayerNoisySphereReplaceFeature(Codec<ThreeLayerNoisySphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ThreeLayerNoisySphereReplaceConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        int radiusMin = context.config().radiusMin;
        int radiusMax = context.config().radiusMax;
        List<Block> matches = context.config().matches;
        long noiseSeed = context.level().getSeed() ^ NOISE_SEED_FLIP_MASK;
        BlockState floor = context.config().floor.orElse(null);
        BlockState ceiling = context.config().ceiling.orElse(null);
        int floorWidth = context.config().floorWidth;
        int ceilingWidth = context.config().ceilingWidth;

        for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(
                origin, noiseSeed, NOISE_FREQUENCY_XZ, NOISE_FREQUENCY_Y, radiusMin, radiusMax)) {
            if (matches.contains(level.getBlockState(here).getBlock())) {
                boolean placed = false;
                if (floor != null) {
                    for (int yOffset = 1; yOffset <= floorWidth; yOffset++) {
                        if (level.getBlockState(here.above(yOffset)).isAir()) {
                            level.setBlock(here, floor, 3);
                            placed = true;
                            break;
                        }
                    }
                }
                if (!placed && ceiling != null) {
                    for (int yOffset = 1; yOffset <= ceilingWidth; yOffset++) {
                        if (level.getBlockState(here.below(yOffset)).isAir()) {
                            level.setBlock(here, ceiling, 3);
                            placed = true;
                            break;
                        }
                    }
                }
                if (!placed) {
                    level.setBlock(here, context.config().regular, 3);
                }
            }
        }

        return true;
    }
}
