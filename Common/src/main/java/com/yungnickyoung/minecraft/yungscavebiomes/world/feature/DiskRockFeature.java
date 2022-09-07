package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Random;

public class DiskRockFeature extends Feature<SimpleBlockConfiguration> {
    public DiskRockFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        SimpleBlockConfiguration config = context.config();
        BlockState state = config.toPlace().getState(context.random(), context.origin());

        NormalNoise noise = NormalNoise.create(new LegacyRandomSource(random.nextLong()), -3, 1.0);

        // 3 - 7
        int radXZ = random.nextInt(5) + 3;
        int radY = random.nextInt(2) + (int) (radXZ / 1.5);
        double aRadY = radY;

        double taperPercent = random.nextDouble() * 0.4;

        for (int x = -radXZ; x <= radXZ; x++) {
            for (int z = -radXZ; z <= radXZ; z++) {

                for (int y = -radY; y <= radY + 1; y++) {
                    int sub = y == radY + 1 ? 2 : 0;
                    double dx = x / (double) (radXZ - sub);
                    double dz = z / (double) (radXZ - sub);
                    double dy = y / (double) aRadY;

                    // Make y a bit smaller
                    dy *= 1.05;

                    double noiseHere = noise.getValue(x + pos.getX(), y + pos.getY() / 1.2, z + pos.getZ()) * 0.5;

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
