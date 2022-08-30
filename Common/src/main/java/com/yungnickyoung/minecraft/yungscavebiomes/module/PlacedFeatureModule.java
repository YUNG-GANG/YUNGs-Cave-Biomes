package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatureModule {
    public static final PlacedFeature LARGE_ICICLE = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.LARGE_ICICLE),
            List.of(
                    CountPlacement.of(UniformInt.of(21, 48)),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    BiomeFilter.biome()));

    public static final PlacedFeature TILTED_ICICLE = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.TILTED_ICICLE),
            List.of(
                    CountPlacement.of(UniformInt.of(21, 48)),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    BiomeFilter.biome()));

    public static final PlacedFeature SMALL_ICICLE = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.SMALL_ICICLE),
            List.of(
                    CountPlacement.of(UniformInt.of(21, 48)),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    BiomeFilter.biome()));

    public static final PlacedFeature FROST_LILY = new PlacedFeature(
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
                    BiomeFilter.biome()));

    public static final PlacedFeature ICE_PATCH = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.ICE_PATCH),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature ICE_PATCH_CEILING = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.ICE_PATCH_CEILING),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature ICE_SHEET_REPLACE = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.ICE_SHEET_REPLACE),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature ICE_SHEET_REPLACE_2 = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.ICE_SHEET_REPLACE),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature ICICLES = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.ICICLES),
            List.of(
                    CountPlacement.of(UniformInt.of(144, 216)),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    BiomeFilter.biome()));

    public static final PlacedFeature WATER_SURFACE_ICE_FRAGMENT = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                    BiomeFilter.biome()));

    public static final PlacedFeature WATER_SURFACE_ICE_FRAGMENT2 = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.WATER_SURFACE_ICE_FRAGMENT),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(List.of(Blocks.WATER, Blocks.ICE)),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                    BiomeFilter.biome()));

    public static final PlacedFeature MARBLE_WATER_POOL = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.MARBLE_CAVE_WATER_POOL),
            List.of(
                    CountPlacement.of(75),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature MARBLE_WATER_SPRING = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.SPRING_MARBLE_WATER),
            List.of(
                    CountPlacement.of(80),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_10_10,
                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    BiomeFilter.biome()));

    public static final PlacedFeature MARBLE_PATCH = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.MARBLE_PATCH),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature TRAVERTINE_PATCH = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.TRAVERTINE_PATCH),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature MARBLE_GLOW_LICHEN = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.MARBLE_GLOW_LICHEN),
            List.of(
                    CountPlacement.of(UniformInt.of(104, 157)),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    InSquarePlacement.spread(),
                    SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
                    BiomeFilter.biome()));

    public static final PlacedFeature SANDSTONE_GLOW_LICHEN = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.SANDSTONE_GLOW_LICHEN),
            List.of(
                    CountPlacement.of(UniformInt.of(104, 157)),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    InSquarePlacement.spread(),
                    SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
                    BiomeFilter.biome()));

    public static final PlacedFeature SANDSTONE_PATCH = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.SANDSTONE_PATCH),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    // Needed twice to make sure everything is covered
    public static final PlacedFeature SANDSTONE_PATCH2 = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.SANDSTONE_PATCH),
            List.of(
                    CountPlacement.of(250),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature CACTUS_PATCH = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.CACTUS_PATCH),
            List.of(
                    CountPlacement.of(100),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                    BiomeFilter.biome()));

    public static final PlacedFeature PRICKLY_PEAR_CACTUS_PATCH = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.PRICKLY_PEAR_CACTUS_PATCH),
            List.of(
                    CountPlacement.of(50),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                    BiomeFilter.biome()));

    public static final PlacedFeature BRITTLE_SANDSTONE_REPLACE = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.BRITTLE_SANDSTONE_REPLACE),
            List.of(
                    CountPlacement.of(150),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature DEAD_BUSH_SPREAD = new PlacedFeature(
            Holder.hackyErase(VegetationFeatures.PATCH_DEAD_BUSH),
            List.of(
                    CountPlacement.of(150),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    BiomeFilter.biome()));

    public static final PlacedFeature PRICKLY_VINES = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.PRICKLY_VINE),
            List.of(
                    CountPlacement.of(120),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 6),
                    RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                    BiomeFilter.biome()));

    public static final PlacedFeature DISK_ROCK = new PlacedFeature(
            Holder.direct(ConfiguredFeatureModule.DISK_ROCK),
            List.of(
                    CountPlacement.of(15),
                    InSquarePlacement.spread(),
                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                    EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                            BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                    RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                    BiomeFilter.biome()));

//    public static final PlacedFeature NO_OP = new PlacedFeature(
//            Holder.direct(ConfiguredFeatureModule.NO_OP),
//            List.of());

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("large_icicle_tilted", TILTED_ICICLE);
        register("small_icicle", SMALL_ICICLE);
        register("frost_lily", FROST_LILY);
        register("ice_patch", ICE_PATCH);
        register("ice_patch_ceiling", ICE_PATCH_CEILING);
        register("ice_sheet_replace", ICE_SHEET_REPLACE);
        register("ice_sheet_replace_2", ICE_SHEET_REPLACE_2);
        register("icicles", ICICLES);
        register("water_surface_ice_fragment", WATER_SURFACE_ICE_FRAGMENT);
        register("water_surface_ice_fragment2", WATER_SURFACE_ICE_FRAGMENT2);
        register("marble_water_pool", MARBLE_WATER_POOL);
        register("marble_water_spring", MARBLE_WATER_SPRING);
        register("marble_patch", MARBLE_PATCH);
        register("travertine_patch", TRAVERTINE_PATCH);
        register("marble_glow_lichen", MARBLE_GLOW_LICHEN);
        register("sandstone_glow_lichen", SANDSTONE_GLOW_LICHEN);
        register("sandstone_patch", SANDSTONE_PATCH);
        register("sandstone_patch_2", SANDSTONE_PATCH2);
        register("cactus_patch", CACTUS_PATCH);
        register("prickly_pear_cactus_patch", PRICKLY_PEAR_CACTUS_PATCH);
        register("brittle_sandstone_replace", BRITTLE_SANDSTONE_REPLACE);
        register("dead_bush_spread", DEAD_BUSH_SPREAD);
        register("prickly_vines", PRICKLY_VINES);
        register("disk_rock", DISK_ROCK);
    }

    private static void register(String name, PlacedFeature feature) {
        Services.REGISTRY.registerPlacedFeature(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, name), feature);
    }
}
