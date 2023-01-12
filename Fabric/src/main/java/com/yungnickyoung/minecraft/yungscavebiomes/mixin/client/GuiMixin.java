package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.BuffetedOverlayFabric;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I"))
    public void yungscavebiomes_renderBufferedOverlay(PoseStack matrixStack, float tickDelta, CallbackInfo callbackInfo) {
        BuffetedOverlayFabric.render(matrixStack, tickDelta);
    }
}