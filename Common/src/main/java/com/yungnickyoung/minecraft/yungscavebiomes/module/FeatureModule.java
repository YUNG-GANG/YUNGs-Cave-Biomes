package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.CactusPatchFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.CeilingReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.CeilingReplaceFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.FloorReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.FloorReplaceFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IceSheetConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IceSheetFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IcicleClusterFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LostCavesSurfaceReplaceFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.NoisySphereReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.NoisySphereReplaceFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.PillarRockFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.PricklyPeachCactusPatchFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.ThreeLayerNoisySphereReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.ThreeLayerNoisySphereReplaceFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.WaterSurfaceIceFragmentFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class FeatureModule {
    @AutoRegister("large_icicle")
    public static final Feature<LargeIceDripstoneConfiguration> LARGE_ICICLE = new LargeIceDripstoneFeature(LargeIceDripstoneConfiguration.CODEC);

    @AutoRegister("icicle_cluster")
    public static final Feature<DripstoneClusterConfiguration> ICICLE_CLUSTER = new IcicleClusterFeature(DripstoneClusterConfiguration.CODEC);

    @AutoRegister("sphere_replace")
    public static final Feature<NoisySphereReplaceConfig> SPHERE_REPLACE = new NoisySphereReplaceFeature(NoisySphereReplaceConfig.CODEC);

    @AutoRegister("cactus_patch")
    public static final Feature<NoneFeatureConfiguration> CACTUS_PATCH = new CactusPatchFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("prickly_peach_cactus_patch")
    public static final Feature<NoneFeatureConfiguration> PRICKLY_PEACH_CACTUS_PATCH = new PricklyPeachCactusPatchFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("three_layer_sphere_replace")
    public static final Feature<ThreeLayerNoisySphereReplaceConfig> THREE_LAYER_SPHERE_REPLACE = new ThreeLayerNoisySphereReplaceFeature(ThreeLayerNoisySphereReplaceConfig.CODEC);

    @AutoRegister("floor_replace")
    public static final Feature<FloorReplaceConfig> FLOOR_REPLACE = new FloorReplaceFeature(FloorReplaceConfig.CODEC);

    @AutoRegister("ceiling_replace")
    public static final Feature<CeilingReplaceConfig> CEILING_REPLACE = new CeilingReplaceFeature(CeilingReplaceConfig.CODEC);

    @AutoRegister("water_surface_ice_fragment")
    public static final Feature<NoneFeatureConfiguration> WATER_SURFACE_ICE_FRAGMENT = new WaterSurfaceIceFragmentFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("pillar_rock")
    public static final Feature<SimpleBlockConfiguration> PILLAR_ROCK = new PillarRockFeature(SimpleBlockConfiguration.CODEC);

    @AutoRegister("lost_caves_surface_replace")
    public static final Feature<NoneFeatureConfiguration> LOST_CAVES_SURFACE_REPLACE = new LostCavesSurfaceReplaceFeature(NoneFeatureConfiguration.CODEC);

    @AutoRegister("ice_sheet_replace")
    public static final Feature<IceSheetConfiguration> ICE_SHEET_REPLACE = new IceSheetFeature(IceSheetConfiguration.CODEC);
}
