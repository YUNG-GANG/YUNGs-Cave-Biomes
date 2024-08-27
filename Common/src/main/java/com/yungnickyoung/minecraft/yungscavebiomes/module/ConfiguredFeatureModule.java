package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.google.common.collect.ImmutableList;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterConfiguredFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.CeilingReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IceSheetConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.NoisySphereReplaceConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class ConfiguredFeatureModule {
    @AutoRegister("large_icicle")
    public static final AutoRegisterConfiguredFeature<LargeIceDripstoneConfiguration> LARGE_ICICLE = AutoRegisterConfiguredFeature.of(
            FeatureModule.LARGE_ICICLE,
            () -> new LargeIceDripstoneConfiguration(
                    30,
                    UniformInt.of(4, 19),
                    UniformFloat.of(0.4F, 2.0F),
                    0.45F, // Vanilla is 0.33F, but we want bigger!
                    UniformFloat.of(0.3F, 0.9F),
                    UniformFloat.of(0.4F, 1.0F),
                    UniformFloat.of(0.0F, 0.3F),
                    4,
                    0.6F,
                    UniformFloat.of(0.0F, (float) (Math.PI * 2)),
                    0.4f
            ));

    @AutoRegister("large_icicle_tilted")
    public static final AutoRegisterConfiguredFeature<LargeIceDripstoneConfiguration> TILTED_ICICLE = AutoRegisterConfiguredFeature.of(
            FeatureModule.LARGE_ICICLE,
            () -> new LargeIceDripstoneConfiguration(
                    30,
                    UniformInt.of(4, 19),
                    UniformFloat.of(0.4F, 2.0F),
                    0.45F,
                    UniformFloat.of(0.6F, 0.9F),
                    UniformFloat.of(0.6F, 1.0F),
                    UniformFloat.of(0.3F, 0.6F),
                    8,
                    0.5F,
                    UniformFloat.of(0.0F, (float) (Math.PI * 2)),
                    0.4f
            ));

    @AutoRegister("small_icicle")
    public static final AutoRegisterConfiguredFeature<LargeIceDripstoneConfiguration> SMALL_ICICLE = AutoRegisterConfiguredFeature.of(
            FeatureModule.LARGE_ICICLE,
            () -> new LargeIceDripstoneConfiguration(
                    30,
                    UniformInt.of(4, 5),
                    UniformFloat.of(0.6F, 0.61F),
                    0.8F,
                    UniformFloat.of(0.6F, 1.0F),
                    UniformFloat.of(0.6F, 1.0F),
                    UniformFloat.of(0.0F, 0.3F),
                    4,
                    0.4F,
                    UniformFloat.of(0.0F, (float) (Math.PI * 2)),
                    0.1f
            ));

    @AutoRegister("frost_lily")
    public static final AutoRegisterConfiguredFeature<SimpleBlockConfiguration> FROST_LILY = AutoRegisterConfiguredFeature.of(
            Feature.SIMPLE_BLOCK,
            () -> new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.FROST_LILY.get())));

    @AutoRegister("no_op")
    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration> NO_OP = AutoRegisterConfiguredFeature.of(
            Feature.NO_OP,
            () -> FeatureConfiguration.NONE);

    @AutoRegister("ice_patch")
    public static final AutoRegisterConfiguredFeature<VegetationPatchConfiguration> ICE_PATCH = AutoRegisterConfiguredFeature.of(
            Feature.VEGETATION_PATCH,
            () -> new VegetationPatchConfiguration(
                    BlockTags.MOSS_REPLACEABLE,
                    BlockStateProvider.simple(Blocks.PACKED_ICE),
                    PlacementUtils.inlinePlaced(NO_OP.holder()),
                    CaveSurface.FLOOR,
                    UniformInt.of(3, 4),
                    0.0F,
                    5,
                    0.8F,
                    UniformInt.of(4, 7),
                    0.3F));

    @AutoRegister("ice_patch_ceiling")
    public static final AutoRegisterConfiguredFeature<VegetationPatchConfiguration> ICE_PATCH_CEILING = AutoRegisterConfiguredFeature.of(
            Feature.VEGETATION_PATCH,
            () -> new VegetationPatchConfiguration(
                    BlockTags.MOSS_REPLACEABLE,
                    BlockStateProvider.simple(Blocks.PACKED_ICE),
                    PlacementUtils.inlinePlaced(NO_OP.holder()),
                    CaveSurface.CEILING,
                    UniformInt.of(3, 4),
                    0.0F,
                    5,
                    0.8F,
                    UniformInt.of(4, 7),
                    0.3F));

    @AutoRegister("ice_sheet_replace")
    public static final AutoRegisterConfiguredFeature<IceSheetConfiguration> ICE_SHEET_REPLACE = AutoRegisterConfiguredFeature.of(
            FeatureModule.ICE_SHEET_REPLACE,
            () -> new IceSheetConfiguration(8));

    @AutoRegister("icicles")
    public static final AutoRegisterConfiguredFeature<DripstoneClusterConfiguration> ICICLES = AutoRegisterConfiguredFeature.of(
            FeatureModule.ICICLE_CLUSTER,
            () -> new DripstoneClusterConfiguration(
                    12,
                    UniformInt.of(3, 6),
                    UniformInt.of(2, 8),
                    1,
                    3,
                    UniformInt.of(2, 4),
                    UniformFloat.of(0.5F, 0.9F),
                    ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F),
                    0.1F,
                    3,
                    8));

    @AutoRegister("water_surface_ice_fragment")
    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration> WATER_SURFACE_ICE_FRAGMENT = AutoRegisterConfiguredFeature.of(
            FeatureModule.WATER_SURFACE_ICE_FRAGMENT,
            () -> FeatureConfiguration.NONE);

    @AutoRegister("lost_caves_surface_replace")
    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration> LOST_CAVES_SURFACE_REPLACE = AutoRegisterConfiguredFeature.of(
            FeatureModule.LOST_CAVES_SURFACE_REPLACE,
            () -> FeatureConfiguration.NONE);

    /**
     * Replaces some of the ceiling layer exposed to air with brittle ancient sandstone.
     */
    @AutoRegister("brittle_sandstone_ceiling_patch")
    public static final AutoRegisterConfiguredFeature<CeilingReplaceConfig> BRITTLE_SANDSTONE_CEILING_PATCH = AutoRegisterConfiguredFeature.of(
            FeatureModule.CEILING_REPLACE,
            () -> new CeilingReplaceConfig(
                    ImmutableList.of(BlockModule.LAYERED_ANCIENT_SANDSTONE.get()),
                    BlockModule.BRITTLE_ANCIENT_SANDSTONE.get().defaultBlockState(),
                    1, 6, 10));

    @AutoRegister("sandstone_glow_lichen")
    public static final AutoRegisterConfiguredFeature<GlowLichenConfiguration> SANDSTONE_GLOW_LICHEN = AutoRegisterConfiguredFeature.of(
            Feature.GLOW_LICHEN,
            () -> new GlowLichenConfiguration(
                    20, false, true, true, 0.5F,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            BlockModule.ANCIENT_SAND.get(),
                            BlockModule.LAYERED_ANCIENT_SANDSTONE.get(),
                            BlockModule.ANCIENT_SANDSTONE.get())));

    @AutoRegister("cactus_patch")
    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration> CACTUS_PATCH = AutoRegisterConfiguredFeature.of(
            FeatureModule.CACTUS_PATCH,
            () -> FeatureConfiguration.NONE);

    @AutoRegister("prickly_peach_cactus_patch")
    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration> PRICKLY_PEACH_CACTUS_PATCH = AutoRegisterConfiguredFeature.of(
            FeatureModule.PRICKLY_PEACH_CACTUS_PATCH,
            () -> FeatureConfiguration.NONE);

    @AutoRegister("prickly_vine")
    public static final AutoRegisterConfiguredFeature<BlockColumnConfiguration> PRICKLY_VINE = AutoRegisterConfiguredFeature.of(
            Feature.BLOCK_COLUMN,
            () -> new BlockColumnConfiguration(
                    List.of(
                            BlockColumnConfiguration.layer(
                                    new WeightedListInt(
                                            SimpleWeightedRandomList.<IntProvider>builder()
                                                    .add(UniformInt.of(0, 19), 2)
                                                    .add(UniformInt.of(0, 2), 3)
                                                    .add(UniformInt.of(0, 6), 10)
                                                    .build()),
                                    BlockStateProvider.simple(BlockModule.PRICKLY_VINES_PLANT.get())),
                            BlockColumnConfiguration.layer(
                                    ConstantInt.of(1),
                                    BlockStateProvider.simple(BlockModule.PRICKLY_VINES.get()))),
                    Direction.DOWN,
                    BlockPredicate.ONLY_IN_AIR_PREDICATE,
                    true));

    @AutoRegister("layered_sandstone_pillar")
    public static final AutoRegisterConfiguredFeature<SimpleBlockConfiguration> LAYERED_SANDSTONE_PILLAR = AutoRegisterConfiguredFeature.of(
            FeatureModule.PILLAR_ROCK,
            () -> new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.LAYERED_ANCIENT_SANDSTONE.get())));

//    @AutoRegister("marble_cave_water_pool")
    public static final AutoRegisterConfiguredFeature<VegetationPatchConfiguration> MARBLE_CAVE_WATER_POOL = AutoRegisterConfiguredFeature.of(
            Feature.WATERLOGGED_VEGETATION_PATCH,
            () -> new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(BlockModule.TRAVERTINE.get()),
                    PlacementUtils.inlinePlaced(NO_OP.holder()),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    5,
                    0.1F,
                    UniformInt.of(4, 7),
                    0.7F));

//    @AutoRegister("marble_water_spring")
    public static final AutoRegisterConfiguredFeature<SpringConfiguration> MARBLE_WATER_SPRING = AutoRegisterConfiguredFeature.of(
            Feature.SPRING,
            () -> new SpringConfiguration(
                    Fluids.WATER.defaultFluidState(), false, 4, 1,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            Blocks.STONE,
                            BlockModule.TRAVERTINE.get(),
                            BlockModule.MARBLE.get())));

//    @AutoRegister("marble_patch")
    public static final AutoRegisterConfiguredFeature<NoisySphereReplaceConfig> MARBLE_PATCH = AutoRegisterConfiguredFeature.of(
            FeatureModule.SPHERE_REPLACE,
            () -> new NoisySphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    BlockModule.MARBLE.get().defaultBlockState(),
                    6, 10));

//    @AutoRegister("travertine_patch")
    public static final AutoRegisterConfiguredFeature<NoisySphereReplaceConfig> TRAVERTINE_PATCH = AutoRegisterConfiguredFeature.of(
            FeatureModule.SPHERE_REPLACE,
            () -> new NoisySphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    BlockModule.TRAVERTINE.get().defaultBlockState(),
                    6, 10));

//    @AutoRegister("marble_glow_lichen")
    public static final AutoRegisterConfiguredFeature<GlowLichenConfiguration> MARBLE_GLOW_LICHEN = AutoRegisterConfiguredFeature.of(
            Feature.GLOW_LICHEN,
            () -> new GlowLichenConfiguration(
                    20, false, true, true, 0.5F,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            BlockModule.TRAVERTINE.get(),
                            BlockModule.MARBLE.get())));
}
