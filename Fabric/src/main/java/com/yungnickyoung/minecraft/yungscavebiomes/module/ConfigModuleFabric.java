package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.config.YCBFabricConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.InteractionResult;

public class ConfigModuleFabric {
    public static void init() {
        AutoConfig.register(YCBFabricConfig.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(YCBFabricConfig.class).registerSaveListener(ConfigModuleFabric::bakeConfig);
        AutoConfig.getConfigHolder(YCBFabricConfig.class).registerLoadListener(ConfigModuleFabric::bakeConfig);
        bakeConfig(AutoConfig.getConfigHolder(YCBFabricConfig.class).get());
    }

    private static InteractionResult bakeConfig(ConfigHolder<YCBFabricConfig> configHolder, YCBFabricConfig configFabric) {
        bakeConfig(configFabric);
        return InteractionResult.SUCCESS;
    }

    private static void bakeConfig(YCBFabricConfig configFabric) {
        YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms = configFabric.config.lostCaves.enableSandstorms;
        YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration = configFabric.config.lostCaves.minSandstormDuration;
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration = configFabric.config.lostCaves.maxSandstormDuration;
        YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms = configFabric.config.lostCaves.minTimeBetweenSandstorms;
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms = configFabric.config.lostCaves.maxTimeBetweenSandstorms;
        YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles = configFabric.config.lostCaves.extraSandstormParticles;

        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMin = configFabric.config.lostCaves.noiseParameters.temperatureMin;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMax = configFabric.config.lostCaves.noiseParameters.temperatureMax;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMin = configFabric.config.lostCaves.noiseParameters.humidityMin;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMax = configFabric.config.lostCaves.noiseParameters.humidityMax;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMin = configFabric.config.lostCaves.noiseParameters.continentalnessMin;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMax = configFabric.config.lostCaves.noiseParameters.continentalnessMax;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMin = configFabric.config.lostCaves.noiseParameters.erosionMin;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMax = configFabric.config.lostCaves.noiseParameters.erosionMax;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMin = configFabric.config.lostCaves.noiseParameters.depthMin;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMax = configFabric.config.lostCaves.noiseParameters.depthMax;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMin = configFabric.config.lostCaves.noiseParameters.weirdnessMin;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMax = configFabric.config.lostCaves.noiseParameters.weirdnessMax;
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.offset = configFabric.config.lostCaves.noiseParameters.offset;

        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMin = configFabric.config.frostedCaves.noiseParameters.temperatureMin;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMax = configFabric.config.frostedCaves.noiseParameters.temperatureMax;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMin = configFabric.config.frostedCaves.noiseParameters.humidityMin;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMax = configFabric.config.frostedCaves.noiseParameters.humidityMax;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMin = configFabric.config.frostedCaves.noiseParameters.continentalnessMin;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMax = configFabric.config.frostedCaves.noiseParameters.continentalnessMax;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMin = configFabric.config.frostedCaves.noiseParameters.erosionMin;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMax = configFabric.config.frostedCaves.noiseParameters.erosionMax;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMin = configFabric.config.frostedCaves.noiseParameters.depthMin;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMax = configFabric.config.frostedCaves.noiseParameters.depthMax;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMin = configFabric.config.frostedCaves.noiseParameters.weirdnessMin;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMax = configFabric.config.frostedCaves.noiseParameters.weirdnessMax;
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.offset = configFabric.config.frostedCaves.noiseParameters.offset;

        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMin = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.humidityMin;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMax = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.humidityMax;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMin = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.erosionMin;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMax = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.erosionMax;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMin = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.depthMin;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMax = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.depthMax;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax;
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.offset = configFabric.config.other.vanillaBiomeModifications.dripstoneCaves.offset;
    }
}
