package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public abstract class MixinCactusBlock {
    /**
     * Allow cactus blocks to be placed on ancient sand.
     */
    @Inject(method = "canSurvive", at = @At("TAIL"), cancellable = true)
    public void yungscavebiomes_allowCactusOnAncientSand(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockStateBelow = levelReader.getBlockState(blockPos.below());
        BlockState blockStateAbove = levelReader.getBlockState(blockPos.above());
        if (blockStateBelow.is(BlockModule.ANCIENT_SAND.get()) && !blockStateAbove.getMaterial().isLiquid()) {
            cir.setReturnValue(true);
        }
    }
}
