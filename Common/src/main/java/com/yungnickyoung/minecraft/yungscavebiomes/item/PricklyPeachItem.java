package com.yungnickyoung.minecraft.yungscavebiomes.item;

import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class PricklyPeachItem extends Item {
    public PricklyPeachItem(Properties properties) {
        super(properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        ItemStack retVal = super.finishUsingItem(itemStack, level, livingEntity);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaModule.EAT_PRICKLY_PEACH.trigger(serverPlayer);
        }
        livingEntity.hurt(level.damageSources().cactus(), 1.0f);
        return retVal;
    }
}
