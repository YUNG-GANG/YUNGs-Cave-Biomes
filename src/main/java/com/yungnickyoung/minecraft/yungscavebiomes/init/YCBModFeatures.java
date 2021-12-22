package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IcicleClusterFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneFeature;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;

public class YCBModFeatures {
    public static Feature<LargeDripstoneConfiguration> LARGE_ICICLE = new LargeIceDripstoneFeature(LargeDripstoneConfiguration.CODEC);
    public static Feature<DripstoneClusterConfiguration> ICICLE_CLUSTER = new IcicleClusterFeature(DripstoneClusterConfiguration.CODEC);

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("icicle_cluster", ICICLE_CLUSTER);
    }

    private static void register(String name, Feature<?> obj) {
        Registry.register(Registry.FEATURE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), obj);
    }
}
