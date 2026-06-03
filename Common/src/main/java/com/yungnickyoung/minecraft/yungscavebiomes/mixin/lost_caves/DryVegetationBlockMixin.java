package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import net.minecraft.world.level.block.DryVegetationBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DryVegetationBlock.class)
public abstract class DryVegetationBlockMixin {
    /**
     * Allow dead bush blocks to be placed on ancient sand.
     */
//    @Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
//    public void yungscavebiomes_placeDeadBushOnAncientSand(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
//        if (blockState.is(BlockModule.ANCIENT_SAND.get())) {
//            cir.setReturnValue(true);
//        }
//    }
}
