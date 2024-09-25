package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    /**
     * Prevents rare ice rendering faces when embedded in ice.
     */
    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_cancelRareIceRender(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction, BlockPos neighborPos, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(BlockModule.RARE_ICE.get())) {
            BlockState neighborBlockState = blockGetter.getBlockState(neighborPos);
            if (neighborBlockState.isFaceSturdy(blockGetter, neighborPos, direction.getOpposite())) {
                cir.setReturnValue(false);
            }
        } else if (blockState.is(Blocks.ICE)) {
            BlockState neighborBlockState = blockGetter.getBlockState(neighborPos);
            if (neighborBlockState.is(BlockModule.RARE_ICE.get())) {
                cir.setReturnValue(false);
            }
        }
    }
}
