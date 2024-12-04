package com.yungnickyoung.minecraft.yungscavebiomes.module;

public class ConfigModule {
    public FrostedCaves frostedCaves = new FrostedCaves();
    public LostCaves lostCaves = new LostCaves();
    public Other other = new Other();

    public static class FrostedCaves {
        public BiomeNoiseParameters noiseParameters = new BiomeNoiseParameters();

        public static class BiomeNoiseParameters {
            public float temperatureMin = -1.0F;
            public float temperatureMax = -0.7F;
            public float humidityMin = -1.0F;
            public float humidityMax = 1.0F;
            public float continentalnessMin = -0.19F;
            public float continentalnessMax = 1.0F;
            public float erosionMin = -1.0F;
            public float erosionMax = 1.0F;
            public float depthMin = 0.2F;
            public float depthMax = 0.9F;
            public float weirdnessMin = -1.0F;
            public float weirdnessMax = 1.0F;
            public float offset = 0.0F;
        }
    }

    public static class LostCaves {
        public boolean enableSandstorms = true;
        public boolean extraSandstormParticles = true;

        // Sandstorm times, in seconds
        public int minSandstormDuration = 2 * 60; // 2 min
        public int maxSandstormDuration = 10 * 80; // 8 min
        public int minTimeBetweenSandstorms = 20 * 60; // 20 min
        public int maxTimeBetweenSandstorms = 40 * 60; // 40 min

        public BiomeNoiseParameters noiseParameters = new BiomeNoiseParameters();

        public static class BiomeNoiseParameters {
            public float temperatureMin = 0.55F;
            public float temperatureMax = 1.0F;
            public float humidityMin = -1.0F;
            public float humidityMax = 0.6F;
            public float continentalnessMin = 0.3F;
            public float continentalnessMax = 1.0F;
            public float erosionMin = -1.0F;
            public float erosionMax = 1.0F;
            public float depthMin = 0.2F;
            public float depthMax = 0.9F;
            public float weirdnessMin = -1.0F;
            public float weirdnessMax = 1.0F;
            public float offset = 0.0F;
        }
    }

    public static class Other {
        public VanillaBiomeModifications vanillaBiomeModifications = new VanillaBiomeModifications();

        public static class VanillaBiomeModifications {
            public DripstoneCaves dripstoneCaves = new DripstoneCaves();

            public static class DripstoneCaves {
                public float temperatureMin = -0.7F;
                public float temperatureMax = 1.0F;
                public float humidityMin = -1.0F;
                public float humidityMax = 1.0F;
                public float continentalnessMin = 0.8F;
                public float continentalnessMax = 1.0F;
                public float erosionMin = -1.0F;
                public float erosionMax = 1.0F;
                public float depthMin = 0.2F;
                public float depthMax = 0.9F;
                public float weirdnessMin = -1.0F;
                public float weirdnessMax = 1.0F;
                public float offset = 0.0F;
            }
        }
    }
}
