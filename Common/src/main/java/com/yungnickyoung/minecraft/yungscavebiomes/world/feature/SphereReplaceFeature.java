package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

// Replaces blocks in a sphere that matches a list of blocks
public class SphereReplaceFeature extends Feature<SphereReplaceConfig> {

    public SphereReplaceFeature(Codec<SphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SphereReplaceConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        int radius = context.config().radius;
        List<Block> matches = context.config().matches;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dx = x / (double) radius;
                    double dy = y / (double) radius;
                    double dz = z / (double) radius;
                    if (dx * dx + dy * dy + dz * dz <= 1.0) {
                        BlockPos local = pos.offset(x, y, z);

                        if (matches.contains(level.getBlockState(local).getBlock())) {
                            level.setBlock(local, context.config().place, 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}
