package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class SuspiciousAncientSandBlock extends BrushableBlock {
    public static final ScopedValue<@Nullable BlockEntityType<BrushableBlockEntity>> BRUSHABLE_BE_TYPE = ScopedValue.newInstance();

    public SuspiciousAncientSandBlock(Properties properties, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
        super(BlockModule.ANCIENT_SAND.get(), brushingSound, brushingCompleteSound, properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return createBlockEntity(worldPosition, blockState);
    }

    public static BrushableBlockEntity createBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        return ScopedValue.where(BRUSHABLE_BE_TYPE, EntityTypeModule.BRUSHABLE.get())
                .call(() -> new BrushableBlockEntity(worldPosition, blockState));
    }
}
