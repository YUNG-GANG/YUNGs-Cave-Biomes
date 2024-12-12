package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class TrimPatternsModule {
    public static final ResourceKey<TrimPattern> ANCIENT = ResourceKey.create(Registries.TRIM_PATTERN, YungsCaveBiomesCommon.id("ancient"));
}
