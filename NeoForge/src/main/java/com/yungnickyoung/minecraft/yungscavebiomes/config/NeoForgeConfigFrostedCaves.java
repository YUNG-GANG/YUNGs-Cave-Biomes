package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfigFrostedCaves {
    public final NeoForgeConfigFrostedCavesNoiseParams noiseParameters;

    public NeoForgeConfigFrostedCaves(final ModConfigSpec.Builder BUILDER) {
        BUILDER.push("Frosted Caves");

        noiseParameters = new NeoForgeConfigFrostedCavesNoiseParams(BUILDER);

        BUILDER.pop();
    }
}
