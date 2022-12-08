package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BuffetedOverlayFabric implements HudRenderCallback {
    private static final ResourceLocation texture = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "textures/overlay/buffeted_overlay.png");
    private static final int maxTicks = 200;
    private static final float maxOpacity = 1.0f;
    private static final float minColor = 0.1f;
    private static final float maxColor = 0.9f;
    private static int ticks;
    private static float color = 0.5f;

    /**
     * Renders the Buffeted overlay on the player's screen, with variable opacity depending
     * on the remaining duration of the effect.
     */
    @Override
    public void onHudRender(PoseStack matrixStack, float partialTicks) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        ticks = client.player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get())
                ? Math.min(ticks + 1, maxTicks)
                : Math.max(ticks - 1, 0);

        if (!client.player.isSpectator() && ticks > 0) {
            float opacity = Mth.clamp(ticks / (float) maxTicks, 0, maxOpacity);
            int screenWidth = client.getWindow().getGuiScaledWidth();
            int screenHeight = client.getWindow().getGuiScaledHeight();

            // Determine world light at player position
            int packedLight = client.getEntityRenderDispatcher().getPackedLightCoords(client.player, partialTicks);
            int worldLight = Mth.clamp(Math.max(LightTexture.block(packedLight), LightTexture.sky(packedLight)), 0, 15);
            int currLight = (int) (color * 16);

            // Determine color based on light
            int colorDiff = worldLight - currLight;
            color += .003f * colorDiff;
            color = Mth.clamp(color, minColor, maxColor);

            renderOverlay(opacity, screenWidth, screenHeight, color);
        }
    }

    /**
     * Taken from vanilla's Gui class.
     */
    private static void renderOverlay(float opacity, int screenWidth, int screenHeight, float color) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(color, color, color, opacity);
        RenderSystem.setShaderTexture(0, texture);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder
                .vertex(0.0D, screenHeight, -90.0D)
                .uv(0.0F, 1.0F)
                .endVertex();
        bufferbuilder
                .vertex(screenWidth, screenHeight, -90.0D)
                .uv(1.0F, 1.0F)
                .endVertex();
        bufferbuilder
                .vertex(screenWidth, 0.0D, -90.0D)
                .uv(1.0F, 0.0F)
                .endVertex();
        bufferbuilder
                .vertex(0.0D, 0.0D, -90.0D)
                .uv(0.0F, 0.0F)
                .endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
