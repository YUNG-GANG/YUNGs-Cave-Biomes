package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow
    private Vec3 position;

    @Shadow
    public abstract AABB getBoundingBox();

    @Shadow
    public abstract Level level();

    @Inject(method = "getBlockPosBelowThatAffectsMyMovement", at = @At("HEAD"), cancellable = true)
    private void yungscavebiomes_applyLowFrictionWhenOnIceSheet(CallbackInfoReturnable<BlockPos> cir) {
        BlockPos iceSheetPos = BlockPos.containing(this.position.x, this.getBoundingBox().minY + 0.1, this.position.z);
        if (this.level().getBlockState(iceSheetPos).is(BlockModule.ICE_SHEET.get())) {
            cir.setReturnValue(iceSheetPos);
        }
    }
}
