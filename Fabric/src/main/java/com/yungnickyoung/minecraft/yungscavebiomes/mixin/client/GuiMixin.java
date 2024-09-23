package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.BuffetedOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I"))
    public void yungscavebiomes_renderBufferedOverlay(GuiGraphics guiGraphics, float partialTick, CallbackInfo callbackInfo) {
        Minecraft client = Minecraft.getInstance();
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();
        BuffetedOverlay.render(partialTick, screenWidth, screenHeight);
    }
}