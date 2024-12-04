package com.yungnickyoung.minecraft.yungscavebiomes.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class FabricConfigLostCaves {
    public boolean enableSandstorms = true;
    public boolean extraSandstormParticles = true;
    public int minSandstormDuration = 2 * 60;
    public int maxSandstormDuration = 10 * 60;
    public int minTimeBetweenSandstorms = 20 * 60;
    public int maxTimeBetweenSandstorms = 40 * 60;

    @ConfigEntry.Category("Biome Noise Parameters")
    @ConfigEntry.Gui.CollapsibleObject
    public FabricConfigLostCavesNoiseParams noiseParameters = new FabricConfigLostCavesNoiseParams();

}
