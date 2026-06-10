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
import com.yungnickyoung.minecraft.yungscavebiomes.block.SuspiciousAncientSandBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon.id;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class BlockModule {
    @AutoRegister("icicle")
    public static final AutoRegisterBlock ICICLE = AutoRegisterBlock.of(() -> new IcicleBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.ICE)
                    .noOcclusion()
                    .randomTicks()
                    .strength(0.5f)
                    .forceSolidOn()
                    .dynamicShape()
                    .pushReaction(PushReaction.DESTROY)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .instrument(NoteBlockInstrument.CHIME)
                    .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
                    .sound(SoundType.GLASS)
                    .setId(ResourceKey.create(Registries.BLOCK, id("icicle")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("icicle"))));

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
                    .sound(SoundType.GLASS)
            .setId(ResourceKey.create(Registries.BLOCK, id("frost_lily")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, id("frost_lily"))));

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
                    .sound(SoundType.GLASS)
            .setId(ResourceKey.create(Registries.BLOCK, id("rare_ice")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("rare_ice"))));

    @AutoRegister("ice_sheet")
    public static final AutoRegisterBlock ICE_SHEET = AutoRegisterBlock.of(() -> new IceSheetBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.ICE)
                    .friction(0.98f)
                    .noOcclusion()
                    .noCollision()
                    .strength(0.3f)
                    .lightLevel(blockState -> blockState.getValue(BlockStateProperties.LIT) ? 4 : 0)
                    .randomTicks()
                    .isValidSpawn((state, world, pos, entityType) -> true)
                    .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.GLASS)
            .setId(ResourceKey.create(Registries.BLOCK, id("ice_sheet")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("ice_sheet"))));


    @AutoRegister("ancient_sand")
    public static final AutoRegisterBlock ANCIENT_SAND = AutoRegisterBlock.of(() -> new ColoredFallingBlock(new ColorRGBA(0xd1b482),
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.SAND)
                            .strength(0.5f)
                            .instrument(NoteBlockInstrument.SNARE)
                            .sound(SoundType.SAND)
                            .setId(ResourceKey.create(Registries.BLOCK, id("ancient_sand")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("ancient_sand"))));


    @AutoRegister("suspicious_ancient_sand")
    public static final AutoRegisterBlock SUSPICIOUS_ANCIENT_SAND = AutoRegisterBlock.of(() -> new SuspiciousAncientSandBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.25F)
                    .sound(SoundType.SUSPICIOUS_SAND)
                    .pushReaction(PushReaction.DESTROY)
            .setId(ResourceKey.create(Registries.BLOCK, id("suspicious_ancient_sand"))),
                    SoundEvents.BRUSH_SAND,
                    SoundEvents.BRUSH_SAND_COMPLETED))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("suspicious_ancient_sand"))));

    @AutoRegister("ancient_sandstone")
    public static final AutoRegisterBlock ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("ancient_sandstone")))))
            .withStairs()
            .withSlab()
            .withWall()
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("ancient_sandstone"))));

    @AutoRegister("brittle_ancient_sandstone")
    public static final AutoRegisterBlock BRITTLE_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(0xd1b482, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.5f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("brittle_ancient_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("brittle_ancient_sandstone"))));


    @AutoRegister("brittle_sandstone")
    public static final AutoRegisterBlock BRITTLE_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(14406560, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.5f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("brittle_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("brittle_sandstone"))));

    @AutoRegister("brittle_red_sandstone")
    public static final AutoRegisterBlock BRITTLE_RED_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(11098145, BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.5f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("brittle_red_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("brittle_red_sandstone"))));

    @AutoRegister("cut_ancient_sandstone")
    public static final AutoRegisterBlock CUT_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("cut_ancient_sandstone")))))
            .withSlab()
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("cut_ancient_sandstone"))));

    @AutoRegister("chiseled_ancient_sandstone")
    public static final AutoRegisterBlock CHISELED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("chiseled_ancient_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("chiseled_ancient_sandstone"))));

    @AutoRegister("smooth_ancient_sandstone")
    public static final AutoRegisterBlock SMOOTH_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("smooth_ancient_sandstone")))))
            .withStairs()
            .withSlab()
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("smooth_ancient_sandstone"))));

    @AutoRegister("layered_ancient_sandstone")
    public static final AutoRegisterBlock LAYERED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("layered_ancient_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("layered_ancient_sandstone"))));

    @AutoRegister("layered_sandstone")
    public static final AutoRegisterBlock LAYERED_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("layered_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("layered_sandstone"))));

    @AutoRegister("layered_red_sandstone")
    public static final AutoRegisterBlock LAYERED_RED_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("layered_red_sandstone")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("layered_red_sandstone"))));

    @AutoRegister("prickly_peach_cactus")
    public static final AutoRegisterBlock PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> new PricklyPeachCactusBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .sound(SoundType.WOOL)
                    .pushReaction(PushReaction.DESTROY)
                    .strength(0.4f)
                    .setId(ResourceKey.create(Registries.BLOCK, id("prickly_peach_cactus")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("prickly_peach_cactus"))));

    @AutoRegister("potted_prickly_peach_cactus")
    public static final AutoRegisterBlock POTTED_PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> Services.PLATFORM.getPottedPricklyPeachCactusBlock());

    @AutoRegister("prickly_vines")
    public static final AutoRegisterBlock PRICKLY_VINES = AutoRegisterBlock.of(() -> new PricklyVinesBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .noCollision()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.WEEPING_VINES)
                    .setId(ResourceKey.create(Registries.BLOCK, id("prickly_vines")))))
            .withItem(() -> new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id("prickly_vines"))));

    @AutoRegister("prickly_vines_plant")
    public static final AutoRegisterBlock PRICKLY_VINES_PLANT = AutoRegisterBlock.of(() -> new PricklyVinesPlantBlock(BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.WEEPING_VINES)
            .setId(ResourceKey.create(Registries.BLOCK, id("prickly_vines_plant")))));

    public static final TagKey<Block> SAND_SNAPPER_BLOCKS = TagKey.create(Registries.BLOCK,
            id("sand_snapper_blocks"));

    public static final TagKey<Block> ICE_SHEET_FEATURE_AVOID = TagKey.create(Registries.BLOCK,
            id("ice_sheet_feature_avoid"));

    public static final TagKey<Block> CREEPING_ICE_GLOWS_ON = TagKey.create(Registries.BLOCK,
            id("creeping_ice_glows_on"));
}
