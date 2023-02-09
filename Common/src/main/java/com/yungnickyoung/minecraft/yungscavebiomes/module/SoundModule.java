package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterSoundEvent;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class SoundModule {
    @AutoRegister("music.overworld.ice_caves")
    public static AutoRegisterSoundEvent MUSIC_BIOME_ICE_CAVES = AutoRegisterSoundEvent.create();

    @AutoRegister("block.rare_ice.ambient")
    public static AutoRegisterSoundEvent AMBIENT_BLOCK_RARE_ICE = AutoRegisterSoundEvent.create();

    @AutoRegister("music.overworld.lost_caves")
    public static AutoRegisterSoundEvent MUSIC_BIOME_LOST_CAVES = AutoRegisterSoundEvent.create();

    @AutoRegister("ambient.lost_caves.loop")
    public static AutoRegisterSoundEvent AMBIENT_LOST_CAVES = AutoRegisterSoundEvent.create();

    @AutoRegister("ambient.lost_caves.sandstorm_loop")
    public static AutoRegisterSoundEvent SANDSTORM_AMBIENT_LOST_CAVES = AutoRegisterSoundEvent.create();
}
