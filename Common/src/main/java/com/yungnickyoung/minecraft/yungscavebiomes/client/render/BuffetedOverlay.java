package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BuffetedOverlay implements LayeredDraw.Layer {
    private static BuffetedOverlay INSTANCE;
    public static BuffetedOverlay getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BuffetedOverlay();
        }
        return INSTANCE;
    }

    private static final ResourceLocation OVERLAY_TEXTURE = YungsCaveBiomesCommon.id("textures/overlay/buffeted_overlay.png");
    private static final int MAX_TICKS = 200;
    private static final float MAX_OPACITY = 1.0f;
    private static final float MIN_COLOR = 0.1f;
    private static final float MAX_COLOR = 0.9f;

    private int ticks;
    private float color = 0.5f;

    /**
     * Renders the Buffeted overlay on the player's screen, with variable opacity depending
     * on the remaining duration of the effect.
     */
    public void render(@NotNull GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        // Reset overlay on player death so it doesn't persist on respawn
        if (!client.player.isAlive()) {
            ticks = 0;
        }

        ticks = client.player.hasEffect(MobEffectModule.BUFFETED_EFFECT.getHolder())
                ? Math.min(ticks + 1, MAX_TICKS)
                : Math.max(ticks - 1, 0);

        if (!client.player.isSpectator() && ticks > 0) {
            float opacity = Mth.clamp(ticks / (float) MAX_TICKS, 0, MAX_OPACITY);

            // Determine world light at player position
            int packedLight = client.getEntityRenderDispatcher().getPackedLightCoords(client.player, partialTicks);
            int worldLight = Mth.clamp(Math.max(LightTexture.block(packedLight), LightTexture.sky(packedLight)), 0, 15);
            int currLight = (int) (color * 16);

            // Determine color based on light
            int colorDiff = worldLight - currLight;
            color += .003f * colorDiff;
            color = Mth.clamp(color, MIN_COLOR, MAX_COLOR);

            renderTextureOverlay(guiGraphics, color, color, color, opacity);
        }
    }

    // Taken from vanilla's Gui class
    private static void renderTextureOverlay(GuiGraphics guiGraphics, float r, float g, float b, float alpha) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        guiGraphics.setColor(r, g, b, alpha);
        guiGraphics.blit(OVERLAY_TEXTURE, 0, 0, -90, 0.0F, 0.0F,
                guiGraphics.guiWidth(), guiGraphics.guiHeight(),
                guiGraphics.guiWidth(), guiGraphics.guiHeight());
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
