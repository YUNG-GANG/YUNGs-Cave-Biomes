package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util.NoisySphereUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

/**
 * Handles the placement of layered ancient sandstone in the walls/ceiling as well as
 * ancient sand & ancient sandstone in the floor. Similar to {@link ThreeLayerNoisySphereReplaceFeature}, but with
 * hardcoded settings and behaviors.
 *
 * This feature is specific to the Lost Caves, meaning it is not reusable for other biomes.
 * While that is typically undesirable, I figured creating a generalized solution
 * would be over-engineering. We can always generalize this in the future if we find ourselves wanting
 * similar behavior in other biomes.
 */
public class LostCavesSurfaceReplaceFeature extends Feature<NoneFeatureConfiguration> {
    private static final double NOISE_FREQUENCY_XZ = 0.15;
    private static final double NOISE_FREQUENCY_Y = 0.15;
    private static final long NOISE_SEED_FLIP_MASK = -0x6139D09B0B75F247L;

    private static final int RADIUS_MIN = 10;
    private static final int RADIUS_MAX = 16;

    public LostCavesSurfaceReplaceFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        long noiseSeed = context.level().getSeed() ^ NOISE_SEED_FLIP_MASK;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        List<Block> matches = ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE, BlockModule.LAYERED_ANCIENT_SANDSTONE.get());

        for (BlockPos here : new NoisySphereUtils.NoisySphereIterable(
                origin, noiseSeed, NOISE_FREQUENCY_XZ, NOISE_FREQUENCY_Y, RADIUS_MIN, RADIUS_MAX)) {
            if (matches.contains(level.getBlockState(here).getBlock())) {
                boolean placed = false;
                for (int yOffset = 1; yOffset <= 6; yOffset++) {
                    mutableBlockPos.set(here).move(Direction.UP, yOffset);
                    BlockState replacement = yOffset <= 3
                            ? BlockModule.ANCIENT_SAND.get().defaultBlockState()
                            : BlockModule.ANCIENT_SANDSTONE.get().defaultBlockState();
                    if (level.getBlockState(mutableBlockPos).isAir()) {
                        level.setBlock(here, replacement, 3);
                        placed = true;
                        break;
                    }
                }
                if (!placed) {
                    level.setBlock(here, BlockModule.LAYERED_ANCIENT_SANDSTONE.get().defaultBlockState(), 3);
                }
            }
        }

        return true;
    }
}
