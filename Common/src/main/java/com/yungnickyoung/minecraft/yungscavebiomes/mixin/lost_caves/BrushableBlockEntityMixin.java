package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.yungnickyoung.minecraft.yungscavebiomes.block.SuspiciousAncientSandBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.lang.classfile.Opcode;

@Mixin(BrushableBlockEntity.class)
public class BrushableBlockEntityMixin {
    @WrapOperation(method = "<init>", at = @At(value = "FIELD",
            opcode = 178, // getstatic
            target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;BRUSHABLE_BLOCK:Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static BlockEntityType<BrushableBlockEntity> yungscavebiomes_allowCustomType(
            final Operation<BlockEntityType<BrushableBlockEntity>> original) {
        return SuspiciousAncientSandBlock.BRUSHABLE_BE_TYPE.orElse(original.call());
    }
}
