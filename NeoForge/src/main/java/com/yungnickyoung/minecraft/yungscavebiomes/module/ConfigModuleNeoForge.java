package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.config.YCBNeoForgeConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

public class ConfigModuleNeoForge {
    public static void init(IEventBus eventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, YCBNeoForgeConfig.SPEC, "yungscavebiomes-neoforge-" + YungsCaveBiomesCommon.MC_VERSION_STRING + ".toml");
        NeoForge.EVENT_BUS.addListener(ConfigModuleNeoForge::onWorldLoad);
        eventBus.addListener(ConfigModuleNeoForge::onConfigChange);
    }

    private static void onWorldLoad(LevelEvent.Load event) {
        bakeConfig();
    }

    private static void onConfigChange(ModConfigEvent event) {
        if (event.getConfig().getSpec() == YCBNeoForgeConfig.SPEC) {
            bakeConfig();
        }
    }

    private static void bakeConfig() {
        YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms = YCBNeoForgeConfig.lostCaves.enableSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration = YCBNeoForgeConfig.lostCaves.minSandstormDuration.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration = YCBNeoForgeConfig.lostCaves.maxSandstormDuration.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms = YCBNeoForgeConfig.lostCaves.minTimeBetweenSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms = YCBNeoForgeConfig.lostCaves.maxTimeBetweenSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles = YCBNeoForgeConfig.lostCaves.extraSandstormParticles.get();

        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMin = YCBNeoForgeConfig.lostCaves.noiseParameters.temperatureMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMax = YCBNeoForgeConfig.lostCaves.noiseParameters.temperatureMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMin = YCBNeoForgeConfig.lostCaves.noiseParameters.humidityMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMax = YCBNeoForgeConfig.lostCaves.noiseParameters.humidityMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMin = YCBNeoForgeConfig.lostCaves.noiseParameters.continentalnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMax = YCBNeoForgeConfig.lostCaves.noiseParameters.continentalnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMin = YCBNeoForgeConfig.lostCaves.noiseParameters.erosionMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMax = YCBNeoForgeConfig.lostCaves.noiseParameters.erosionMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMin = YCBNeoForgeConfig.lostCaves.noiseParameters.depthMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMax = YCBNeoForgeConfig.lostCaves.noiseParameters.depthMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMin = YCBNeoForgeConfig.lostCaves.noiseParameters.weirdnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMax = YCBNeoForgeConfig.lostCaves.noiseParameters.weirdnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.offset = YCBNeoForgeConfig.lostCaves.noiseParameters.offset.get().floatValue();

        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMin = YCBNeoForgeConfig.frostedCaves.noiseParameters.temperatureMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMax = YCBNeoForgeConfig.frostedCaves.noiseParameters.temperatureMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMin = YCBNeoForgeConfig.frostedCaves.noiseParameters.humidityMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMax = YCBNeoForgeConfig.frostedCaves.noiseParameters.humidityMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMin = YCBNeoForgeConfig.frostedCaves.noiseParameters.continentalnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMax = YCBNeoForgeConfig.frostedCaves.noiseParameters.continentalnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMin = YCBNeoForgeConfig.frostedCaves.noiseParameters.erosionMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMax = YCBNeoForgeConfig.frostedCaves.noiseParameters.erosionMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMin = YCBNeoForgeConfig.frostedCaves.noiseParameters.depthMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMax = YCBNeoForgeConfig.frostedCaves.noiseParameters.depthMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMin = YCBNeoForgeConfig.frostedCaves.noiseParameters.weirdnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMax = YCBNeoForgeConfig.frostedCaves.noiseParameters.weirdnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.offset = YCBNeoForgeConfig.frostedCaves.noiseParameters.offset.get().floatValue();

        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMin = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.humidityMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMax = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.humidityMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMin = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.erosionMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMax = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.erosionMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMin = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.depthMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMax = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.depthMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.offset = YCBNeoForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.offset.get().floatValue();
    }
}
