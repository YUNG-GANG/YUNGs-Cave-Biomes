package com.yungnickyoung.minecraft.yungscavebiomes.client.sounds;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.client.sandstorm.SandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import com.yungnickyoung.minecraft.yungscavebiomes.client.sandstorm.ISandstormClientDataProvider;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;

/**
 * Handles playing the ambient sounds when the player is in the Lost Caves biome.
 * We need our own distinct handler for this because the ambient sound changes
 * depending on whether a sandstorm is active.
 */
public class LostCavesAmbientSoundsHandler implements AmbientSoundHandler {
    private static final ResourceLocation regularAmbientSound =
            new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ambient.lost_caves.loop");

    private static final ResourceLocation sandstormAmbientSound =
            new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ambient.lost_caves.sandstorm_loop");

    private final LocalPlayer player;
    private final SoundManager soundManager;
    private final BiomeManager biomeManager;
    private BiomeAmbientSoundsHandler.LoopSoundInstance lostCavesSound = new BiomeAmbientSoundsHandler.LoopSoundInstance(SoundModule.AMBIENT_LOST_CAVES.get());
    private boolean inLostCaves = false;

    public LostCavesAmbientSoundsHandler(LocalPlayer localPlayer, SoundManager soundManager, BiomeManager biomeManager) {
        this.player = localPlayer;
        this.soundManager = soundManager;
        this.biomeManager = biomeManager;
    }

    @Override
    public void tick() {
        Holder<Biome> currBiome = this.biomeManager.getNoiseBiomeAtPosition(this.player.getX(), this.player.getY(), this.player.getZ());

        // Start playing ambient sound when entering lost caves biome
        if (currBiome.is(BiomeModule.LOST_CAVES.getResourceKey()) && !this.inLostCaves) {
            this.inLostCaves = true;
            this.lostCavesSound = new BiomeAmbientSoundsHandler.LoopSoundInstance(SoundModule.AMBIENT_LOST_CAVES.get());
            this.soundManager.play(lostCavesSound);
            this.lostCavesSound.fadeIn();
        }

        // Stop playing ambient sounds when leaving lost caves biome
        if (!currBiome.is(BiomeModule.LOST_CAVES.getResourceKey()) && this.inLostCaves) {
            this.inLostCaves = false;
            this.lostCavesSound.fadeOut();
        }

        SandstormClientData sandstormClientData = ((ISandstormClientDataProvider) this.player.level).getSandstormClientData();

        if (this.inLostCaves) {
            // Set sandstorm sound
            if (sandstormClientData.isSandstormActive()
                    && !this.lostCavesSound.getLocation().equals(sandstormAmbientSound)) {
                changeAmbientsound(SoundModule.SANDSTORM_AMBIENT_LOST_CAVES.get());
            }

            // Set regular sound
            if (!sandstormClientData.isSandstormActive()
                    && !this.lostCavesSound.getLocation().equals(regularAmbientSound)) {
                changeAmbientsound(SoundModule.AMBIENT_LOST_CAVES.get());
            }
        }
    }

    /**
     * Fades out the current ambient sound and fades in the new one.
     * @param newAmbientSoundEvent The new ambient sound to play.
     */
    private void changeAmbientsound(SoundEvent newAmbientSoundEvent) {
        this.lostCavesSound.fadeOut();
        this.lostCavesSound = new BiomeAmbientSoundsHandler.LoopSoundInstance(newAmbientSoundEvent);
        this.soundManager.play(lostCavesSound);
        this.lostCavesSound.fadeIn();
    }
}
