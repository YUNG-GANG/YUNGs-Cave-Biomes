package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagModule {
    public static final TagKey<Block> SAND_SNAPPER_BLOCKS = TagKey.create(Registry.BLOCK_REGISTRY,
            new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "sand_snapper_blocks"));
}
