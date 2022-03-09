package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class AncientSandBlock extends SandBlock {
    public AncientSandBlock(int dustColor, Properties properties) {
        super(dustColor, properties);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        if (random.nextInt(12) == 0 && FallingBlock.isFree(level.getBlockState(blockPos.below()))) {
            double d = (double)blockPos.getX() + random.nextDouble();
            double e = (double)blockPos.getY() - 0.05;
            double f = (double)blockPos.getZ() + random.nextDouble();
            level.addParticle(YCBModParticles.ANCIENT_DUST, d, e, f, 0.0, 0.0, 0.0);
        }
    }
}
