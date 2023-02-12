package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class BlockModule {
    @AutoRegister("icicle")
    public static final AutoRegisterBlock ICICLE = AutoRegisterBlock.of(() -> new IcicleBlock(BlockBehaviour.Properties
                    .of(Material.ICE, MaterialColor.ICE)
                    .noOcclusion()
                    .randomTicks()
                    .strength(0.5f)
                    .dynamicShape()
                    .sound(SoundType.GLASS)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("frost_lily")
    public static final AutoRegisterBlock FROST_LILY = AutoRegisterBlock.of(() -> new FrostLilyBlock(BlockBehaviour.Properties
                    .of(Material.ICE, MaterialColor.ICE)
                    .noOcclusion()
                    .instabreak()
                    .dynamicShape()
                    .lightLevel(blockState -> 10)
                    .sound(SoundType.GLASS)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("marble")
    public static final AutoRegisterBlock MARBLE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.TERRACOTTA_WHITE)
                    .requiresCorrectToolForDrops()
                    .strength(1.3f, 4.0f)
                    .sound(SoundType.STONE)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("travertine")
    public static final AutoRegisterBlock TRAVERTINE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.TERRACOTTA_PINK)
                    .requiresCorrectToolForDrops()
                    .strength(1.5f, 6.0f)
                    .sound(SoundType.CALCITE)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("rare_ice")
    public static final AutoRegisterBlock RARE_ICE = AutoRegisterBlock.of(() -> new RareIceBlock(BlockBehaviour.Properties
                    .of(Material.ICE_SOLID, MaterialColor.ICE)
                    .friction(0.98f)
                    .noOcclusion()
                    .lightLevel((blockState) -> 11)
                    .requiresCorrectToolForDrops()
                    .strength(3f)
                    .sound(SoundType.GLASS)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("ice_sheet")
    public static final AutoRegisterBlock ICE_SHEET = AutoRegisterBlock.of(() -> new IceSheetBlock(BlockBehaviour.Properties
                    .of(Material.TOP_SNOW, MaterialColor.ICE)
                    .friction(0.98f)
                    .noOcclusion()
                    .noCollission()
                    .strength(0.3f)
                    .lightLevel(blockState -> blockState.getValue(BlockStateProperties.LIT) ? 4 : 0)
                    .randomTicks()
                    .sound(SoundType.GLASS)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("ancient_sand")
    public static final AutoRegisterBlock ANCIENT_SAND = AutoRegisterBlock.of(() -> new SandBlock(0xd1b482, BlockBehaviour.Properties
                    .of(Material.SAND, MaterialColor.SAND)
                    .strength(0.5f)
                    .sound(SoundType.SAND)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("ancient_sandstone")
    public static final AutoRegisterBlock ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withStairs()
            .withSlab()
            .withWall()
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("brittle_ancient_sandstone")
    public static final AutoRegisterBlock BRITTLE_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(0xd1b482, BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.5f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("brittle_sandstone")
    public static final AutoRegisterBlock BRITTLE_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(14406560, BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.5f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("brittle_red_sandstone")
    public static final AutoRegisterBlock BRITTLE_RED_SANDSTONE = AutoRegisterBlock.of(() -> new BrittleSandstoneBlock(11098145, BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops()
                    .strength(0.5f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("cut_ancient_sandstone")
    public static final AutoRegisterBlock CUT_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withSlab()
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("chiseled_ancient_sandstone")
    public static final AutoRegisterBlock CHISELED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("smooth_ancient_sandstone")
    public static final AutoRegisterBlock SMOOTH_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withStairs()
            .withSlab()
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("layered_ancient_sandstone")
    public static final AutoRegisterBlock LAYERED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("layered_sandstone")
    public static final AutoRegisterBlock LAYERED_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("layered_red_sandstone")
    public static final AutoRegisterBlock LAYERED_RED_SANDSTONE = AutoRegisterBlock.of(() -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops()
                    .strength(0.8f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("prickly_peach_cactus")
    public static final AutoRegisterBlock PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> new PricklyPeachCactusBlock(BlockBehaviour.Properties
                    .of(Material.CACTUS)
                    .randomTicks()
                    .sound(SoundType.WOOL)
                    .strength(0.4f)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("potted_prickly_peach_cactus")
    public static final AutoRegisterBlock POTTED_PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> new FlowerPotBlock(PRICKLY_PEACH_CACTUS.get(), BlockBehaviour.Properties
            .of(Material.DECORATION)
            .instabreak()
            .noOcclusion()));

    @AutoRegister("prickly_vines")
    public static final AutoRegisterBlock PRICKLY_VINES = AutoRegisterBlock.of(() -> new PricklyVinesBlock(BlockBehaviour.Properties
                    .of(Material.PLANT)
                    .randomTicks()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.WEEPING_VINES)))
            .withItem(() -> new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get()));

    @AutoRegister("prickly_vines_plant")
    public static final AutoRegisterBlock PRICKLY_VINES_PLANT = AutoRegisterBlock.of(() -> new PricklyVinesPlantBlock(BlockBehaviour.Properties
            .of(Material.PLANT)
            .randomTicks()
            .noCollission()
            .instabreak()
            .sound(SoundType.WEEPING_VINES)));
}
