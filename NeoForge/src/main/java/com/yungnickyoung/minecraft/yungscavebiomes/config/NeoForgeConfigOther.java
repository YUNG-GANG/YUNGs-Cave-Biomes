package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfigOther {
    public final NeoForgeConfigOther.VanillaBiomeModifications vanillaBiomeModifications;

    public NeoForgeConfigOther(final ModConfigSpec.Builder BUILDER) {
        BUILDER.push("Other");
        vanillaBiomeModifications = new VanillaBiomeModifications(BUILDER);
        BUILDER.pop();
    }

    public static class VanillaBiomeModifications {
        public final DripstoneCaves dripstoneCaves;

        public VanillaBiomeModifications(final ModConfigSpec.Builder BUILDER) {
            BUILDER.push("Vanilla Biome Modifications");
            dripstoneCaves = new DripstoneCaves(BUILDER);
            BUILDER.pop();
        }

        public static class DripstoneCaves {
            public final ModConfigSpec.DoubleValue temperatureMin;
            public final ModConfigSpec.DoubleValue temperatureMax;
            public final ModConfigSpec.DoubleValue humidityMin;
            public final ModConfigSpec.DoubleValue humidityMax;
            public final ModConfigSpec.DoubleValue continentalnessMin;
            public final ModConfigSpec.DoubleValue continentalnessMax;
            public final ModConfigSpec.DoubleValue erosionMin;
            public final ModConfigSpec.DoubleValue erosionMax;
            public final ModConfigSpec.DoubleValue depthMin;
            public final ModConfigSpec.DoubleValue depthMax;
            public final ModConfigSpec.DoubleValue weirdnessMin;
            public final ModConfigSpec.DoubleValue weirdnessMax;
            public final ModConfigSpec.DoubleValue offset;

            public DripstoneCaves(final ModConfigSpec.Builder BUILDER) {
                BUILDER.push("Dripstone Caves");

                temperatureMin = BUILDER
                        .worldRestart()
                        .defineInRange("Temperature Min", -0.7, -1, 1);

                temperatureMax = BUILDER
                        .worldRestart()
                        .defineInRange("Temperature Max", 1.0, -1, 1);

                humidityMin = BUILDER
                        .worldRestart()
                        .defineInRange("Humidity Min", -1.0, -1, 1);

                humidityMax = BUILDER
                        .worldRestart()
                        .defineInRange("Humidity Max", 1.0, -1, 1);

                continentalnessMin = BUILDER
                        .worldRestart()
                        .defineInRange("Continentalness Min", 0.8, -1, 1);

                continentalnessMax = BUILDER
                        .worldRestart()
                        .defineInRange("Continentalness Max", 1.0, -1, 1);

                erosionMin = BUILDER
                        .worldRestart()
                        .defineInRange("Erosion Min", -1.0, -1, 1);

                erosionMax = BUILDER
                        .worldRestart()
                        .defineInRange("Erosion Max", 1.0, -1, 1);

                depthMin = BUILDER
                        .worldRestart()
                        .defineInRange("Depth Min", 0.2, 0, 1);

                depthMax = BUILDER
                        .worldRestart()
                        .defineInRange("Depth Max", 0.9, 0, 1);

                weirdnessMin = BUILDER
                        .worldRestart()
                        .defineInRange("Weirdness Min", -1.0, -1, 1);

                weirdnessMax = BUILDER
                        .worldRestart()
                        .defineInRange("Weirdness Max", 1.0, -1, 1);

                offset = BUILDER
                        .worldRestart()
                        .defineInRange("Offset", 0.0, -1, 1);

                BUILDER.pop();
            }
        }
    }
}
