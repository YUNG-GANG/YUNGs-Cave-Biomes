package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class FeatureModule {
    @AutoRegister("large_icicle")
    public static Feature<LargeIceDripstoneConfiguration> LARGE_ICICLE = new LargeIceDripstoneFeature(LargeIceDripstoneConfiguration.CODEC);

    @AutoRegister("icicle_cluster")
    public static Feature<DripstoneClusterConfiguration> ICICLE_CLUSTER = new IcicleClusterFeature(DripstoneClusterConfiguration.CODEC);

    @AutoRegister("sphere_replace")
    public static Feature<SphereReplaceConfig> SPHERE_REPLACE = new SphereReplaceFeature(SphereReplaceConfig.CODEC);

    @AutoRegister("cactus_patch")
    public static Feature<NoneFeatureConfiguration> CACTUS_PATCH = new CactusPatchFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("prickly_peach_cactus_patch")
    public static Feature<NoneFeatureConfiguration> PRICKLY_PEACH_CACTUS_PATCH = new PricklyPeachCactusPatchFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("multisurface_sphere_replace")
    public static Feature<MultisurfaceSphereReplaceConfig> MULTISURFACE_SPHERE_REPLACE = new MultisurfaceSphereReplaceFeature(MultisurfaceSphereReplaceConfig.CODEC);

    @AutoRegister("water_surface_ice_fragment")
    public static Feature<NoneFeatureConfiguration> WATER_SURFACE_ICE_FRAGMENT = new WaterSurfaceIceFragmentFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("disk_rock")
    public static Feature<SimpleBlockConfiguration> DISK_ROCK = new DiskRockFeature(SimpleBlockConfiguration.CODEC);

    @AutoRegister("ice_sheet_replace")
    public static Feature<IceSheetConfiguration> ICE_SHEET_REPLACE = new IceSheetFeature(IceSheetConfiguration.CODEC);
}
