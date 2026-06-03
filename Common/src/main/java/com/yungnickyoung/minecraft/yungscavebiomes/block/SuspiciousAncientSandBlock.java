package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SuspiciousAncientSandBlock extends BrushableBlock {
    public SuspiciousAncientSandBlock(Properties properties, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
        super(BlockModule.ANCIENT_SAND.get(), brushingSound, brushingCompleteSound, properties);
    }
}
