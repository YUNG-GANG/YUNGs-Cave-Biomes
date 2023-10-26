package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;

public class SandstormFogRenderer {
    public double fogLevel = 0;

    /**
     * Customizes the fog in Lost Caves during sandstorms.
     * Note that there is a separate Forge-only mixin required, since Forge replaces the vanilla setupFog method with their own.
     */
    public void render(FogRenderer.FogMode mode, float renderDistance) {
        if (mode == FogRenderer.FogMode.FOG_TERRAIN) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (localPlayer == null || clientLevel == null) return;

            // Increase if in sandstorm, decrease if not
            if (clientLevel.getBiome(localPlayer.blockPosition()).is(BiomeModule.LOST_CAVES.getResourceKey())
                    && ((ISandstormClientData) clientLevel).isSandstormActive()) {
                this.fogLevel = Mth.clamp(this.fogLevel + 0.002, 0, 1);
            } else {
                this.fogLevel = Mth.clamp(this.fogLevel - 0.002, 0, 1);
            }

            // Interpolate fog to be closer to the player
            if (this.fogLevel > 0) {
                RenderSystem.setShaderFogStart((float) Mth.lerp(this.fogLevel, RenderSystem.getShaderFogStart(), -4f));
                RenderSystem.setShaderFogEnd((float) Mth.lerp(this.fogLevel, RenderSystem.getShaderFogEnd(), Math.min(renderDistance * 0.45f, 64)));
            }
        }
    }
}
