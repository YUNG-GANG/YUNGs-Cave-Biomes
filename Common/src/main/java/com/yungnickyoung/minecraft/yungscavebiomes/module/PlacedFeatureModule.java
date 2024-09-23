package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
//import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterPlacedFeature;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

import java.util.List;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class PlacedFeatureModule {
//    @AutoRegister("large_icicle")
//    public static final AutoRegisterPlacedFeature LARGE_ICICLE = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.LARGE_ICICLE,
//            List.of(
//                    CountPlacement.of(UniformInt.of(21, 48)),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    BiomeFilter.biome()));
//
//    @AutoRegister("large_icicle_tilted")
//    public static final AutoRegisterPlacedFeature TILTED_ICICLE = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.TILTED_ICICLE,
//            List.of(
//                    CountPlacement.of(UniformInt.of(21, 48)),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    BiomeFilter.biome()));
//
//    @AutoRegister("small_icicle")
//    public static final AutoRegisterPlacedFeature SMALL_ICICLE = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.SMALL_ICICLE,
//            List.of(
//                    CountPlacement.of(UniformInt.of(21, 48)),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    BiomeFilter.biome()));
//
//    @AutoRegister("frost_lily")
//    public static final AutoRegisterPlacedFeature FROST_LILY = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.FROST_LILY,
//            List.of(
//                    CountPlacement.of(20),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN,
//                            BlockPredicate.anyOf(
//                                    BlockPredicate.matchesTag(BlockTags.ICE),
//                                    BlockPredicate.matchesBlocks(List.of(Blocks.SNOW_BLOCK))),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("ice_patch")
//    public static final AutoRegisterPlacedFeature ICE_PATCH = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.ICE_PATCH,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("ice_patch_ceiling")
//    public static final AutoRegisterPlacedFeature ICE_PATCH_CEILING = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.ICE_PATCH_CEILING,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("ice_sheet_replace")
//    public static final AutoRegisterPlacedFeature ICE_SHEET_REPLACE = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.ICE_SHEET_REPLACE,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("ice_sheet_replace_2")
//    public static final AutoRegisterPlacedFeature ICE_SHEET_REPLACE_2 = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.ICE_SHEET_REPLACE,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("icicles")
//    public static final AutoRegisterPlacedFeature ICICLES = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.ICICLES,
//            List.of(
//                    CountPlacement.of(UniformInt.of(144, 216)),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    BiomeFilter.biome()));
//
//    @AutoRegister("water_surface_ice_fragment")
//    public static final AutoRegisterPlacedFeature WATER_SURFACE_ICE_FRAGMENT = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("water_surface_ice_fragment_2")
//    public static final AutoRegisterPlacedFeature WATER_SURFACE_ICE_FRAGMENT_2 = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("sandstone_glow_lichen")
//    public static final AutoRegisterPlacedFeature SANDSTONE_GLOW_LICHEN = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.SANDSTONE_GLOW_LICHEN,
//            List.of(
//                    CountPlacement.of(UniformInt.of(104, 157)),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    InSquarePlacement.spread(),
//                    SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("lost_caves_surface_replace")
//    public static final AutoRegisterPlacedFeature LOST_CAVES_SURFACE_REPLACE = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.LOST_CAVES_SURFACE_REPLACE,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("lost_caves_surface_replace_2")
//    public static final AutoRegisterPlacedFeature LOST_CAVES_SURFACE_REPLACE_2 = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.LOST_CAVES_SURFACE_REPLACE,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("cactus_patch")
//    public static final AutoRegisterPlacedFeature CACTUS_PATCH = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.CACTUS_PATCH,
//            List.of(
//                    CountPlacement.of(100),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("prickly_peach_cactus_patch")
//    public static final AutoRegisterPlacedFeature PRICKLY_PEACH_CACTUS_PATCH = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.PRICKLY_PEACH_CACTUS_PATCH,
//            List.of(
//                    CountPlacement.of(30),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("brittle_sandstone_ceiling_patch")
//    public static final AutoRegisterPlacedFeature BRITTLE_SANDSTONE_CEILING_PATCH = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.BRITTLE_SANDSTONE_CEILING_PATCH,
//            List.of(
//                    CountPlacement.of(150),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("dead_bush_spread")
//    public static final AutoRegisterPlacedFeature DEAD_BUSH_SPREAD = AutoRegisterPlacedFeature.of(
//            Holder.hackyErase(VegetationFeatures.PATCH_DEAD_BUSH),
//            List.of(
//                    CountPlacement.of(150),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("prickly_vines")
//    public static final AutoRegisterPlacedFeature PRICKLY_VINES = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.PRICKLY_VINE,
//            List.of(
//                    CountPlacement.of(120),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 6),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
//                    BiomeFilter.biome()));
//
//    @AutoRegister("layered_sandstone_pillar")
//    public static final AutoRegisterPlacedFeature LAYERED_SANDSTONE_PILLAR = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.LAYERED_SANDSTONE_PILLAR,
//            List.of(
//                    CountPlacement.of(15),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
//                    BiomeFilter.biome()));
//
////    @AutoRegister("marble_cave_water_pool")
//    public static final AutoRegisterPlacedFeature MARBLE_CAVE_WATER_POOL = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.MARBLE_CAVE_WATER_POOL,
//            List.of(
//                    CountPlacement.of(75),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
////    @AutoRegister("marble_water_spring")
//    public static final AutoRegisterPlacedFeature MARBLE_WATER_SPRING = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.MARBLE_WATER_SPRING,
//            List.of(
//                    CountPlacement.of(80),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_10_10,
//                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    BiomeFilter.biome()));
//
////    @AutoRegister("marble_patch")
//    public static final AutoRegisterPlacedFeature MARBLE_PATCH = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.MARBLE_PATCH,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
//                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
//                    BiomeFilter.biome()));
//
////    @AutoRegister("travertine_patch")
//    public static final AutoRegisterPlacedFeature TRAVERTINE_PATCH = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.TRAVERTINE_PATCH,
//            List.of(
//                    CountPlacement.of(250),
//                    InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
//                    RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
//                    BiomeFilter.biome()));
//
////    @AutoRegister("marble_glow_lichen")
//    public static final AutoRegisterPlacedFeature MARBLE_GLOW_LICHEN = AutoRegisterPlacedFeature.of(
//            ConfiguredFeatureModule.MARBLE_GLOW_LICHEN,
//            List.of(
//                    CountPlacement.of(UniformInt.of(104, 157)),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    InSquarePlacement.spread(),
//                    SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
//                    BiomeFilter.biome()));
}
