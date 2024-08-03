package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.block.IcicleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractCauldronBlock.class)
public abstract class MixinAbstractCauldronBlock extends Block {
    public MixinAbstractCauldronBlock(Properties properties) {
        super(properties);
    }

    @Shadow
    protected abstract boolean canReceiveStalactiteDrip(Fluid fluid);

    @Shadow
    protected abstract void receiveStalactiteDrip(BlockState blockState, Level level, BlockPos blockPos, Fluid fluid);

    /**
     * Allows cauldrons to receive icicle drips.
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void yungscavebiomes_checkForIcicleDrip(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random, CallbackInfo ci) {
        BlockPos iciclePos = IcicleBlock.findIcicleTipAboveCauldron(serverLevel, blockPos);
        if (iciclePos == null) {
            return;
        }
        Fluid fluid = IcicleBlock.getCauldronFillFluidType(serverLevel, iciclePos);
        if (fluid != Fluids.EMPTY && this.canReceiveStalactiteDrip(fluid)) {
            this.receiveStalactiteDrip(blockState, serverLevel, blockPos, fluid);
        }
    }
}
