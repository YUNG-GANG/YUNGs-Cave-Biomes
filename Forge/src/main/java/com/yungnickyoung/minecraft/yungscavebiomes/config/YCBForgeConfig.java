package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class YCBForgeConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigLostCaves lostCaves;
    public static final ForgeConfigFrostedCaves frostedCaves;
    public static final ForgeConfigOther other;

    static {
        BUILDER.push("YUNG's Cave Biomes");

        lostCaves = new ForgeConfigLostCaves(BUILDER);
        frostedCaves = new ForgeConfigFrostedCaves(BUILDER);
        other = new ForgeConfigOther(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
