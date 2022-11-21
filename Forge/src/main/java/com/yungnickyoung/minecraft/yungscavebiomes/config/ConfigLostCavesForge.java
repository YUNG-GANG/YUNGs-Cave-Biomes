package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigLostCavesForge {
    public final ForgeConfigSpec.ConfigValue<Boolean> enableSandstorms;
    public final ForgeConfigSpec.ConfigValue<Integer> avgSandstormDuration;
    public final ForgeConfigSpec.ConfigValue<Integer> avgTimeBetweenSandstorms;
    public final ForgeConfigSpec.ConfigValue<Integer> minTimeBetweenSandstorms;
    public ConfigLostCavesForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER.push("Lost Caves");

        enableSandstorms = BUILDER
                .worldRestart()
                .define("Enable Sandstorms", true);

        avgSandstormDuration = BUILDER
                .worldRestart()
                .define("Average Sandstorm Duration (Minutes)", 5);

        avgTimeBetweenSandstorms = BUILDER
                .worldRestart()
                .define("Average Time Between Sandstorms (Minutes)", 30);

        minTimeBetweenSandstorms = BUILDER
                .worldRestart()
                .define("Minimum Time Between Sandstorms (Minutes)", 10);


        BUILDER.pop();
    }
}
