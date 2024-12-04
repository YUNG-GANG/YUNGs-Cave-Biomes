package com.yungnickyoung.minecraft.yungscavebiomes.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class FabricConfigFrostedCaves {
    @ConfigEntry.Category("Biome Noise Parameters")
    @ConfigEntry.Gui.CollapsibleObject
    public FabricConfigFrostedCavesNoiseParams noiseParameters = new FabricConfigFrostedCavesNoiseParams();
}
