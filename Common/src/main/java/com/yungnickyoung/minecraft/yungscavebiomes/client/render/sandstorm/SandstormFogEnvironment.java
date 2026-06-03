package com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import org.jspecify.annotations.Nullable;

public class SandstormFogEnvironment extends FogEnvironment {
    private static final int FOG_COLOR = ARGB.colorFromFloat(1f, 0.8f, 0.5f, 0.15f);

    /**
     * The current fog level. 0 is no fog, 1 is full fog.
     * This is used to smoothly transition the fog level when entering and exiting the Lost Caves biome during sandstorms.
     */
    private double fogLevel = 0;

    @Override
    public void setupFog(final FogData fog, final Camera camera, final ClientLevel level, final float renderDistance, final DeltaTracker deltaTracker) {
        this.updateFogState(camera, level, deltaTracker);
        if (this.fogLevel > 0) {
            fog.environmentalStart = Mth.lerp((float) this.fogLevel, fog.environmentalStart, -4f);
            float minRainFogEnd = Math.min(96.0F, fog.environmentalEnd); // as per AtmosphericFogEnvironment
            fog.environmentalEnd = Mth.lerp((float) this.fogLevel, fog.environmentalEnd, Math.max(minRainFogEnd, 64));
        }
    }

    @Override
    public int getBaseColor(final ClientLevel level, final Camera camera, final int renderDistance, final float partialTicks) {
        if (this.fogLevel > 0) {
            return ARGB.srgbLerp((float) this.fogLevel, -1, FOG_COLOR);
        } else {
            return -1;
        }
    }

    @Override public boolean isApplicable(@Nullable final FogType fogType, final Entity entity) {
        return fogType == FogType.ATMOSPHERIC;
    }

    private void updateFogState(final Camera camera, final ClientLevel level, final DeltaTracker deltaTracker) {
        BlockPos blockPos = camera.blockPosition();
        var biome = level.getBiome(blockPos);
        float deltaTicks = deltaTracker.getGameTimeDeltaTicks();
        double targetFogLevel;
        if (biome.is(BiomeModule.LOST_CAVES) && ((ISandstormClientDataProvider) level).getSandstormClientData().isSandstormActive()) {
            targetFogLevel = 1f;
        } else {
            targetFogLevel = 0f;
        }
        this.fogLevel = this.fogLevel + targetFogLevel * deltaTicks * 0.02f;

        // Reset fog if player is dead so it doesn't persist on respawn
        // todo really, this should be done once when the respawn packet is sent.
        //      if immediate respawn is on, then the player might never be dead from the client's perspective.
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null && !localPlayer.isAlive()) {
            this.fogLevel = 0f;
        }
    }
}
