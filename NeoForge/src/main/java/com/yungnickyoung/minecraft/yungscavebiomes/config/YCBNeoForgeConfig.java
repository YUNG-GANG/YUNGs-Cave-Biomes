package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class YCBNeoForgeConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final NeoForgeConfigLostCaves lostCaves;
    public static final NeoForgeConfigFrostedCaves frostedCaves;
    public static final NeoForgeConfigOther other;

    static {
        BUILDER.push("YUNG's Cave Biomes");

        lostCaves = new NeoForgeConfigLostCaves(BUILDER);
        frostedCaves = new NeoForgeConfigFrostedCaves(BUILDER);
        other = new NeoForgeConfigOther(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
