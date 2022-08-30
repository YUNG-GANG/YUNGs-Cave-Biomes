package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.google.common.collect.ImmutableList;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyVinesBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.IceSheetConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.LargeIceDripstoneConfiguration;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.MultisurfaceSphereReplaceConfig;
import com.yungnickyoung.minecraft.yungscavebiomes.world.feature.SphereReplaceConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
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

public class ConfiguredFeatureModule {
    public static final ConfiguredFeature<LargeIceDripstoneConfiguration, ?> LARGE_ICICLE = new ConfiguredFeature<>(
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
            ));

    public static final ConfiguredFeature<LargeIceDripstoneConfiguration, ?> TILTED_ICICLE = new ConfiguredFeature<>(
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
            ));

    public static final ConfiguredFeature<LargeIceDripstoneConfiguration, ?> SMALL_ICICLE = new ConfiguredFeature<>(
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
            ));

    public static final ConfiguredFeature<SimpleBlockConfiguration, ?> FROST_LILY = new ConfiguredFeature<>(
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(BlockStateProvider.simple(BlockModule.FROST_LILY.get())));

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> NO_OP = new ConfiguredFeature<>(
            Feature.NO_OP,
            new NoneFeatureConfiguration());

    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH = new ConfiguredFeature<>(
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
                    0.3F));

    public static final ConfiguredFeature<VegetationPatchConfiguration, ?> ICE_PATCH_CEILING = new ConfiguredFeature<>(
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
                    0.3F));

    public static final ConfiguredFeature<IceSheetConfiguration, ?> ICE_SHEET_REPLACE = new ConfiguredFeature<>(
            FeatureModule.ICE_SHEET_REPLACE,
            new IceSheetConfiguration(8));

    public static final ConfiguredFeature<DripstoneClusterConfiguration, ?> ICICLES = new ConfiguredFeature<>(
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
                    8));

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> WATER_SURFACE_ICE_FRAGMENT = new ConfiguredFeature<>(
            FeatureModule.WATER_SURFACE_ICE_FRAGMENT,
            NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<?, ?> MARBLE_CAVE_WATER_POOL = new ConfiguredFeature<>(
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
                    0.7F));

    public static final ConfiguredFeature<SpringConfiguration, ?> SPRING_MARBLE_WATER = new ConfiguredFeature<>(
            Feature.SPRING,
            new SpringConfiguration(
                    Fluids.WATER.defaultFluidState(), false, 4, 1,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            Blocks.STONE,
                            BlockModule.TRAVERTINE.get(),
                            BlockModule.MARBLE.get())));

    public static final ConfiguredFeature<SphereReplaceConfig, ?> MARBLE_PATCH = new ConfiguredFeature<>(
            FeatureModule.SPHERE_REPLACE,
            new SphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    BlockModule.MARBLE.get().defaultBlockState(),
                    7));

    public static final ConfiguredFeature<SphereReplaceConfig, ?> TRAVERTINE_PATCH = new ConfiguredFeature<>(
            FeatureModule.SPHERE_REPLACE,
            new SphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    BlockModule.TRAVERTINE.get().defaultBlockState(),
                    7));

    public static final ConfiguredFeature<MultisurfaceSphereReplaceConfig, ?> SANDSTONE_PATCH = new ConfiguredFeature<>(
            FeatureModule.MULTISURFACE_SPHERE_REPLACE,
            new MultisurfaceSphereReplaceConfig(
                    ImmutableList.of(Blocks.STONE, Blocks.DEEPSLATE),
                    BlockModule.ANCIENT_SAND.get().defaultBlockState(),
                    BlockModule.LAYERED_ANCIENT_SANDSTONE.get().defaultBlockState(),
                    BlockModule.ANCIENT_SANDSTONE.get().defaultBlockState(),
                    12));

    public static final ConfiguredFeature<GlowLichenConfiguration, ?> MARBLE_GLOW_LICHEN = new ConfiguredFeature<>(
            Feature.GLOW_LICHEN,
            new GlowLichenConfiguration(
                    20, false, true, true, 0.5F,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            BlockModule.TRAVERTINE.get(),
                            BlockModule.MARBLE.get())));

    public static final ConfiguredFeature<GlowLichenConfiguration, ?> SANDSTONE_GLOW_LICHEN = new ConfiguredFeature<>(
            Feature.GLOW_LICHEN,
            new GlowLichenConfiguration(
                    20, false, true, true, 0.5F,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            BlockModule.ANCIENT_SAND.get(),
                            BlockModule.LAYERED_ANCIENT_SANDSTONE.get(),
                            BlockModule.ANCIENT_SANDSTONE.get())));

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> CACTUS_PATCH = new ConfiguredFeature<>(
            FeatureModule.CACTUS_PATCH,
            NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<NoneFeatureConfiguration, ?> PRICKLY_PEAR_CACTUS_PATCH = new ConfiguredFeature<>(
            FeatureModule.PRICKLY_PEAR_CACTUS_PATCH,
            NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<SphereReplaceConfig, ?> BRITTLE_SANDSTONE_REPLACE = new ConfiguredFeature<>(
            FeatureModule.SPHERE_REPLACE,
            new SphereReplaceConfig(
                    ImmutableList.of(BlockModule.ANCIENT_SANDSTONE.get()),
                    BlockModule.BRITTLE_ANCIENT_SANDSTONE.get().defaultBlockState(),
                    7));

    private static final WeightedStateProvider PRICKLY_VINES_BODY_PROVIDER = new WeightedStateProvider(
            SimpleWeightedRandomList.<BlockState>builder()
                    .add(BlockModule.PRICKLY_VINES_PLANT.get().defaultBlockState(), 4)
                    .add(BlockModule.PRICKLY_VINES_PLANT.get().defaultBlockState(), 1)
                    .build());

    private static final RandomizedIntStateProvider PRICKLY_VINES_HEAD_PROVIDER = new RandomizedIntStateProvider(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(BlockModule.PRICKLY_VINES.get().defaultBlockState(), 4)
                    .add(BlockModule.PRICKLY_VINES.get().defaultBlockState(), 1)),
            PricklyVinesBlock.AGE, UniformInt.of(23, 25));

    public static final ConfiguredFeature<BlockColumnConfiguration, ?> PRICKLY_VINE = new ConfiguredFeature<>(
            Feature.BLOCK_COLUMN,
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
                    true));

    public static final ConfiguredFeature<ReplaceBlockConfiguration, ?> DISK_ROCK = new ConfiguredFeature<>(
            FeatureModule.DISK_ROCK,
            new ReplaceBlockConfiguration(
                    BlockModule.LAYERED_ANCIENT_SANDSTONE.get().defaultBlockState(),
                    BlockModule.LAYERED_ANCIENT_SANDSTONE.get().defaultBlockState()));

    public static void init() {
        register("large_icicle", LARGE_ICICLE);
        register("large_icicle_tilted", TILTED_ICICLE);
        register("small_icicle", SMALL_ICICLE);
        register("frost_lily", FROST_LILY);
        register("no_op", NO_OP);
        register("ice_patch", ICE_PATCH);
        register("ice_patch_ceiling", ICE_PATCH_CEILING);
        register("ice_sheet_replace", ICE_SHEET_REPLACE);
        register("icicles", ICICLES);
        register("water_surface_ice_fragment", WATER_SURFACE_ICE_FRAGMENT);
        register("marble_cave_water_pool", MARBLE_CAVE_WATER_POOL);
        register("spring_marble_water", SPRING_MARBLE_WATER);
        register("marble_patch", MARBLE_PATCH);
        register("travertine_patch", TRAVERTINE_PATCH);
        register("sandstone_patch", SANDSTONE_PATCH);
        register("marble_glow_lichen", MARBLE_GLOW_LICHEN);
        register("sandstone_glow_lichen", SANDSTONE_GLOW_LICHEN);
        register("brittle_sandstone_replace", BRITTLE_SANDSTONE_REPLACE);
        register("cactus_patch", CACTUS_PATCH);
        register("prickly_pear_cactus_patch", PRICKLY_PEAR_CACTUS_PATCH);
        register("prickly_vine", PRICKLY_VINE);
        register("disk_rock", DISK_ROCK);
    }

    private static void register(String name, ConfiguredFeature<?, ?> feature) {
        Services.REGISTRY.registerConfiguredFeature(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, name), feature);
    }
}
