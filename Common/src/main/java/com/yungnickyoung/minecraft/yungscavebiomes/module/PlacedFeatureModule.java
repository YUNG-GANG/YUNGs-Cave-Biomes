package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterPlacedFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

//@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class PlacedFeatureModule {
    //@AutoRegister("large_icicle")
//    public static final AutoRegisterPlacedFeature LARGE_ICICLE = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.LARGE_ICICLE),
//                    List.of(
//                            CountPlacement.of(UniformInt.of(21, 48)),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            BiomeFilter.biome())));
    public static final PlacedFeature LARGE_ICICLE = register("large_icicle",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.LARGE_ICICLE),
                    List.of(
                            CountPlacement.of(UniformInt.of(21, 48)),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            BiomeFilter.biome())));

    //@AutoRegister("large_icicle_tilted")
//    public static final AutoRegisterPlacedFeature TILTED_ICICLE = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.TILTED_ICICLE),
//                    List.of(
//                            CountPlacement.of(UniformInt.of(21, 48)),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            BiomeFilter.biome())));
    public static final PlacedFeature TILTED_ICICLE = register("large_icicle_tilted",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.TILTED_ICICLE),
                    List.of(
                            CountPlacement.of(UniformInt.of(21, 48)),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            BiomeFilter.biome())));

    //@AutoRegister("small_icicle")
//    public static final AutoRegisterPlacedFeature SMALL_ICICLE = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.SMALL_ICICLE),
//                    List.of(
//                            CountPlacement.of(UniformInt.of(21, 48)),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            BiomeFilter.biome())));
    public static final PlacedFeature SMALL_ICICLE = register("small_icicle",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.SMALL_ICICLE),
                    List.of(
                            CountPlacement.of(UniformInt.of(21, 48)),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            BiomeFilter.biome())));

    //@AutoRegister("frost_lily")
//    public static final AutoRegisterPlacedFeature FROST_LILY = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.FROST_LILY),
//                    List.of(
//                            CountPlacement.of(40),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN,
//                                    BlockPredicate.anyOf(
//                                            BlockPredicate.matchesTag(BlockTags.ICE),
//                                            BlockPredicate.matchesBlocks(List.of(Blocks.SNOW_BLOCK))),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature FROST_LILY = register("frost_lily",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.FROST_LILY),
                    List.of(
                            CountPlacement.of(40),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN,
                                    BlockPredicate.anyOf(
                                            BlockPredicate.matchesTag(BlockTags.ICE),
                                            BlockPredicate.matchesBlocks(List.of(Blocks.SNOW_BLOCK))),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("ice_patch")
//    public static final AutoRegisterPlacedFeature ICE_PATCH = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.ICE_PATCH),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature ICE_PATCH = register("ice_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.ICE_PATCH),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("ice_patch_ceiling")
//    public static final AutoRegisterPlacedFeature ICE_PATCH_CEILING = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.ICE_PATCH_CEILING),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature ICE_PATCH_CEILING = register("ice_patch_ceiling",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.ICE_PATCH_CEILING),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                            BiomeFilter.biome())));

    //@AutoRegister("ice_sheet_replace")
//    public static final AutoRegisterPlacedFeature ICE_SHEET_REPLACE = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.ICE_SHEET_REPLACE),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature ICE_SHEET_REPLACE = register("ice_sheet_replace",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.ICE_SHEET_REPLACE),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("ice_sheet_replace_2")
//    public static final AutoRegisterPlacedFeature ICE_SHEET_REPLACE_2 = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.ICE_SHEET_REPLACE),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature ICE_SHEET_REPLACE_2 = register("ice_sheet_replace_2",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.ICE_SHEET_REPLACE),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("icicles")
//    public static final AutoRegisterPlacedFeature ICICLES = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.ICICLES),
//                    List.of(
//                            CountPlacement.of(UniformInt.of(144, 216)),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            BiomeFilter.biome())));
    public static final PlacedFeature ICICLES = register("icicles",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.ICICLES),
                    List.of(
                            CountPlacement.of(UniformInt.of(144, 216)),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            BiomeFilter.biome())));

    //@AutoRegister("water_surface_ice_fragment")
//    public static final AutoRegisterPlacedFeature WATER_SURFACE_ICE_FRAGMENT = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature WATER_SURFACE_ICE_FRAGMENT = register("water_surface_ice_fragment",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                            BiomeFilter.biome())));

    //@AutoRegister("water_surface_ice_fragment_2")
//    public static final AutoRegisterPlacedFeature WATER_SURFACE_ICE_FRAGMENT2 = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature WATER_SURFACE_ICE_FRAGMENT2 = register("water_surface_ice_fragment_2",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                            BiomeFilter.biome())));

    //@AutoRegister("marble_cave_water_pool")
//    public static final AutoRegisterPlacedFeature MARBLE_CAVE_WATER_POOL = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.MARBLE_CAVE_WATER_POOL),
//                    List.of(
//                            CountPlacement.of(75),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature MARBLE_CAVE_WATER_POOL = register("marble_cave_water_pool",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.MARBLE_CAVE_WATER_POOL),
                    List.of(
                            CountPlacement.of(75),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("marble_water_spring")
//    public static final AutoRegisterPlacedFeature MARBLE_WATER_SPRING = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.MARBLE_WATER_SPRING),
//                    List.of(
//                            CountPlacement.of(80),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_10_10,
//                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            BiomeFilter.biome())));
    public static final PlacedFeature MARBLE_WATER_SPRING = register("marble_water_spring",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.MARBLE_WATER_SPRING),
                    List.of(
                            CountPlacement.of(80),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_10_10,
                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            BiomeFilter.biome())));

    //@AutoRegister("marble_patch")
//    public static final AutoRegisterPlacedFeature MARBLE_PATCH = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.MARBLE_PATCH),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature MARBLE_PATCH = register("marble_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.MARBLE_PATCH),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("travertine_patch")
//    public static final AutoRegisterPlacedFeature TRAVERTINE_PATCH = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.TRAVERTINE_PATCH),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature TRAVERTINE_PATCH = register("travertine_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.TRAVERTINE_PATCH),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                            BiomeFilter.biome())));

    //@AutoRegister("marble_glow_lichen")
//    public static final AutoRegisterPlacedFeature MARBLE_GLOW_LICHEN = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.MARBLE_GLOW_LICHEN),
//                    List.of(
//                            CountPlacement.of(UniformInt.of(104, 157)),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            InSquarePlacement.spread(),
//                            SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
//                            BiomeFilter.biome())));
    public static final PlacedFeature MARBLE_GLOW_LICHEN = register("marble_glow_lichen",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.MARBLE_GLOW_LICHEN),
                    List.of(
                            CountPlacement.of(UniformInt.of(104, 157)),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            InSquarePlacement.spread(),
                            SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
                            BiomeFilter.biome())));

    //@AutoRegister("sandstone_glow_lichen")
//    public static final AutoRegisterPlacedFeature SANDSTONE_GLOW_LICHEN = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.SANDSTONE_GLOW_LICHEN),
//                    List.of(
//                            CountPlacement.of(UniformInt.of(104, 157)),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            InSquarePlacement.spread(),
//                            SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
//                            BiomeFilter.biome())));
    public static final PlacedFeature SANDSTONE_GLOW_LICHEN = register("sandstone_glow_lichen",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.SANDSTONE_GLOW_LICHEN),
                    List.of(
                            CountPlacement.of(UniformInt.of(104, 157)),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            InSquarePlacement.spread(),
                            SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
                            BiomeFilter.biome())));

    //@AutoRegister("sandstone_patch")
//    public static final AutoRegisterPlacedFeature SANDSTONE_PATCH = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.SANDSTONE_PATCH),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature SANDSTONE_PATCH = register("sandstone_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.SANDSTONE_PATCH),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    // Needed twice to make sure everything is covered
    //@AutoRegister("sandstone_patch_2")
//    public static final AutoRegisterPlacedFeature SANDSTONE_PATCH2 = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.SANDSTONE_PATCH),
//                    List.of(
//                            CountPlacement.of(250),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature SANDSTONE_PATCH2 = register("sandstone_patch_2",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.SANDSTONE_PATCH),
                    List.of(
                            CountPlacement.of(250),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("cactus_patch")
//    public static final AutoRegisterPlacedFeature CACTUS_PATCH = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.CACTUS_PATCH),
//                    List.of(
//                            CountPlacement.of(100),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature CACTUS_PATCH = register("cactus_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.CACTUS_PATCH),
                    List.of(
                            CountPlacement.of(100),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                            BiomeFilter.biome())));

    //@AutoRegister("prickly_pear_cactus_patch")
//    public static final AutoRegisterPlacedFeature PRICKLY_PEAR_CACTUS_PATCH = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.PRICKLY_PEAR_CACTUS_PATCH),
//                    List.of(
//                            CountPlacement.of(50),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature PRICKLY_PEAR_CACTUS_PATCH = register("prickly_pear_cactus_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.PRICKLY_PEAR_CACTUS_PATCH),
                    List.of(
                            CountPlacement.of(50),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                            BiomeFilter.biome())));

    //@AutoRegister("brittle_sandstone_replace")
//    public static final AutoRegisterPlacedFeature BRITTLE_SANDSTONE_REPLACE = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.BRITTLE_SANDSTONE_REPLACE),
//                    List.of(
//                            CountPlacement.of(150),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature BRITTLE_SANDSTONE_REPLACE = register("brittle_sandstone_replace",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.BRITTLE_SANDSTONE_REPLACE),
                    List.of(
                            CountPlacement.of(150),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                            BiomeFilter.biome())));

    //@AutoRegister("dead_bush_spread")
//    public static final AutoRegisterPlacedFeature DEAD_BUSH_SPREAD = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.hackyErase(VegetationFeatures.PATCH_DEAD_BUSH),
//                    List.of(
//                            CountPlacement.of(150),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            BiomeFilter.biome())));
    public static final PlacedFeature DEAD_BUSH_SPREAD = register("dead_bush_spread",
            new PlacedFeature(
                    Holder.hackyErase(VegetationFeatures.PATCH_DEAD_BUSH),
                    List.of(
                            CountPlacement.of(150),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            BiomeFilter.biome())));

    //@AutoRegister("prickly_vines")
//    public static final AutoRegisterPlacedFeature PRICKLY_VINES = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.PRICKLY_VINE),
//                    List.of(
//                            CountPlacement.of(120),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 6),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature PRICKLY_VINES = register("prickly_vines",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.PRICKLY_VINE),
                    List.of(
                            CountPlacement.of(120),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 6),
                            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                            BiomeFilter.biome())));

    //@AutoRegister("layered_sandstone_pillar")
//    public static final AutoRegisterPlacedFeature LAYERED_SANDSTONE_PILLAR = AutoRegisterPlacedFeature.of(() ->
//            new PlacedFeature(
//                    Holder.direct(ConfiguredFeatureModule.LAYERED_SANDSTONE_PILLAR),
//                    List.of(
//                            CountPlacement.of(15),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                            BiomeFilter.biome())));
    public static final PlacedFeature LAYERED_SANDSTONE_PILLAR = register("layered_sandstone_pillar",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.LAYERED_SANDSTONE_PILLAR),
                    List.of(
                            CountPlacement.of(15),
                            InSquarePlacement.spread(),
                            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                            RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                            BiomeFilter.biome())));

    private static PlacedFeature register(String key, PlacedFeature feature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, key), feature);
    }
}
