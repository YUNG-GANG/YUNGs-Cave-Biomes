package com.yungnickyoung.minecraft.yungscavebiomes.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class FabricConfigEntrypoint {
    @ConfigEntry.Category("Lost Caves")
    @ConfigEntry.Gui.CollapsibleObject
    public FabricConfigLostCaves lostCaves = new FabricConfigLostCaves();

    @ConfigEntry.Category("Frosted Caves")
    @ConfigEntry.Gui.CollapsibleObject
    public FabricConfigFrostedCaves frostedCaves = new FabricConfigFrostedCaves();

    @ConfigEntry.Category("Other")
    @ConfigEntry.Gui.CollapsibleObject
    public FabricConfigOther other = new FabricConfigOther();
}
