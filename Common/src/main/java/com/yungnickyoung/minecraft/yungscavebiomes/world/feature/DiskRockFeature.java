package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Random;

public class DiskRockFeature extends Feature<ReplaceBlockConfiguration> {
    public DiskRockFeature(Codec<ReplaceBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ReplaceBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        ReplaceBlockConfiguration config = context.config();
        BlockState state = config.targetStates.get(0).state;

        NormalNoise noise = NormalNoise.create(new LegacyRandomSource(random.nextLong()), -3, 1.0);

        // 3 - 7
        int radXZ = random.nextInt(5) + 3;
        int radY = random.nextInt(2) + (int) (radXZ / 1.5);
        double aRadY = radY;

        double taperPercent = random.nextDouble() * 0.4;

        for (int x = -radXZ; x <= radXZ; x++) {
            for (int z = -radXZ; z <= radXZ; z++) {

                for (int y = -radY; y <= radY; y++) {
                    double dx = x / (double) radXZ;
                    double dz = z / (double) radXZ;
                    double dy = y / (double) aRadY;

                    // Make y a bit smaller
                    dy *= 1.05;

                    double noiseHere = noise.getValue(x + pos.getX(), y + pos.getY(), z + pos.getZ()) * 0.1;

                    // We want to squeeze in the y direction first
                    if (dy * dy <= 1 + noiseHere) {
                        if (dx * dx + dz * dz <= 1 + noiseHere) {
                            BlockPos local = pos.offset(x, y, z);
                            level.setBlock(local, state, 3);
                        }
                    }

                    aRadY += taperPercent;
                }
            }
        }


        return false;
    }
}
