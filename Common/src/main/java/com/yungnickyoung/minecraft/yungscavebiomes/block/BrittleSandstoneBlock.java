package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BrittleSandstoneBlock extends Block {
    private final int dustColor;

    public BrittleSandstoneBlock(int dustColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        if (random.nextInt(36) == 0 && FallingBlock.isFree(level.getBlockState(blockPos.below()))) {
            double d = (double)blockPos.getX() + random.nextDouble();
            double e = (double)blockPos.getY() - 0.05;
            double f = (double)blockPos.getZ() + random.nextDouble();
            level.addParticle(new BlockParticleOption(ParticleTypeModule.ANCIENT_DUST.get(), blockState), d, e, f, 0.0, 0.0, 0.0);
        }
    }

    public int getDustColor() {
        return this.dustColor;
    }
}
