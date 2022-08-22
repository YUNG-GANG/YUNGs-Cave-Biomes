package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundModule {
    public static SoundEvent MUSIC_BIOME_ICE_CAVES = new SoundEvent(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "music.overworld.ice_caves"));
    public static SoundEvent MUSIC_BIOME_DESERT_CAVES = new SoundEvent(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "music.overworld.desert_caves"));
    public static SoundEvent AMBIENT_BLOCK_RARE_ICE = new SoundEvent(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "block.rare_ice.ambient"));

    public static void init() {
        Services.REGISTRY.registerSoundEvent(MUSIC_BIOME_ICE_CAVES.getLocation(), MUSIC_BIOME_ICE_CAVES);
        Services.REGISTRY.registerSoundEvent(MUSIC_BIOME_DESERT_CAVES.getLocation(), MUSIC_BIOME_DESERT_CAVES);
        Services.REGISTRY.registerSoundEvent(AMBIENT_BLOCK_RARE_ICE.getLocation(), AMBIENT_BLOCK_RARE_ICE);
    }
}
