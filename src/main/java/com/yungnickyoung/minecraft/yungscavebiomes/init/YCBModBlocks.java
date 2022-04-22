package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class YCBModBlocks {
    /** Blocks **/
    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings
        .of(Material.STONE, MaterialColor.ICE)
        .noOcclusion()
        .sound(SoundType.GLASS)
        .randomTicks()
        .strength(0.5f)
        .dynamicShape());

    public static final Block FROST_LILY = new FrostLilyBlock(FabricBlockSettings
            .of(Material.ICE, MaterialColor.ICE)
            .noOcclusion()
            .dynamicShape()
            .instabreak()
            .lightLevel(blockState -> 10)
            .sound(SoundType.GLASS));

    public static final Block MARBLE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.TERRACOTTA_WHITE)
            .requiresCorrectToolForDrops()
            .strength(1.3f, 4.0f)
            .sound(SoundType.STONE));

    public static final Block TRAVERTINE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.TERRACOTTA_PINK)
            .requiresCorrectToolForDrops()
            .strength(1.5f, 6.0f)
            .sound(SoundType.CALCITE));

    public static final Block RARE_ICE = new RareIceBlock(FabricBlockSettings
            .of(Material.ICE_SOLID, MaterialColor.ICE)
            .friction(0.98f)
            .noOcclusion()
            .lightLevel((blockState) -> 11)
            .requiresCorrectToolForDrops()
            .strength(3f)
            .sound(SoundType.GLASS));

    public static final Block ICE_SHEET = new IceSheetBlock(FabricBlockSettings
            .of(Material.ICE_SOLID, MaterialColor.ICE)
            .friction(0.98f)
            .noOcclusion()
            .strength(0.5f)
            .sound(SoundType.GLASS));

    public static final Block ANCIENT_SAND = new SandBlock(0xd1b482, FabricBlockSettings
            .of(Material.SAND, MaterialColor.SAND)
            .strength(0.5f)
            .sound(SoundType.SAND));

    public static final Block ANCIENT_SANDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(0.8f));

    public static final Block BRITTLE_ANCIENT_SANDSTONE = new BrittleSandstoneBlock(0xd1b482, FabricBlockSettings
            .of(Material.STONE, MaterialColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(0.5f));

    public static final Block BRITTLE_SANDSTONE = new BrittleSandstoneBlock(14406560, FabricBlockSettings
            .of(Material.STONE, MaterialColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(0.5f));

    public static final Block BRITTLE_RED_SANDSTONE = new BrittleSandstoneBlock(11098145, FabricBlockSettings
            .of(Material.STONE, MaterialColor.COLOR_ORANGE)
            .requiresCorrectToolForDrops()
            .strength(0.5f));

    public static final Block CUT_ANCIENT_SANDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(0.8f));

    public static final Block LAYERED_ANCIENT_SANDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(0.8f));

    public static final Block LAYERED_SANDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(0.8f));

    public static final Block LAYERED_RED_SANDSTONE = new Block(FabricBlockSettings
            .of(Material.STONE, MaterialColor.COLOR_ORANGE)
            .requiresCorrectToolForDrops()
            .strength(0.8f));

    public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(FabricBlockSettings
            .of(Material.CACTUS)
            .ticksRandomly()
            .sounds(SoundType.WOOL)
            .strength(0.4f));

    public static final Block POTTED_PRICKLY_PEAR_CACTUS = new FlowerPotBlock(PRICKLY_PEAR_CACTUS, FabricBlockSettings
            .of(Material.DECORATION)
            .instabreak()
            .noOcclusion());

    public static final Block PRICKLY_VINES = new PricklyVinesBlock(FabricBlockSettings
            .of(Material.PLANT)
            .ticksRandomly()
            .noCollision()
            .breakInstantly()
            .sounds(SoundType.WEEPING_VINES));

    public static final Block PRICKLY_VINES_PLANT = new PricklyVinesPlantBlock(FabricBlockSettings
            .of(Material.PLANT)
            .ticksRandomly()
            .noCollision()
            .breakInstantly()
            .sounds(SoundType.WEEPING_VINES));

    public static void init() {
        // Blocks
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "icicle"), ICICLE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "frost_lily"), FROST_LILY);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "marble"), MARBLE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "travertine"), TRAVERTINE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "rare_ice"), RARE_ICE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_sheet"), ICE_SHEET);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ancient_sand"), ANCIENT_SAND);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ancient_sandstone"), ANCIENT_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "brittle_ancient_sandstone"), BRITTLE_ANCIENT_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "brittle_sandstone"), BRITTLE_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "brittle_red_sandstone"), BRITTLE_RED_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "cut_ancient_sandstone"), CUT_ANCIENT_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "layered_ancient_sandstone"), LAYERED_ANCIENT_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "layered_sandstone"), LAYERED_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "layered_red_sandstone"), LAYERED_RED_SANDSTONE);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "prickly_pear_cactus"), PRICKLY_PEAR_CACTUS);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "potted_prickly_pear_cactus"), POTTED_PRICKLY_PEAR_CACTUS);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "prickly_vines"), PRICKLY_VINES);
        Registry.register(Registry.BLOCK, new ResourceLocation(YungsCaveBiomes.MOD_ID, "prickly_vines_plant"), PRICKLY_VINES_PLANT);

        // Items
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "icicle"),
                new BlockItem(ICICLE, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "frost_lily"),
                new BlockItem(FROST_LILY, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "marble"),
                new BlockItem(MARBLE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "travertine"),
                new BlockItem(TRAVERTINE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "rare_ice"),
                new BlockItem(RARE_ICE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_sheet"),
                new BlockItem(ICE_SHEET, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "ancient_sand"),
                new BlockItem(ANCIENT_SAND, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "ancient_sandstone"),
                new BlockItem(ANCIENT_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "brittle_ancient_sandstone"),
                new BlockItem(BRITTLE_ANCIENT_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "brittle_sandstone"),
                new BlockItem(BRITTLE_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "brittle_red_sandstone"),
                new BlockItem(BRITTLE_RED_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "cut_ancient_sandstone"),
                new BlockItem(CUT_ANCIENT_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "layered_ancient_sandstone"),
                new BlockItem(LAYERED_ANCIENT_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "layered_sandstone"),
                new BlockItem(LAYERED_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "layered_red_sandstone"),
                new BlockItem(LAYERED_RED_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "prickly_pear_cactus"),
                new BlockItem(PRICKLY_PEAR_CACTUS, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM,
                new ResourceLocation(YungsCaveBiomes.MOD_ID, "prickly_vines"),
                new BlockItem(PRICKLY_VINES, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
    }
}
