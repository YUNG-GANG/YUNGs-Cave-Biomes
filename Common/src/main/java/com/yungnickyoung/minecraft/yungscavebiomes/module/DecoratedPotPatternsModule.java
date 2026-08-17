package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon.id;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class DecoratedPotPatternsModule {
    public static final Set<ResourceKey<DecoratedPotPattern>>                    ALL_PATTERNS         = new HashSet<>();
    public static final Map<ResourceKey<Item>, ResourceKey<DecoratedPotPattern>> RESOURCE_KEY_BY_ITEM = new HashMap<>();

    @AutoRegister("_ignored")
    public static void init() {}

    public static final ResourceKey<DecoratedPotPattern> HOURGLASS = Services.PLATFORM.registerDecoratedPotPattern(
            "hourglass_pottery_pattern", ResourceKey.create(Registries.ITEM, id("hourglass_pottery_sherd")));

    public static final ResourceKey<DecoratedPotPattern> CLOCK = Services.PLATFORM.registerDecoratedPotPattern(
            "clock_pottery_pattern", ResourceKey.create(Registries.ITEM, id("clock_pottery_sherd")));

    @Nullable
    public static ResourceKey<DecoratedPotPattern> getResourceKeyForItem(Holder.Reference<Item> item) {
        return RESOURCE_KEY_BY_ITEM.get(item.key());
    }

    public static boolean isCustomRegisteredKey(ResourceKey<String> key) {
        return ALL_PATTERNS.contains(key);
    }
}
