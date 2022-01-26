package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.google.common.collect.ImmutableSet;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluids;

public class YCBModConfiguredFeatures {
    public static ConfiguredFeature<LargeDripstoneConfiguration, ?> LARGE_ICICLE = YCBModFeatures.LARGE_ICICLE.configured(
            new LargeDripstoneConfiguration(
                    30,
                    UniformInt.of(4, 19),
                    UniformFloat.of(0.4F, 2.0F),
                    0.45F, // Vanilla is 0.33F, but we want bigger!
                    UniformFloat.of(0.3F, 0.9F),
                    UniformFloat.of(0.4F, 1.0F),
                    UniformFloat.of(0.0F, 0.3F),
                    4,
                    0.6F
            ));

    public static final ConfiguredFeature<SimpleBlockConfiguration, ?> FROST_LILY = Feature.SIMPLE_BLOCK.configured(
            new SimpleBlockConfiguration(BlockStateProvider.simple(YCBModBlocks.FROST_LILY))
    );

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> NO_OP = Feature.NO_OP.configured(
            new NoneFeatureConfiguration()
    );

    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH = Feature.VEGETATION_PATCH.configured(
            new VegetationPatchConfiguration(
                    BlockTags.MOSS_REPLACEABLE.getName(),
                    BlockStateProvider.simple(Blocks.PACKED_ICE),
                    NO_OP::placed, CaveSurface.FLOOR,
                    UniformInt.of(3, 4),
                    0.0F,
                    5,
                    0.8F,
                    UniformInt.of(4, 7),
                    0.3F)
    );

    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH_CEILING = Feature.VEGETATION_PATCH.configured(
            new VegetationPatchConfiguration(
                    BlockTags.MOSS_REPLACEABLE.getName(),
                    BlockStateProvider.simple(Blocks.PACKED_ICE),
                    NO_OP::placed, CaveSurface.CEILING,
                    UniformInt.of(3, 4),
                    0.0F,
                    5,
                    0.8F,
                    UniformInt.of(4, 7),
                    0.3F)
    );

    public static final ConfiguredFeature<DripstoneClusterConfiguration, ?> ICICLES = YCBModFeatures.ICICLE_CLUSTER.configured(
            new DripstoneClusterConfiguration(
                    12,
                    UniformInt.of(3, 6),
                    UniformInt.of(2, 8),
                    1,
                    3,
                    UniformInt.of(2, 4),
                    UniformFloat.of(0.3F, 0.7F),
                    ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F),
                    0.1F,
                    3,
                    8));

    public static final ConfiguredFeature<?, ?> MARBLE_CAVE_WATER_POOL = Feature.WATERLOGGED_VEGETATION_PATCH.configured(
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE.getName(),
                    BlockStateProvider.simple(Blocks.DIORITE),
                    () -> NO_OP.placed(),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    5,
                    0.1F,
                    UniformInt.of(4, 7),
                    0.7F));

    public static final ConfiguredFeature<SpringConfiguration, ?> SPRING_MARBLE_WATER =
            Feature.SPRING.configured(
                    new SpringConfiguration(
                            Fluids.WATER.defaultFluidState(), false, 4, 1,
                            ImmutableSet.of(Blocks.STONE, Blocks.DIORITE)
                    )
            );


    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("frost_lily", FROST_LILY);
        register("no_op", NO_OP);
        register("ice_patch", ICE_PATCH);
        register("ice_patch_ceiling", ICE_PATCH_CEILING);
        register("icicles", ICICLES);
        register("marble_cave_water_pool", MARBLE_CAVE_WATER_POOL);
        register("spring_marble_water", SPRING_MARBLE_WATER);
    }

    private static void register(String name, ConfiguredFeature<?, ?> obj) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), obj);
    }
}
