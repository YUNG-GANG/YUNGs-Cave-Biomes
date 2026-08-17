package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin {
    @Inject(method = "getSideSprite", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_renderCustomPotteryMaterial(Optional<Item> item, CallbackInfoReturnable<SpriteId> cir) {
        if (item.isPresent()) {
            ResourceKey<DecoratedPotPattern> resourceKey = DecoratedPotPatternsModule.getResourceKeyForItem(item.get().builtInRegistryHolder());
            var sprite = Sheets.getDecoratedPotSprite(resourceKey);
            if (sprite != null) {
                cir.setReturnValue(sprite);
            }
        }
    }
}
