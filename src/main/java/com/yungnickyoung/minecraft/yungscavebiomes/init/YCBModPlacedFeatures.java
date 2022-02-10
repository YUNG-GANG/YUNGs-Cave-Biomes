package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

public class YCBModPlacedFeatures {
    public static final PlacedFeature LARGE_ICICLE = YCBModConfiguredFeatures.LARGE_ICICLE.placed(
            CountPlacement.of(UniformInt.of(28, 64)),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            BiomeFilter.biome()
    );

    public static final PlacedFeature FROST_LILY = YCBModConfiguredFeatures.FROST_LILY.placed(
            CountPlacement.of(40),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN,
                    BlockPredicate.solid(),
                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome()
    );

    public static final PlacedFeature ICE_PATCH = YCBModConfiguredFeatures.ICE_PATCH.placed(
            CountPlacement.of(150),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
    );

    public static final PlacedFeature ICE_PATCH_CEILING = YCBModConfiguredFeatures.ICE_PATCH_CEILING.placed(
            CountPlacement.of(150),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
    );

    public static final PlacedFeature ICICLES = YCBModConfiguredFeatures.ICICLES.placed(
            CountPlacement.of(UniformInt.of(72, 144)),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            BiomeFilter.biome()
    );

    public static final PlacedFeature MARBLE_WATER_POOL = YCBModConfiguredFeatures.MARBLE_CAVE_WATER_POOL.placed(
            CountPlacement.of(75),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
    );

    public static final PlacedFeature MARBLE_WATER_SPRING = YCBModConfiguredFeatures.SPRING_MARBLE_WATER.placed(
            CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome()
    );

    public static final PlacedFeature MARBLE_PATCH = YCBModConfiguredFeatures.MARBLE_PATCH.placed(
            CountPlacement.of(150),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(),
                    BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
    );

    public static final PlacedFeature MARBLE_PATCH_CEILING = YCBModConfiguredFeatures.MARBLE_PATCH_CEILING.placed(
            CountPlacement.of(150),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
    );

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("frost_lily", FROST_LILY);
        register("ice_patch", ICE_PATCH);
        register("ice_patch_ceiling", ICE_PATCH_CEILING);
        register("icicles", ICICLES);
        register("marble_water_pool", MARBLE_WATER_POOL);
        register("marble_water_spring", MARBLE_WATER_SPRING);
        register("marble_patch", MARBLE_PATCH);
        register("marble_patch_ceiling", MARBLE_PATCH_CEILING);
    }

    private static void register(String name, PlacedFeature obj) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), obj);
    }
}
