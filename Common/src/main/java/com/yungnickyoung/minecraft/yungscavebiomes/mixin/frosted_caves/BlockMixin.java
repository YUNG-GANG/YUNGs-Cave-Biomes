package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
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
    private static void yungscavebiomes_cancelRareIceRender(final BlockState blockState, final BlockState neighborState, final Direction direction, final CallbackInfoReturnable<Boolean> cir) {
        //todo test me. i removed the if rare ice and other block is sturdy on opposite face then don't render as I think that's equivalent to vanilla logic
        if (blockState.is(Blocks.ICE)) {
            if (neighborState.is(BlockModule.RARE_ICE.get())) {
                cir.setReturnValue(false);
            }
        }
    }
}