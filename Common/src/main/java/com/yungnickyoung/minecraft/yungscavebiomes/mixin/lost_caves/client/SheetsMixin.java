package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SpriteMapper;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(Sheets.class)
public abstract class SheetsMixin {
    @Shadow @Final public static SpriteMapper DECORATED_POT_MAPPER;
    @Unique
    private static final Map<ResourceKey<String>, SpriteId> CUSTOM_DECORATED_POT_SPRITES = new HashMap<>();

    @Inject(method = "getDecoratedPotSprite", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_createCustomPotteryMaterials(ResourceKey<String> key, CallbackInfoReturnable<SpriteId> cir) {
        if (key == null) return;
        if (DecoratedPotPatternsModule.isCustomRegisteredKey(key)) {
            // Cache the material so we don't have to create it again
            if (!CUSTOM_DECORATED_POT_SPRITES.containsKey(key)) {
                CUSTOM_DECORATED_POT_SPRITES.put(key, DECORATED_POT_MAPPER.apply(key.identifier()));
            }

            cir.setReturnValue(CUSTOM_DECORATED_POT_SPRITES.get(key));
        }
    }
}
