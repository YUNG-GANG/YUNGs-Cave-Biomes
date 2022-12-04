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
public class MixinFogRenderer {
    @Shadow
    private static float fogRed;
    @Shadow
    private static float fogGreen;
    @Shadow
    private static float fogBlue;

    private static double lostCavesFoggy = 0;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void handleLostCavesFog(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean isVeryFoggy, CallbackInfo ci) {
        if (mode == FogRenderer.FogMode.FOG_TERRAIN && Minecraft.getInstance().player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get())) {
            lostCavesFoggy = Mth.clamp(lostCavesFoggy + 0.05, 0, 1);

//            RenderSystem.setShaderFogStart((float) Mth.lerp(lostCavesFoggy, RenderSystem.getShaderFogStart(), -4f));
//            RenderSystem.setShaderFogEnd((float) Mth.lerp(lostCavesFoggy, RenderSystem.getShaderFogEnd(), renderDistance * 0.45f));

            RenderSystem.setShaderFogStart(-4f);
            RenderSystem.setShaderFogEnd(renderDistance * 0.45f);
        } else {
            lostCavesFoggy = Mth.clamp(lostCavesFoggy - 0.05, 0, 1);
        }
    }

    @Inject(method = "setupColor", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer;biomeChangedTime:J", ordinal = 4, opcode = 179 /*PUTFIELD*/, shift = At.Shift.AFTER))
    private static void setupLostCavesColor(Camera camera, float f, ClientLevel clientLevel, int i2, float g, CallbackInfo ci) {
        if (Minecraft.getInstance().player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get())) {
            fogRed = 0.8f;
            fogGreen = 0.5f;
            fogBlue = 0.15f;
        }
    }
}
