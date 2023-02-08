package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.SandstormFogRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Unique mixin for Forge since they replace the vanilla setupFog method with their own.
 */
@Mixin(FogRenderer.class)
public abstract class MixinFogRendererForge {
    @Shadow
    private static float fogRed;
    @Shadow
    private static float fogGreen;
    @Shadow
    private static float fogBlue;

    @Unique
    private static final SandstormFogRenderer sandstormFogRenderer = new SandstormFogRenderer();

    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At("TAIL"), remap = false)
    private static void yungscavebiomes_handleLostCavesFogForge(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean isVeryFoggy, float partialTicks, CallbackInfo ci) {
        sandstormFogRenderer.render(mode, renderDistance);
    }

    @Inject(method = "setupColor", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer;biomeChangedTime:J", ordinal = 4, opcode = 179 /*PUTFIELD*/, shift = At.Shift.AFTER))
    private static void yungscavebiomes_setupLostCavesColorForge(Camera camera, float f, ClientLevel clientLevel, int i2, float g, CallbackInfo ci) {        // Interpolate fog if needed
        if (sandstormFogRenderer.fogLevel > 0) {
            fogRed = (float) Mth.lerp(sandstormFogRenderer.fogLevel, fogRed, 0.8f);
            fogGreen = (float) Mth.lerp(sandstormFogRenderer.fogLevel, fogGreen, 0.5f);
            fogBlue = (float) Mth.lerp(sandstormFogRenderer.fogLevel, fogBlue, 0.15f);
        }
    }
}
