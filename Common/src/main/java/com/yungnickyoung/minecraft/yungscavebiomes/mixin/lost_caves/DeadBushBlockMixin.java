package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeadBushBlock.class)
public abstract class DeadBushBlockMixin {
    /**
     * Allow dead bush blocks to be placed on ancient sand.
     */
    @Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
    public void yungscavebiomes_placeDeadBushOnAncientSand(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(BlockModule.ANCIENT_SAND.get())) {
            cir.setReturnValue(true);
        }
    }
}
