package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class YCBConfigForge {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("YUNG's Cave Biomes");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
