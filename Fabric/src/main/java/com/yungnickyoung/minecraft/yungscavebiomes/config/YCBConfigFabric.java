package com.yungnickyoung.minecraft.yungscavebiomes.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="yungscavebiomes-fabric-1_20_1")
public class YCBConfigFabric implements ConfigData {
    @ConfigEntry.Category("YUNG's Cave Biomes")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigLostCavesFabric lostCaves = new ConfigLostCavesFabric();
}
