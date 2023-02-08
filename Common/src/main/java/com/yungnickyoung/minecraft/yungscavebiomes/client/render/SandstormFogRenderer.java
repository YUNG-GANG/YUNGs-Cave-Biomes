package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;

public class SandstormFogRenderer {
    public static final FogSettings fogSettings = new FogSettings();

    /**
     * Customizes the fog in Lost Caves during sandstorms.
     * Note that there is a separate Forge-only mixin required, since Forge replaces the vanilla setupFog method with their own.
     */
    public static void render(FogRenderer.FogMode mode, float renderDistance) {
        if (mode == FogRenderer.FogMode.FOG_TERRAIN) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (localPlayer == null || clientLevel == null) return;

            // Increase if in sandstorm, decrease if not
            if (clientLevel.getBiome(localPlayer.blockPosition()).is(BiomeModule.LOST_CAVES.getResourceKey())
                    && ((ISandstormClientData) clientLevel).isSandstormActive()) {
                fogSettings.fogLevel = Mth.clamp(fogSettings.fogLevel + 0.002, 0, 1);
            } else {
                fogSettings.fogLevel = Mth.clamp(fogSettings.fogLevel - 0.002, 0, 1);
            }

            // Interpolate fog to be closer to the player
            if (fogSettings.fogLevel > 0) {
                RenderSystem.setShaderFogStart((float) Mth.lerp(fogSettings.fogLevel, RenderSystem.getShaderFogStart(), -4f));
                RenderSystem.setShaderFogEnd((float) Mth.lerp(fogSettings.fogLevel, RenderSystem.getShaderFogEnd(), Math.min(renderDistance * 0.45f, 64)));
            }
        }
    }

    /**
     * Customizes the fog color in Lost Caves during sandstorms.
     */
    public static void setupFogColor() {
        // Interpolate fog if needed
        if (fogSettings.fogLevel > 0) {
            fogSettings.red = (float) Mth.lerp(fogSettings.fogLevel, fogSettings.red, 0.8f);
            fogSettings.green = (float) Mth.lerp(fogSettings.fogLevel, fogSettings.green, 0.5f);
            fogSettings.blue = (float) Mth.lerp(fogSettings.fogLevel, fogSettings.blue, 0.15f);
        }
    }

    public static class FogSettings {
        public float red, green, blue;
        public double fogLevel;

        public FogSettings() {
        }
    }
}
