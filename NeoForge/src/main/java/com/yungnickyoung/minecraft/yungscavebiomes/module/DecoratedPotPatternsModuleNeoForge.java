package com.yungnickyoung.minecraft.yungscavebiomes.module;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;

public class DecoratedPotPatternsModuleNeoForge {

    public static void queueForRegistration(ResourceLocation resourceLocation) {
        queuedPatterns.add(resourceLocation);
    }

    private static final List<ResourceLocation> queuedPatterns = new ArrayList<>();

    public static void init(IEventBus eventBus) {
        eventBus.addListener(DecoratedPotPatternsModuleNeoForge::registerDecoratedPotPatterns);
    }

    private static void registerDecoratedPotPatterns(final RegisterEvent event) {
        event.register(Registries.DECORATED_POT_PATTERN, helper -> {
            queuedPatterns.forEach(id -> {
                ResourceKey<DecoratedPotPattern> resourceKey = ResourceKey.create(Registries.DECORATED_POT_PATTERN, id);
                helper.register(resourceKey, new DecoratedPotPattern(id));
            });
        });
    }
}
