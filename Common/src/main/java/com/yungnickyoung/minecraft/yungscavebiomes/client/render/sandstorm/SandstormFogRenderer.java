package com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;

public class SandstormFogRenderer {
    /**
     * The current fog level. 0 is no fog, 1 is full fog.
     * This is used to smoothly transition the fog level when entering and exiting the Lost Caves biome during sandstorms.
     */
    private double fogLevel = 0;

    /**
     * Renders the fog in Lost Caves during sandstorms.
     * Note that the mixin that invokes this method is defined separately in each loader module,
     * since Forge replaces the vanilla setupFog method with their own.
     */
    public void render(FogRenderer.FogMode mode, float renderDistance) {
        if (mode == FogRenderer.FogMode.FOG_TERRAIN) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (localPlayer == null || clientLevel == null) return;

            // Increase fog if in active sandstorm, decrease if not
            if (clientLevel.getBiome(localPlayer.blockPosition()).is(BiomeModule.LOST_CAVES.getResourceKey())
                    && ((ISandstormClientDataProvider) clientLevel).getSandstormClientData().isSandstormActive()) {
                this.fogLevel = Mth.clamp(this.fogLevel + 0.002, 0, 1);
            } else {
                this.fogLevel = Mth.clamp(this.fogLevel - 0.002, 0, 1);
            }

            // Reset fog if player is dead so it doesn't persist on respawn
            if (!localPlayer.isAlive()) {
                this.fogLevel = 0;
            }

            // Interpolate fog to be closer to the player
            if (this.fogLevel > 0) {
                RenderSystem.setShaderFogStart((float) Mth.lerp(this.fogLevel, RenderSystem.getShaderFogStart(), -4f));
                RenderSystem.setShaderFogEnd((float) Mth.lerp(this.fogLevel, RenderSystem.getShaderFogEnd(), Math.min(renderDistance * 0.45f, 64)));
            }
        }
    }

    public double getFogLevel() {
        return fogLevel;
    }
}
