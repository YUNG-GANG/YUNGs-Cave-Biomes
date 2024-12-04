package com.yungnickyoung.minecraft.yungscavebiomes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeConfigOther {
    public final ForgeConfigOther.VanillaBiomeModifications vanillaBiomeModifications;

    public ForgeConfigOther(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER.push("Other");
        vanillaBiomeModifications = new VanillaBiomeModifications(BUILDER);
        BUILDER.pop();
    }

    public static class VanillaBiomeModifications {
        public final DripstoneCaves dripstoneCaves;

        public VanillaBiomeModifications(final ForgeConfigSpec.Builder BUILDER) {
            BUILDER.push("Vanilla Biome Modifications");
            dripstoneCaves = new DripstoneCaves(BUILDER);
            BUILDER.pop();
        }

        public static class DripstoneCaves {
            public final ForgeConfigSpec.DoubleValue temperatureMin;
            public final ForgeConfigSpec.DoubleValue temperatureMax;
            public final ForgeConfigSpec.DoubleValue humidityMin;
            public final ForgeConfigSpec.DoubleValue humidityMax;
            public final ForgeConfigSpec.DoubleValue continentalnessMin;
            public final ForgeConfigSpec.DoubleValue continentalnessMax;
            public final ForgeConfigSpec.DoubleValue erosionMin;
            public final ForgeConfigSpec.DoubleValue erosionMax;
            public final ForgeConfigSpec.DoubleValue depthMin;
            public final ForgeConfigSpec.DoubleValue depthMax;
            public final ForgeConfigSpec.DoubleValue weirdnessMin;
            public final ForgeConfigSpec.DoubleValue weirdnessMax;
            public final ForgeConfigSpec.DoubleValue offset;

            public DripstoneCaves(final ForgeConfigSpec.Builder BUILDER) {
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
