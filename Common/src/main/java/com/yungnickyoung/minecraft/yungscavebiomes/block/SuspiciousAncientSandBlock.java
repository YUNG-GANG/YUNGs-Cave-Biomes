package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.SuspiciousAncientSandBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SuspiciousAncientSandBlock extends BrushableBlock {
    public SuspiciousAncientSandBlock(Properties properties, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
        super(BlockModule.ANCIENT_SAND.get(), properties, brushingSound, brushingCompleteSound);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SuspiciousAncientSandBlockEntity(blockPos, blockState);
    }
}
