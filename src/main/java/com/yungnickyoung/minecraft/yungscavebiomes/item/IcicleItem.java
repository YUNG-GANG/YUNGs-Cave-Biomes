package com.yungnickyoung.minecraft.yungscavebiomes.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IcicleItem extends ArrowItem {
    public IcicleItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        Arrow arrow = new Arrow(level, livingEntity);
        arrow.setEffectsFromItem(itemStack);
        return arrow;
    }
}
