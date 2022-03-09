package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class MixinCactusBlock {
    @Inject(method = "canSurvive", at = @At("TAIL"), cancellable = true)
    public void allowCactusOnAncientSand(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockStateBelow = levelReader.getBlockState(blockPos.below());
        BlockState blockStateAbove = levelReader.getBlockState(blockPos.above());
        if (blockStateBelow.is(YCBModBlocks.ANCIENT_SAND) && !blockStateAbove.getMaterial().isLiquid()) {
            cir.setReturnValue(true);
        }
    }
}
