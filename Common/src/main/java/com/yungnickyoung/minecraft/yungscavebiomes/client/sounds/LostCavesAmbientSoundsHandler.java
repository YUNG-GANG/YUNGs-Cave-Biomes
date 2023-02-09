package com.yungnickyoung.minecraft.yungscavebiomes.client.sounds;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;

/**
 * Handles playing the ambient sounds when the player is in the Lost Caves biome.
 * We need our own distinct handler for this because the ambient sound changes
 * depending on whether a sandstorm is active.
 */
public class LostCavesAmbientSoundsHandler implements AmbientSoundHandler {
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

        ISandstormClientData sandstormClientData = ((ISandstormClientData) this.player.level);
        // Set sandstorm sound
        if (this.inLostCaves
                && sandstormClientData.isSandstormActive()
                && this.lostCavesSound.getLocation().equals(lostCavesSoundLocation)) {
            this.lostCavesSound.fadeOut();
            this.lostCavesSound = new BiomeAmbientSoundsHandler.LoopSoundInstance(SoundModule.SANDSTORM_AMBIENT_LOST_CAVES.get());
            this.soundManager.play(lostCavesSound);
            this.lostCavesSound.fadeIn();
        }

        // Set regular sound
        if (this.inLostCaves
                && !sandstormClientData.isSandstormActive()
                && this.lostCavesSound.getLocation().equals(sandstormSoundLocation)) {
            this.lostCavesSound.fadeOut();
            this.lostCavesSound = new BiomeAmbientSoundsHandler.LoopSoundInstance(SoundModule.AMBIENT_LOST_CAVES.get());
            this.soundManager.play(lostCavesSound);
            this.lostCavesSound.fadeIn();
        }
    }

    private static final ResourceLocation lostCavesSoundLocation =
            new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ambient.lost_caves.loop");

    private static final ResourceLocation sandstormSoundLocation =
            new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ambient.lost_caves.sandstorm_loop");
}
