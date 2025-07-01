package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfigLostCaves {
    public final ModConfigSpec.ConfigValue<Boolean> enableSandstorms;
    public final ModConfigSpec.ConfigValue<Integer> minSandstormDuration;
    public final ModConfigSpec.ConfigValue<Integer> maxSandstormDuration;
    public final ModConfigSpec.ConfigValue<Integer> minTimeBetweenSandstorms;
    public final ModConfigSpec.ConfigValue<Integer> maxTimeBetweenSandstorms;
    public final ModConfigSpec.ConfigValue<Boolean> extraSandstormParticles;

    public final NeoForgeConfigLostCavesNoiseParams noiseParameters;

    public NeoForgeConfigLostCaves(final ModConfigSpec.Builder BUILDER) {
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

        noiseParameters = new NeoForgeConfigLostCavesNoiseParams(BUILDER);

        BUILDER.pop();
    }
}
