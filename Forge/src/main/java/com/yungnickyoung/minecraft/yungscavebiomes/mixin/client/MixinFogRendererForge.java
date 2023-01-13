package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Unique mixin for Forge since they replace the vanilla setupFog method with their own.
 */
@Mixin(FogRenderer.class)
public abstract class MixinFogRendererForge {
    private static double lostCavesFoggy = 0;

    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At("TAIL"), remap = false)
    private static void yungscavebiomes_handleLostCavesFogForge(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean isVeryFoggy, float partialTicks, CallbackInfo ci) {
        if (mode == FogRenderer.FogMode.FOG_TERRAIN && Minecraft.getInstance().player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get())) {
            lostCavesFoggy = Mth.clamp(lostCavesFoggy + 0.05, 0, 1);

            RenderSystem.setShaderFogStart(-4f);
            RenderSystem.setShaderFogEnd(Math.min(renderDistance * 0.45f, 64));
        } else {
            lostCavesFoggy = Mth.clamp(lostCavesFoggy - 0.05, 0, 1);
        }
    }
}
