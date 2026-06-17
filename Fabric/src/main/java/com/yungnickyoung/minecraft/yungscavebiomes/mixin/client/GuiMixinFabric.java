package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.BuffetedOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixinFabric {
    @Inject(method = "extractCameraOverlays", at = @At(value = "RETURN"))
    public void yungscavebiomes_renderBufferedOverlay(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        BuffetedOverlay.INSTANCE.extract(guiGraphics, deltaTracker);
    }
}