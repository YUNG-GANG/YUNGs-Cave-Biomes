package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyVinesBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.MultisurfaceSphereReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.SphereReplaceConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class YCBModConfiguredFeatures {
    public static ConfiguredFeature<LargeIceDripstoneConfiguration, ?> LARGE_ICICLE = YCBModFeatures.LARGE_ICICLE.configured(
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
            ));

    public static ConfiguredFeature<LargeIceDripstoneConfiguration, ?> TILTED_ICICLE = YCBModFeatures.LARGE_ICICLE.configured(
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
            ));

    public static ConfiguredFeature<LargeIceDripstoneConfiguration, ?> SMALL_ICICLE = YCBModFeatures.LARGE_ICICLE.configured(
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
                    UniformFloat.of(0.5F, 0.9F),
                    ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F),
                    0.1F,
                    3,
                    8));

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> WATER_SURFACE_ICE_FRAGMENT = YCBModFeatures.WATER_SURFACE_ICE_FRAGMENT.configured(
            NoneFeatureConfiguration.INSTANCE
    );

    public static final ConfiguredFeature<?, ?> MARBLE_CAVE_WATER_POOL = Feature.WATERLOGGED_VEGETATION_PATCH.configured(
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE.getName(),
                    BlockStateProvider.simple(YCBModBlocks.TRAVERTINE),
                    NO_OP::placed,
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
                            ImmutableSet.of(Blocks.STONE, YCBModBlocks.TRAVERTINE, YCBModBlocks.MARBLE)
                    )
            );

    public static final ConfiguredFeature<SphereReplaceConfig, ?> MARBLE_PATCH = YCBModFeatures.SPHERE_REPLACE.configured(
            new SphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    YCBModBlocks.MARBLE.defaultBlockState(),
                    7
            )
    );

    public static final ConfiguredFeature<SphereReplaceConfig, ?> TRAVERTINE_PATCH = YCBModFeatures.SPHERE_REPLACE.configured(
            new SphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    YCBModBlocks.TRAVERTINE.defaultBlockState(),
                    7
            )
    );

    public static final ConfiguredFeature<MultisurfaceSphereReplaceConfig, ?> SANDSTONE_PATCH = YCBModFeatures.MULTISURFACE_SPHERE_REPLACE.configured(
            new MultisurfaceSphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    YCBModBlocks.ANCIENT_SAND.defaultBlockState(),
                    YCBModBlocks.LAYERED_ANCIENT_SANDSTONE.defaultBlockState(),
                    YCBModBlocks.ANCIENT_SANDSTONE.defaultBlockState(),
                    12
            )
    );

    public static final ConfiguredFeature<GlowLichenConfiguration, ?> MARBLE_GLOW_LICHEN = Feature.GLOW_LICHEN.configured(
            new GlowLichenConfiguration(
                    20, false, true, true, 0.5F,
                    List.of(YCBModBlocks.TRAVERTINE, YCBModBlocks.MARBLE)
            )
    );

    public static final ConfiguredFeature<GlowLichenConfiguration, ?> SANDSTONE_GLOW_LICHEN = Feature.GLOW_LICHEN.configured(
            new GlowLichenConfiguration(
                    20, false, true, true, 0.5F,
                    List.of(YCBModBlocks.ANCIENT_SAND, YCBModBlocks.LAYERED_ANCIENT_SANDSTONE, YCBModBlocks.ANCIENT_SANDSTONE)
            )
    );

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> CACTUS_PATCH = YCBModFeatures.CACTUS_PATCH.configured(
            NoneFeatureConfiguration.INSTANCE
    );

    public static final ConfiguredFeature<SphereReplaceConfig, ?> BRITTLE_SANDSTONE_REPLACE = YCBModFeatures.SPHERE_REPLACE.configured(
            new SphereReplaceConfig(
                    ImmutableList.of(YCBModBlocks.ANCIENT_SANDSTONE),
                    YCBModBlocks.BRITTLE_ANCIENT_SANDSTONE.defaultBlockState(),
                    7
            )
    );

    private static final WeightedStateProvider PRICKLY_VINES_BODY_PROVIDER = new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                    .add(YCBModBlocks.PRICKLY_VINES_PLANT.defaultBlockState(), 4)
                    .add(YCBModBlocks.PRICKLY_VINES_PLANT.defaultBlockState(), 1)
                    .build());

    private static final RandomizedIntStateProvider PRICKLY_VINES_HEAD_PROVIDER = new RandomizedIntStateProvider(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(YCBModBlocks.PRICKLY_VINES.defaultBlockState(), 4)
                    .add(YCBModBlocks.PRICKLY_VINES.defaultBlockState(), 1)),
            PricklyVinesBlock.AGE, UniformInt.of(23, 25));

    public static final ConfiguredFeature<BlockColumnConfiguration, ?> PRICKLY_VINE = Feature.BLOCK_COLUMN.configured(
            new BlockColumnConfiguration(
                    List.of(BlockColumnConfiguration.layer(
                            new WeightedListInt(
                                    SimpleWeightedRandomList.<IntProvider>builder()
                                            .add(UniformInt.of(0, 19), 2)
                                            .add(UniformInt.of(0, 2), 3)
                                            .add(UniformInt.of(0, 6), 10)
                                            .build()),
                                    PRICKLY_VINES_BODY_PROVIDER),
                            BlockColumnConfiguration.layer(ConstantInt.of(1), PRICKLY_VINES_HEAD_PROVIDER)),
                    Direction.DOWN,
                    BlockPredicate.ONLY_IN_AIR_PREDICATE,
                    true
            )
    );

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("large_icicle_tilted", TILTED_ICICLE);
        register("small_icicle", SMALL_ICICLE);
        register("frost_lily", FROST_LILY);
        register("no_op", NO_OP);
        register("ice_patch", ICE_PATCH);
        register("ice_patch_ceiling", ICE_PATCH_CEILING);
        register("icicles", ICICLES);
        register("water_surface_ice_fragment", WATER_SURFACE_ICE_FRAGMENT);
        register("marble_cave_water_pool", MARBLE_CAVE_WATER_POOL);
        register("spring_marble_water", SPRING_MARBLE_WATER);
        register("marble_patch", MARBLE_PATCH);
        register("travertine_patch", TRAVERTINE_PATCH);
        register("marble_glow_lichen", MARBLE_GLOW_LICHEN);
        register("sandstone_glow_lichen", SANDSTONE_GLOW_LICHEN);
        register("sandstone_patch", SANDSTONE_PATCH);
        register("brittle_sandstone_replace", BRITTLE_SANDSTONE_REPLACE);
        register("cactus_patch", CACTUS_PATCH);
        register("prickly_vine", PRICKLY_VINE);
    }

    private static void register(String name, ConfiguredFeature<?, ?> obj) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), obj);
    }
}
