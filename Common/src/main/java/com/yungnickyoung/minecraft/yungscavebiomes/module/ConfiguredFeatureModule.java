package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.google.common.collect.ImmutableList;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterConfiguredFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IceSheetConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.MultisurfaceSphereReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.SphereReplaceConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

//@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class ConfiguredFeatureModule {
    //@AutoRegister("large_icicle")
//    public static final AutoRegisterConfiguredFeature<LargeIceDripstoneConfiguration, ?> LARGE_ICICLE = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.LARGE_ICICLE,
//                    new LargeIceDripstoneConfiguration(
//                            30,
//                            UniformInt.of(4, 19),
//                            UniformFloat.of(0.4F, 2.0F),
//                            0.45F, // Vanilla is 0.33F, but we want bigger!
//                            UniformFloat.of(0.3F, 0.9F),
//                            UniformFloat.of(0.4F, 1.0F),
//                            UniformFloat.of(0.0F, 0.3F),
//                            4,
//                            0.6F,
//                            UniformFloat.of(0.0F, (float) (Math.PI * 2)),
//                            0.4f
//                    )));
    public static final ConfiguredFeature<LargeIceDripstoneConfiguration, ?> LARGE_ICICLE = register("large_icicle",
            new ConfiguredFeature<>(
                    FeatureModule.LARGE_ICICLE,
                    new LargeIceDripstoneConfiguration(
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
                    )));

    //@AutoRegister("large_icicle_tilted")
//    public static final AutoRegisterConfiguredFeature<LargeIceDripstoneConfiguration, ?> TILTED_ICICLE = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.LARGE_ICICLE,
//                    new LargeIceDripstoneConfiguration(
//                            30,
//                            UniformInt.of(4, 19),
//                            UniformFloat.of(0.4F, 2.0F),
//                            0.45F,
//                            UniformFloat.of(0.6F, 0.9F),
//                            UniformFloat.of(0.6F, 1.0F),
//                            UniformFloat.of(0.3F, 0.6F),
//                            8,
//                            0.5F,
//                            UniformFloat.of(0.0F, (float) (Math.PI * 2)),
//                            0.4f
//                    )));
    public static final ConfiguredFeature<LargeIceDripstoneConfiguration, ?> TILTED_ICICLE = register("large_icicle_tilted",
            new ConfiguredFeature<>(
                    FeatureModule.LARGE_ICICLE,
                    new LargeIceDripstoneConfiguration(
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
                    )));

    //@AutoRegister("small_icicle")
//    public static final AutoRegisterConfiguredFeature<LargeIceDripstoneConfiguration, ?> SMALL_ICICLE = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.LARGE_ICICLE,
//                    new LargeIceDripstoneConfiguration(
//                            30,
//                            UniformInt.of(4, 5),
//                            UniformFloat.of(0.6F, 0.61F),
//                            0.8F,
//                            UniformFloat.of(0.6F, 1.0F),
//                            UniformFloat.of(0.6F, 1.0F),
//                            UniformFloat.of(0.0F, 0.3F),
//                            4,
//                            0.4F,
//                            UniformFloat.of(0.0F, (float) (Math.PI * 2)),
//                            0.1f
//                    )));
    public static final ConfiguredFeature<LargeIceDripstoneConfiguration, ?> SMALL_ICICLE = register("small_icicle",
            new ConfiguredFeature<>(
                    FeatureModule.LARGE_ICICLE,
                    new LargeIceDripstoneConfiguration(
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
                    )));

    //@AutoRegister("frost_lily")
//    public static final AutoRegisterConfiguredFeature<SimpleBlockConfiguration, ?> FROST_LILY = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.SIMPLE_BLOCK,
//                    new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.FROST_LILY.get()))));
    public static final ConfiguredFeature<SimpleBlockConfiguration, ?> FROST_LILY = register("frost_lily",
            new ConfiguredFeature<>(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.FROST_LILY.get()))));

    //@AutoRegister("no_op")
//    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration, ?> NO_OP = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.NO_OP,
//                    new NoneFeatureConfiguration()));
    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> NO_OP = register("no_op",
            new ConfiguredFeature<>(
                    Feature.NO_OP,
                    new NoneFeatureConfiguration()));

    //@AutoRegister("ice_patch")
//    public static final AutoRegisterConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.VEGETATION_PATCH,
//                    new VegetationPatchConfiguration(
//                            BlockTags.MOSS_REPLACEABLE,
//                            BlockStateProvider.simple(Blocks.PACKED_ICE),
//                            PlacementUtils.inlinePlaced(Holder.direct(NO_OP.get())),
//                            CaveSurface.FLOOR,
//                            UniformInt.of(3, 4),
//                            0.0F,
//                            5,
//                            0.8F,
//                            UniformInt.of(4, 7),
//                            0.3F)));
    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH = register("ice_patch",
            new ConfiguredFeature<>(
                    Feature.VEGETATION_PATCH,
                    new VegetationPatchConfiguration(
                            BlockTags.MOSS_REPLACEABLE,
                            BlockStateProvider.simple(Blocks.PACKED_ICE),
                            PlacementUtils.inlinePlaced(Holder.direct(NO_OP)),
                            CaveSurface.FLOOR,
                            UniformInt.of(3, 4),
                            0.0F,
                            5,
                            0.8F,
                            UniformInt.of(4, 7),
                            0.3F)));

    //@AutoRegister("ice_patch_ceiling")
//    public static final AutoRegisterConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH_CEILING = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.VEGETATION_PATCH,
//                    new VegetationPatchConfiguration(
//                            BlockTags.MOSS_REPLACEABLE,
//                            BlockStateProvider.simple(Blocks.PACKED_ICE),
//                            PlacementUtils.inlinePlaced(Holder.direct(NO_OP.get())),
//                            CaveSurface.CEILING,
//                            UniformInt.of(3, 4),
//                            0.0F,
//                            5,
//                            0.8F,
//                            UniformInt.of(4, 7),
//                            0.3F)));
    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH_CEILING = register("ice_patch_ceiling",
            new ConfiguredFeature<>(
                    Feature.VEGETATION_PATCH,
                    new VegetationPatchConfiguration(
                            BlockTags.MOSS_REPLACEABLE,
                            BlockStateProvider.simple(Blocks.PACKED_ICE),
                            PlacementUtils.inlinePlaced(Holder.direct(NO_OP)),
                            CaveSurface.CEILING,
                            UniformInt.of(3, 4),
                            0.0F,
                            5,
                            0.8F,
                            UniformInt.of(4, 7),
                            0.3F)));

    //@AutoRegister("ice_sheet_replace")
//    public static final AutoRegisterConfiguredFeature<IceSheetConfiguration, ?> ICE_SHEET_REPLACE = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.ICE_SHEET_REPLACE,
//                    new IceSheetConfiguration(8)));
    public static final ConfiguredFeature<IceSheetConfiguration, ?> ICE_SHEET_REPLACE = register("ice_sheet_replace",
            new ConfiguredFeature<>(
                    FeatureModule.ICE_SHEET_REPLACE,
                    new IceSheetConfiguration(8)));

    //@AutoRegister("icicles")
//    public static final AutoRegisterConfiguredFeature<DripstoneClusterConfiguration, ?> ICICLES = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.ICICLE_CLUSTER,
//                    new DripstoneClusterConfiguration(
//                            12,
//                            UniformInt.of(3, 6),
//                            UniformInt.of(2, 8),
//                            1,
//                            3,
//                            UniformInt.of(2, 4),
//                            UniformFloat.of(0.5F, 0.9F),
//                            ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F),
//                            0.1F,
//                            3,
//                            8)));
    public static final ConfiguredFeature<DripstoneClusterConfiguration, ?> ICICLES = register("icicles",
            new ConfiguredFeature<>(
                    FeatureModule.ICICLE_CLUSTER,
                    new DripstoneClusterConfiguration(
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
                            8)));

    //@AutoRegister("water_surface_ice_fragment")
//    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration, ?> WATER_SURFACE_ICE_FRAGMENT = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.WATER_SURFACE_ICE_FRAGMENT,
//                    NoneFeatureConfiguration.INSTANCE));
    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> WATER_SURFACE_ICE_FRAGMENT = register("water_surface_ice_fragment",
            new ConfiguredFeature<>(
                    FeatureModule.WATER_SURFACE_ICE_FRAGMENT,
                    NoneFeatureConfiguration.INSTANCE));

    //@AutoRegister("marble_cave_water_pool")
//    public static final AutoRegisterConfiguredFeature<?, ?> MARBLE_CAVE_WATER_POOL = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.WATERLOGGED_VEGETATION_PATCH,
//                    new VegetationPatchConfiguration(
//                            BlockTags.LUSH_GROUND_REPLACEABLE,
//                            BlockStateProvider.simple(BlockModule.TRAVERTINE.get()),
//                            PlacementUtils.inlinePlaced(Holder.direct(NO_OP.get())),
//                            CaveSurface.FLOOR,
//                            ConstantInt.of(3),
//                            0.8F,
//                            5,
//                            0.1F,
//                            UniformInt.of(4, 7),
//                            0.7F)));
    public static final ConfiguredFeature<?, ?> MARBLE_CAVE_WATER_POOL = register("marble_cave_water_pool",
            new ConfiguredFeature<>(
                    Feature.WATERLOGGED_VEGETATION_PATCH,
                    new VegetationPatchConfiguration(
                            BlockTags.LUSH_GROUND_REPLACEABLE,
                            BlockStateProvider.simple(BlockModule.TRAVERTINE.get()),
                            PlacementUtils.inlinePlaced(Holder.direct(NO_OP)),
                            CaveSurface.FLOOR,
                            ConstantInt.of(3),
                            0.8F,
                            5,
                            0.1F,
                            UniformInt.of(4, 7),
                            0.7F)));

    //@AutoRegister("marble_water_spring")
//    public static final AutoRegisterConfiguredFeature<SpringConfiguration, ?> MARBLE_WATER_SPRING = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.SPRING,
//                    new SpringConfiguration(
//                            Fluids.WATER.defaultFluidState(), false, 4, 1,
//                            HolderSet.direct(
//                                    Block::builtInRegistryHolder,
//                                    Blocks.STONE,
//                                    BlockModule.TRAVERTINE.get(),
//                                    BlockModule.MARBLE.get()))));
    public static final ConfiguredFeature<SpringConfiguration, ?> MARBLE_WATER_SPRING = register("marble_water_spring",
            new ConfiguredFeature<>(
                    Feature.SPRING,
                    new SpringConfiguration(
                            Fluids.WATER.defaultFluidState(), false, 4, 1,
                            HolderSet.direct(
                                    Block::builtInRegistryHolder,
                                    Blocks.STONE,
                                    BlockModule.TRAVERTINE.get(),
                                    BlockModule.MARBLE.get()))));

    //@AutoRegister("marble_patch")
//    public static final AutoRegisterConfiguredFeature<SphereReplaceConfig, ?> MARBLE_PATCH = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.SPHERE_REPLACE,
//                    new SphereReplaceConfig(
//                            ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
//                            BlockModule.MARBLE.get().defaultBlockState(),
//                            7)));
    public static final ConfiguredFeature<SphereReplaceConfig, ?> MARBLE_PATCH = register("marble_patch",
            new ConfiguredFeature<>(
                    FeatureModule.SPHERE_REPLACE,
                    new SphereReplaceConfig(
                            ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                            BlockModule.MARBLE.get().defaultBlockState(),
                            7)));

    //@AutoRegister("travertine_patch")
//    public static final AutoRegisterConfiguredFeature<SphereReplaceConfig, ?> TRAVERTINE_PATCH = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.SPHERE_REPLACE,
//                    new SphereReplaceConfig(
//                            ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
//                            BlockModule.TRAVERTINE.get().defaultBlockState(),
//                            7)));
    public static final ConfiguredFeature<SphereReplaceConfig, ?> TRAVERTINE_PATCH = register("travertine_patch",
            new ConfiguredFeature<>(
                    FeatureModule.SPHERE_REPLACE,
                    new SphereReplaceConfig(
                            ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                            BlockModule.TRAVERTINE.get().defaultBlockState(),
                            7)));

    //@AutoRegister("sandstone_patch")
//    public static final AutoRegisterConfiguredFeature<MultisurfaceSphereReplaceConfig, ?> SANDSTONE_PATCH = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.MULTISURFACE_SPHERE_REPLACE,
//                    new MultisurfaceSphereReplaceConfig(
//                            ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
//                            BlockModule.ANCIENT_SAND.get().defaultBlockState(),
//                            BlockModule.LAYERED_ANCIENT_SANDSTONE.get().defaultBlockState(),
//                            BlockModule.ANCIENT_SANDSTONE.get().defaultBlockState(),
//                            12)));
    public static final ConfiguredFeature<MultisurfaceSphereReplaceConfig, ?> SANDSTONE_PATCH = register("sandstone_patch",
            new ConfiguredFeature<>(
                    FeatureModule.MULTISURFACE_SPHERE_REPLACE,
                    new MultisurfaceSphereReplaceConfig(
                            ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                            BlockModule.ANCIENT_SAND.get().defaultBlockState(),
                            BlockModule.LAYERED_ANCIENT_SANDSTONE.get().defaultBlockState(),
                            BlockModule.ANCIENT_SANDSTONE.get().defaultBlockState(),
                            12)));

    //@AutoRegister("marble_glow_lichen")
//    public static final AutoRegisterConfiguredFeature<GlowLichenConfiguration, ?> MARBLE_GLOW_LICHEN = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.GLOW_LICHEN,
//                    new GlowLichenConfiguration(
//                            20, false, true, true, 0.5F,
//                            HolderSet.direct(
//                                    Block::builtInRegistryHolder,
//                                    BlockModule.TRAVERTINE.get(),
//                                    BlockModule.MARBLE.get()))));
    public static final ConfiguredFeature<GlowLichenConfiguration, ?> MARBLE_GLOW_LICHEN = register("marble_glow_lichen",
            new ConfiguredFeature<>(
                    Feature.GLOW_LICHEN,
                    new GlowLichenConfiguration(
                            20, false, true, true, 0.5F,
                            HolderSet.direct(
                                    Block::builtInRegistryHolder,
                                    BlockModule.TRAVERTINE.get(),
                                    BlockModule.MARBLE.get()))));

    //@AutoRegister("sandstone_glow_lichen")
//    public static final AutoRegisterConfiguredFeature<GlowLichenConfiguration, ?> SANDSTONE_GLOW_LICHEN = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.GLOW_LICHEN,
//                    new GlowLichenConfiguration(
//                            20, false, true, true, 0.5F,
//                            HolderSet.direct(
//                                    Block::builtInRegistryHolder,
//                                    BlockModule.ANCIENT_SAND.get(),
//                                    BlockModule.LAYERED_ANCIENT_SANDSTONE.get(),
//                                    BlockModule.ANCIENT_SANDSTONE.get()))));
    public static final ConfiguredFeature<GlowLichenConfiguration, ?> SANDSTONE_GLOW_LICHEN = register("sandstone_glow_lichen",
            new ConfiguredFeature<>(
                    Feature.GLOW_LICHEN,
                    new GlowLichenConfiguration(
                            20, false, true, true, 0.5F,
                            HolderSet.direct(
                                    Block::builtInRegistryHolder,
                                    BlockModule.ANCIENT_SAND.get(),
                                    BlockModule.LAYERED_ANCIENT_SANDSTONE.get(),
                                    BlockModule.ANCIENT_SANDSTONE.get()))));

    //@AutoRegister("cactus_patch")
//    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration, ?> CACTUS_PATCH = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.CACTUS_PATCH,
//                    NoneFeatureConfiguration.INSTANCE));
    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> CACTUS_PATCH = register("cactus_patch",
            new ConfiguredFeature<>(
                    FeatureModule.CACTUS_PATCH,
                    NoneFeatureConfiguration.INSTANCE));

    //@AutoRegister("prickly_pear_cactus_patch")
//    public static final AutoRegisterConfiguredFeature<NoneFeatureConfiguration, ?> PRICKLY_PEAR_CACTUS_PATCH = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.PRICKLY_PEAR_CACTUS_PATCH,
//                    NoneFeatureConfiguration.INSTANCE));
    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> PRICKLY_PEAR_CACTUS_PATCH = register("prickly_pear_cactus_patch",
            new ConfiguredFeature<>(
                    FeatureModule.PRICKLY_PEAR_CACTUS_PATCH,
                    NoneFeatureConfiguration.INSTANCE));

    //@AutoRegister("brittle_sandstone_replace")
//    public static final AutoRegisterConfiguredFeature<SphereReplaceConfig, ?> BRITTLE_SANDSTONE_REPLACE = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.SPHERE_REPLACE,
//                    new SphereReplaceConfig(
//                            ImmutableList.of(BlockModule.ANCIENT_SANDSTONE.get()),
//                            BlockModule.BRITTLE_ANCIENT_SANDSTONE.get().defaultBlockState(),
//                            7)));
    public static final ConfiguredFeature<SphereReplaceConfig, ?> BRITTLE_SANDSTONE_REPLACE = register("brittle_sandstone_replace",
            new ConfiguredFeature<>(
                    FeatureModule.SPHERE_REPLACE,
                    new SphereReplaceConfig(
                            ImmutableList.of(BlockModule.ANCIENT_SANDSTONE.get()),
                            BlockModule.BRITTLE_ANCIENT_SANDSTONE.get().defaultBlockState(),
                            7)));

    //@AutoRegister("prickly_vine")
//    public static final AutoRegisterConfiguredFeature<BlockColumnConfiguration, ?> PRICKLY_VINE = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    Feature.BLOCK_COLUMN,
//                    new BlockColumnConfiguration(
//                            List.of(
//                                    BlockColumnConfiguration.layer(
//                                            new WeightedListInt(
//                                                    SimpleWeightedRandomList.<IntProvider>builder()
//                                                            .add(UniformInt.of(0, 19), 2)
//                                                            .add(UniformInt.of(0, 2), 3)
//                                                            .add(UniformInt.of(0, 6), 10)
//                                                            .build()),
//                                            BlockStateProvider.simple(BlockModule.PRICKLY_VINES_PLANT.get())),
//                                    BlockColumnConfiguration.layer(
//                                            ConstantInt.of(1),
//                                            BlockStateProvider.simple(BlockModule.PRICKLY_VINES.get()))),
//                            Direction.DOWN,
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE,
//                            true)));
    public static final ConfiguredFeature<BlockColumnConfiguration, ?> PRICKLY_VINE = register("prickly_vine",
            new ConfiguredFeature<>(
                    Feature.BLOCK_COLUMN,
                    new BlockColumnConfiguration(
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
                            true)));


    //@AutoRegister("layered_sandstone_pillar")
//    public static final AutoRegisterConfiguredFeature<SimpleBlockConfiguration, ?> LAYERED_SANDSTONE_PILLAR = AutoRegisterConfiguredFeature.of(() ->
//            new ConfiguredFeature<>(
//                    FeatureModule.DISK_ROCK,
//                    new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.LAYERED_ANCIENT_SANDSTONE.get()))));
    public static final ConfiguredFeature<SimpleBlockConfiguration, ?> LAYERED_SANDSTONE_PILLAR = register("layered_sandstone_pillar",
            new ConfiguredFeature<>(
                    FeatureModule.DISK_ROCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.LAYERED_ANCIENT_SANDSTONE.get()))));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, key), feature);
    }
}
