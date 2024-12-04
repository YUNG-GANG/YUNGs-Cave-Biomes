package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeConfigLostCaves {
    public final ForgeConfigSpec.ConfigValue<Boolean> enableSandstorms;
    public final ForgeConfigSpec.ConfigValue<Integer> minSandstormDuration;
    public final ForgeConfigSpec.ConfigValue<Integer> maxSandstormDuration;
    public final ForgeConfigSpec.ConfigValue<Integer> minTimeBetweenSandstorms;
    public final ForgeConfigSpec.ConfigValue<Integer> maxTimeBetweenSandstorms;
    public final ForgeConfigSpec.ConfigValue<Boolean> extraSandstormParticles;

    public final ForgeConfigLostCavesNoiseParams noiseParameters;

    public ForgeConfigLostCaves(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER.push("Lost Caves");

        enableSandstorms = BUILDER
                .worldRestart()
                .define("Enable Sandstorms", true);

        minSandstormDuration = BUILDER
                .worldRestart()
                .define("Min Sandstorm Duration (seconds)", 2 * 60);

        maxSandstormDuration = BUILDER
                .worldRestart()
                .define("Max Sandstorm Duration (seconds)", 10 * 60);

        minTimeBetweenSandstorms = BUILDER
                .worldRestart()
                .define("Min Time Between Sandstorms (seconds)", 20 * 60);

        maxTimeBetweenSandstorms = BUILDER
                .worldRestart()
                .define("Max Time Between Sandstorms (seconds)", 40 * 60);

        extraSandstormParticles = BUILDER
                .define("Render Extra Particles During Sandstorms", true);

        noiseParameters = new ForgeConfigLostCavesNoiseParams(BUILDER);

        BUILDER.pop();
    }
}
