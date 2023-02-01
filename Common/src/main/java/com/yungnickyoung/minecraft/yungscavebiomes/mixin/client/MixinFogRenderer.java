package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class MixinFogRenderer {
    @Shadow
    private static float fogRed;
    @Shadow
    private static float fogGreen;
    @Shadow
    private static float fogBlue;

    private static double lostCavesFoggy = 0;

    /**
     * Customizes the fog in Lost Caves during sandstorms.
     * Note that there is a separate Forge-only mixin required, since Forge replaces the vanilla setupFog method with their own.
     */
    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void yungscavebiomes_handleLostCavesFog(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean isVeryFoggy, CallbackInfo ci) {
        if (mode == FogRenderer.FogMode.FOG_TERRAIN) {
            // Increase if buffeted, decrease if not
            if (Minecraft.getInstance().player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get())) {
                lostCavesFoggy = Mth.clamp(lostCavesFoggy + 0.002, 0, 1);
            } else {
                lostCavesFoggy = Mth.clamp(lostCavesFoggy - 0.002, 0, 1);
            }

            // Interpolate fog to be closer to the player
            if (lostCavesFoggy > 0) {
                RenderSystem.setShaderFogStart((float) Mth.lerp(lostCavesFoggy, RenderSystem.getShaderFogStart(), -4f));
                RenderSystem.setShaderFogEnd((float) Mth.lerp(lostCavesFoggy, RenderSystem.getShaderFogEnd(), Math.min(renderDistance * 0.45f, 64)));
            }
        }
    }

    /**
     * Customizes the fog color in Lost Caves during sandstorms.
     */
    @Inject(method = "setupColor", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer;biomeChangedTime:J", ordinal = 4, opcode = 179 /*PUTFIELD*/, shift = At.Shift.AFTER))
    private static void yungscavebiomes_setupLostCavesColor(Camera camera, float f, ClientLevel clientLevel, int i2, float g, CallbackInfo ci) {
        // Interpolate fog if needed
        if (lostCavesFoggy > 0) {
            fogRed = (float) Mth.lerp(lostCavesFoggy, fogRed, 0.8f);
            fogGreen = (float) Mth.lerp(lostCavesFoggy, fogGreen, 0.5f);
            fogBlue = (float) Mth.lerp(lostCavesFoggy, fogBlue, 0.15f);
        }
    }
}
