package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class YCBModSounds {
    public static SoundEvent MUSIC_BIOME_ICE_CAVES = new SoundEvent(new ResourceLocation(YungsCaveBiomes.MOD_ID, "music.overworld.ice_caves"));
    public static SoundEvent MUSIC_BIOME_DESERT_CAVES = new SoundEvent(new ResourceLocation(YungsCaveBiomes.MOD_ID, "music.overworld.desert_caves"));

    public static void init() {
        Registry.register(Registry.SOUND_EVENT, MUSIC_BIOME_ICE_CAVES.getLocation(), MUSIC_BIOME_ICE_CAVES);
        Registry.register(Registry.SOUND_EVENT, MUSIC_BIOME_DESERT_CAVES.getLocation(), MUSIC_BIOME_DESERT_CAVES);
    }
}
