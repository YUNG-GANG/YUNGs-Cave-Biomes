package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.config.YCBConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.InteractionResult;

public class ConfigModuleFabric {
    public static void init() {
        AutoConfig.register(YCBConfigFabric.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(YCBConfigFabric.class).registerSaveListener(ConfigModuleFabric::bakeConfig);
        AutoConfig.getConfigHolder(YCBConfigFabric.class).registerLoadListener(ConfigModuleFabric::bakeConfig);
        bakeConfig(AutoConfig.getConfigHolder(YCBConfigFabric.class).get());
    }

    private static InteractionResult bakeConfig(ConfigHolder<YCBConfigFabric> configHolder, YCBConfigFabric configFabric) {
        bakeConfig(configFabric);
        return InteractionResult.SUCCESS;
    }

    private static void bakeConfig(YCBConfigFabric configFabric) {
        YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms = configFabric.lostCaves.enableSandstorms;
        YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration = configFabric.lostCaves.minSandstormDuration;
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration = configFabric.lostCaves.maxSandstormDuration;
        YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms = configFabric.lostCaves.minTimeBetweenSandstorms;
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms = configFabric.lostCaves.maxTimeBetweenSandstorms;
        YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles = configFabric.lostCaves.extraSandstormParticles;
    }
}
