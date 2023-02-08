package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.SandstormFogRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Unique mixin for Forge since they replace the vanilla setupFog method with their own.
 */
@Mixin(FogRenderer.class)
public abstract class MixinFogRendererForge {
    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At("TAIL"), remap = false)
    private static void yungscavebiomes_handleLostCavesFogForge(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean isVeryFoggy, float partialTicks, CallbackInfo ci) {
        SandstormFogRenderer.render(mode, renderDistance);
    }

    @Inject(method = "setupColor", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer;biomeChangedTime:J", ordinal = 4, opcode = 179 /*PUTFIELD*/, shift = At.Shift.AFTER))
    private static void yungscavebiomes_setupLostCavesColorForge(Camera camera, float f, ClientLevel clientLevel, int i2, float g, CallbackInfo ci) {
        SandstormFogRenderer.setupFogColor();
    }
}
