package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeConfigFrostedCaves {
    public final ForgeConfigFrostedCavesNoiseParams noiseParameters;

    public ForgeConfigFrostedCaves(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER.push("Frosted Caves");

        noiseParameters = new ForgeConfigFrostedCavesNoiseParams(BUILDER);

        BUILDER.pop();
    }
}
