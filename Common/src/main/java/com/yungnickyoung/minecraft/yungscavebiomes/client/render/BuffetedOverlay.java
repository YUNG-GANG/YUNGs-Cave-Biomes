package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class BuffetedOverlay {
    public static final BuffetedOverlay INSTANCE = new BuffetedOverlay();

    private static final Identifier OVERLAY_TEXTURE = YungsCaveBiomesCommon.id("textures/overlay/buffeted_overlay.png");
    private static final int MAX_TICKS = 200;
    private static final float MAX_OPACITY    = 1.0f;
    private static final float MIN_BRIGHTNESS = 0.1f;
    private static final float MAX_BRIGHTNESS = 0.9f;

    private float ticks;
    private float brightness = 0.5f;

    /**
     * Renders the Buffeted overlay on the player's screen, with variable opacity depending
     * on the remaining duration of the effect.
     */
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }

        // Reset overlay on player death so it doesn't persist on respawn
        if (!client.player.isAlive()) {
            this.ticks = 0;
        }

        //todo test me
        float deltaTicks = deltaTracker.getGameTimeDeltaTicks();
        this.ticks = client.player.hasEffect(MobEffectModule.BUFFETED_EFFECT.getHolder())
                ? Math.min(this.ticks + deltaTicks, MAX_TICKS)
                : Math.max(this.ticks - deltaTicks, 0);

        if (!client.player.isSpectator() && this.ticks > 0) {
            float opacity = Mth.clamp(this.ticks / (float) MAX_TICKS, 0, MAX_OPACITY);

            // Determine world light at player position
            BlockPos blockPos = client.player.blockPosition();
            float levelBrightness = Lightmap.getBrightness(client.player.level().dimensionType(), client.player.level().getMaxLocalRawBrightness(blockPos));

            // Determine color based on light
            float brDiff = levelBrightness - this.brightness;
            this.brightness += 0.048f * brDiff;
            this.brightness = Mth.clamp(this.brightness, MIN_BRIGHTNESS, MAX_BRIGHTNESS);

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    OVERLAY_TEXTURE,
                    0,
                    0,
                    0.0F,
                    0.0F,
                    graphics.guiWidth(),
                    graphics.guiHeight(),
                    graphics.guiWidth(),
                    graphics.guiHeight(),
                    ARGB.colorFromFloat(opacity, this.brightness, this.brightness, this.brightness)
            );
        }
    }
}
