package com.yungnickyoung.minecraft.yungscavebiomes.module;

public class ConfigModule {
    public FrostedCaves frostedCaves = new FrostedCaves();
    public LostCaves lostCaves = new LostCaves();

    public static class FrostedCaves {
    }

    public static class LostCaves {
        public boolean enableSandstorms = true;
        public boolean extraSandstormParticles = true;

        // Sandstorm times, in seconds
        public int minSandstormDuration = 2 * 60; // 2 min
        public int maxSandstormDuration = 10 * 80; // 8 min
        public int minTimeBetweenSandstorms = 20 * 60; // 20 min
        public int maxTimeBetweenSandstorms = 40 * 60; // 40 min
    }
}
