package com.yungnickyoung.minecraft.yungscavebiomes.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class FabricConfigOther {
    @ConfigEntry.Category("Vanilla Biome Modifications")
    @ConfigEntry.Gui.CollapsibleObject
    public VanillaBiomeModifications vanillaBiomeModifications = new VanillaBiomeModifications();

    public static class VanillaBiomeModifications {
        @ConfigEntry.Category("Dripstone Caves")
        @ConfigEntry.Gui.CollapsibleObject
        public DripstoneCaves dripstoneCaves = new DripstoneCaves();

        public static class DripstoneCaves {
            public float temperatureMin = -0.7F;
            public float temperatureMax = 1.0F;
            public float humidityMin = -1.0F;
            public float humidityMax = 1.0F;
            public float continentalnessMin = 0.8F;
            public float continentalnessMax = 1.0F;
            public float erosionMin = -1.0F;
            public float erosionMax = 1.0F;
            public float depthMin = 0.2F;
            public float depthMax = 0.9F;
            public float weirdnessMin = -1.0F;
            public float weirdnessMax = 1.0F;
            public float offset = 0.0F;
        }
    }
}
