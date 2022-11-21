package com.yungnickyoung.minecraft.yungscavebiomes.module;

public class ConfigModule {
    public FrostedCaves frostedCaves = new FrostedCaves();
    public LostCaves lostCaves = new LostCaves();

    public static class FrostedCaves {
    }

    public static class LostCaves {
        public boolean enableSandstorms = true;
        public int avgSandstormDuration = 5;
        public int avgTimeBetweenSandstorms = 30;
        public int minTimeBetweenSandstorms = 10;
    }
}
