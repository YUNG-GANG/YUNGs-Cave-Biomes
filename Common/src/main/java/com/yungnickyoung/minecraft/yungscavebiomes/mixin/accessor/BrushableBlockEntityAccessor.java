package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BrushableBlockEntity.class)
public interface BrushableBlockEntityAccessor {
    @Accessor
    void setItem(ItemStack item);
}
