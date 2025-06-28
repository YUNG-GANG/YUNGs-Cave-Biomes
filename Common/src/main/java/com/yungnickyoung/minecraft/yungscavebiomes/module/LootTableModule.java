package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTableModule {
    public static final ResourceKey<LootTable> SAND_SNAPPER_LOOT = ResourceKey.create(Registries.LOOT_TABLE,
            YungsCaveBiomesCommon.id("archaeology/sand_snapper_loot"));
}
