package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.block.RareIceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void ycbCancelRareIceRender(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction, BlockPos blockPos2, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.getBlock() instanceof RareIceBlock) {
            BlockState blockState2 = blockGetter.getBlockState(blockPos2);
            if (blockState2.isFaceSturdy(blockGetter, blockPos2, direction.getOpposite())) {
                cir.setReturnValue(false);
            }
        }
    }
}
