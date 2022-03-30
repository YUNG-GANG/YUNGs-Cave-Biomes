package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class YCBModFeatures {
    public static Feature<LargeIceDripstoneConfiguration> LARGE_ICICLE = new LargeIceDripstoneFeature(LargeIceDripstoneConfiguration.CODEC);
    public static Feature<DripstoneClusterConfiguration> ICICLE_CLUSTER = new IcicleClusterFeature(DripstoneClusterConfiguration.CODEC);
    public static Feature<SphereReplaceConfig> SPHERE_REPLACE = new SphereReplaceFeature(SphereReplaceConfig.CODEC);
    public static Feature<NoneFeatureConfiguration> CACTUS_PATCH = new CactusPatchFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> WATER_SURFACE_ICE_FRAGMENT = new WaterSurfaceIceFragmentFeature(NoneFeatureConfiguration.CODEC);

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("icicle_cluster", ICICLE_CLUSTER);
        register("sphere_replace", SPHERE_REPLACE);
        register("cactus_patch", CACTUS_PATCH);
        register("water_surface_ice_fragment", WATER_SURFACE_ICE_FRAGMENT);
    }

    private static void register(String name, Feature<?> obj) {
        Registry.register(Registry.FEATURE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), obj);
    }
}
