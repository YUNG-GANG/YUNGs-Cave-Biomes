package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;

public class YCBModConfiguredFeatures {
    public static ConfiguredFeature<LargeDripstoneConfiguration, ?> LARGE_ICICLE = YCBModFeatures.LARGE_ICICLE.configured(
            new LargeDripstoneConfiguration(
                    30,
                    UniformInt.of(4, 19),
                    UniformFloat.of(0.4F, 2.0F),
                    0.45F, // Vanilla is 0.33F, but we want bigger!
                    UniformFloat.of(0.3F, 0.9F),
                    UniformFloat.of(0.4F, 1.0F),
                    UniformFloat.of(0.0F, 0.3F),
                    4,
                    0.6F
            ));

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
    }

    private static void register(String name, ConfiguredFeature<?, ?> obj) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), obj);
    }
}
