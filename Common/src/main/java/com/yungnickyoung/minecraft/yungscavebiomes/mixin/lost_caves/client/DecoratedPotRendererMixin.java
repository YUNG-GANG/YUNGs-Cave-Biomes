package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
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
    @Inject(method = "getSideMaterial", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_renderCustomPotteryMaterial(Optional<Item> item, CallbackInfoReturnable<Material> cir) {
        if (item.isPresent()) {
            ResourceKey<DecoratedPotPattern> resourceKey = DecoratedPotPatternsModule.getResourceKeyForItem(item.get());
            Material material = Sheets.getDecoratedPotMaterial(resourceKey);
            if (material != null) {
                cir.setReturnValue(material);
            }
        }
    }
}
