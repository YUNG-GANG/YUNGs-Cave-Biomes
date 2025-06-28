package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class DecoratedPotPatternsModule {
    private static final Set<ResourceKey<DecoratedPotPattern>> ALL_PATTERNS = new HashSet<>();
    private static final Map<Item, ResourceKey<DecoratedPotPattern>> RESOUCE_KEY_BY_ITEM = new HashMap<>();

    @AutoRegister("_ignored")
    public static void init() {}

    public static final ResourceKey<DecoratedPotPattern> HOURGLASS = create("hourglass_pottery_pattern", ItemModule.HOURGLASS_POTTERY_SHERD);
    public static final ResourceKey<DecoratedPotPattern> CLOCK = create("clock_pottery_pattern", ItemModule.CLOCK_POTTERY_SHERD);

    private static ResourceKey<DecoratedPotPattern> create(String name, AutoRegisterItem autoRegisterItem) {
        ResourceLocation resourceLocation = YungsCaveBiomesCommon.id(name);

        // Register
        ResourceKey<DecoratedPotPattern> resourceKey = ResourceKey.create(Registries.DECORATED_POT_PATTERN, resourceLocation);
        Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, resourceKey, new DecoratedPotPattern(resourceLocation));

        // Add the resource key and item to relevant data structures
        ALL_PATTERNS.add(resourceKey);
        RESOUCE_KEY_BY_ITEM.put(autoRegisterItem.get(), resourceKey);

        return resourceKey;
    }

    @Nullable
    public static ResourceKey<DecoratedPotPattern> getResourceKeyForItem(Item item) {
        return RESOUCE_KEY_BY_ITEM.get(item);
    }

    public static boolean isCustomRegisteredKey(ResourceKey<String> key) {
        return ALL_PATTERNS.contains(key);
    }
}
