package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Arrays;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @WrapOperation(method = "<clinit>",
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=brushable_block"),
                    to = @At(value = "CONSTANT", args = "stringValue=decorated_pot")
            ),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;register(Ljava/lang/String;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static <T extends BlockEntity> BlockEntityType<T> yungscavebiomes_allowAncientSand(
            final String name,
            final @Coerce Object factory,
            final Block[] validBlocks,
            final Operation<BlockEntityType<T>> original) {
        if (name.equals("brushable_block")) {
            Block[] newValidBlocks = Arrays.copyOf(validBlocks, validBlocks.length + 1);
            newValidBlocks[validBlocks.length] = BlockModule.ANCIENT_SAND.get();
            return original.call(name, factory, newValidBlocks);
        }
        return original.call(name, factory, validBlocks);
    }
}
