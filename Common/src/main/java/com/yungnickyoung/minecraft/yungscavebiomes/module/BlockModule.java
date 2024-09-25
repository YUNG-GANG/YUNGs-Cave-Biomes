package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.BrittleSandstoneBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.FrostLilyBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IcicleBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyPeachCactusBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyVinesBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyVinesPlantBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.RareIceBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class BlockModule {
    @AutoRegister("icicle")
    public static final AutoRegisterBlock ICICLE = AutoRegisterBlock.of(() -> new IcicleBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.ICE)
                    .noOcclusion()
                    .randomTicks()
                    .strength(0.5f)
                    .dynamicShape()
                    .pushReaction(PushReaction.DESTROY)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .instrument(NoteBlockInstrument.CHIME)
                    .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
                    .sound(SoundType.GLASS)))
            .withItem(Item.Properties::new);

    @AutoRegister("frost_lily")
    public static final AutoRegisterBlock FROST_LILY = AutoRegisterBlock.of(() -> new FrostLilyBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.ICE)
                    .noOcclusion()
                    .instabreak()
                    .dynamicShape()
                    .pushReaction(PushReaction.DESTROY)
                    .lightLevel(blockState -> 10)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .sound(SoundType.GLASS)))
            .withItem(() -> new Item.Properties().stacksTo(16));

    @AutoRegister("rare_ice")
    public static final AutoRegisterBlock RARE_ICE = AutoRegisterBlock.of(() -> new RareIceBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.ICE)
                    .friction(0.98f)
                    .noOcclusion()
                    .lightLevel((blockState) -> 11)
                    .requiresCorrectToolForDrops()
                    .strength(3f)
                    .instrument(NoteBlockInstrument.CHIME)
                    .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
                    .sound(SoundType.GLASS)))
            .withItem(Item.Properties::new);

    @AutoRegister("ice_sheet")
    public static final AutoRegisterBlock ICE_SHEET = AutoRegisterBlock.of(() -> new IceSheetBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.ICE)
                    .friction(0.98f)
                    .noOcclusion()
                    .noCollission()
                    .strength(0.3f)
                    .lightLevel(blockState -> blockState.getValue(BlockStateProperties.LIT) ? 4 : 0)
                    .randomTicks()
                    .isValidSpawn((state, world, pos, entityType) -> true)
                    .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.GLASS)))
            .withItem(Item.Properties::new);

    @AutoRegister("ancient_sand")
    public static final AutoRegisterBlock ANCIENT_SAND = AutoRegisterBlock.of(() -> new SandBlock(0xd1b482, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .strength(0.5f)
                    .instrument(NoteBlockInstrument.SNARE)
                    .sound(SoundType.SAND)))
            .withItem(Item.Properties::new);

    @AutoRegister("ancient_sandstone")
    public static final AutoRegisterBlock ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)))
            .withStairs()
            .withSlab()
            .withWall()
            .withItem(Item.Properties::new);

    @AutoRegister("brittle_ancient_sandstone")
    public static final AutoRegisterBlock BRITTLE_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(0xd1b482, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.5f)))
            .withItem(Item.Properties::new);

    @AutoRegister("brittle_sandstone")
    public static final AutoRegisterBlock BRITTLE_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(14406560, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.5f)))
            .withItem(Item.Properties::new);

    @AutoRegister("brittle_red_sandstone")
    public static final AutoRegisterBlock BRITTLE_RED_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(11098145, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.5f)))
            .withItem(Item.Properties::new);

    @AutoRegister("cut_ancient_sandstone")
    public static final AutoRegisterBlock CUT_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)))
            .withSlab()
            .withItem(Item.Properties::new);

    @AutoRegister("chiseled_ancient_sandstone")
    public static final AutoRegisterBlock CHISELED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)))
            .withItem(Item.Properties::new);

    @AutoRegister("smooth_ancient_sandstone")
    public static final AutoRegisterBlock SMOOTH_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)))
            .withStairs()
            .withSlab()
            .withItem(Item.Properties::new);

    @AutoRegister("layered_ancient_sandstone")
    public static final AutoRegisterBlock LAYERED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)))
            .withItem(Item.Properties::new);

    @AutoRegister("layered_sandstone")
    public static final AutoRegisterBlock LAYERED_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)))
            .withItem(Item.Properties::new);

    @AutoRegister("layered_red_sandstone")
    public static final AutoRegisterBlock LAYERED_RED_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withItem(Item.Properties::new);

    @AutoRegister("prickly_peach_cactus")
    public static final AutoRegisterBlock PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> new PricklyPeachCactusBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .sound(SoundType.WOOL)
                    .pushReaction(PushReaction.DESTROY)
                    .strength(0.4f)))
            .withItem(Item.Properties::new);

    @AutoRegister("potted_prickly_peach_cactus")
    public static final AutoRegisterBlock POTTED_PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> new FlowerPotBlock(PRICKLY_PEACH_CACTUS.get(), BlockBehaviour.Properties
            .of()
            .instabreak()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)));

    @AutoRegister("prickly_vines")
    public static final AutoRegisterBlock PRICKLY_VINES = AutoRegisterBlock.of(() -> new PricklyVinesBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .noCollission()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.WEEPING_VINES)))
            .withItem(Item.Properties::new);

    @AutoRegister("prickly_vines_plant")
    public static final AutoRegisterBlock PRICKLY_VINES_PLANT = AutoRegisterBlock.of(() -> new PricklyVinesPlantBlock(BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.WEEPING_VINES)));

    public static final TagKey<Block> SAND_SNAPPER_BLOCKS = TagKey.create(Registries.BLOCK,
            YungsCaveBiomesCommon.id("sand_snapper_blocks"));

    public static final TagKey<Block> ICE_SHEET_FEATURE_PLACEABLE_ON = TagKey.create(Registries.BLOCK,
            YungsCaveBiomesCommon.id("ice_sheet_feature_placeable_on"));

    public static final TagKey<Block> ICE_SHEET_FEATURE_AVOID = TagKey.create(Registries.BLOCK,
            YungsCaveBiomesCommon.id("ice_sheet_feature_avoid"));
}
