package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.config.YCBForgeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ConfigModuleForge {
    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, YCBForgeConfig.SPEC, "YungsCaveBiomes-forge-1_20_1.toml");
        MinecraftForge.EVENT_BUS.addListener(ConfigModuleForge::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigModuleForge::onConfigChange);
    }

    private static void onWorldLoad(LevelEvent.Load event) {
        bakeConfig();
    }

    private static void onConfigChange(ModConfigEvent event) {
        if (event.getConfig().getSpec() == YCBForgeConfig.SPEC) {
            bakeConfig();
        }
    }

    private static void bakeConfig() {
        YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms = YCBForgeConfig.lostCaves.enableSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration = YCBForgeConfig.lostCaves.minSandstormDuration.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration = YCBForgeConfig.lostCaves.maxSandstormDuration.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms = YCBForgeConfig.lostCaves.minTimeBetweenSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms = YCBForgeConfig.lostCaves.maxTimeBetweenSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles = YCBForgeConfig.lostCaves.extraSandstormParticles.get();

        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMin = YCBForgeConfig.lostCaves.noiseParameters.temperatureMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMax = YCBForgeConfig.lostCaves.noiseParameters.temperatureMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMin = YCBForgeConfig.lostCaves.noiseParameters.humidityMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMax = YCBForgeConfig.lostCaves.noiseParameters.humidityMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMin = YCBForgeConfig.lostCaves.noiseParameters.continentalnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMax = YCBForgeConfig.lostCaves.noiseParameters.continentalnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMin = YCBForgeConfig.lostCaves.noiseParameters.erosionMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMax = YCBForgeConfig.lostCaves.noiseParameters.erosionMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMin = YCBForgeConfig.lostCaves.noiseParameters.depthMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMax = YCBForgeConfig.lostCaves.noiseParameters.depthMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMin = YCBForgeConfig.lostCaves.noiseParameters.weirdnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMax = YCBForgeConfig.lostCaves.noiseParameters.weirdnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.offset = YCBForgeConfig.lostCaves.noiseParameters.offset.get().floatValue();

        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMin = YCBForgeConfig.frostedCaves.noiseParameters.temperatureMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMax = YCBForgeConfig.frostedCaves.noiseParameters.temperatureMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMin = YCBForgeConfig.frostedCaves.noiseParameters.humidityMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMax = YCBForgeConfig.frostedCaves.noiseParameters.humidityMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMin = YCBForgeConfig.frostedCaves.noiseParameters.continentalnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMax = YCBForgeConfig.frostedCaves.noiseParameters.continentalnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMin = YCBForgeConfig.frostedCaves.noiseParameters.erosionMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMax = YCBForgeConfig.frostedCaves.noiseParameters.erosionMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMin = YCBForgeConfig.frostedCaves.noiseParameters.depthMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMax = YCBForgeConfig.frostedCaves.noiseParameters.depthMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMin = YCBForgeConfig.frostedCaves.noiseParameters.weirdnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMax = YCBForgeConfig.frostedCaves.noiseParameters.weirdnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.offset = YCBForgeConfig.frostedCaves.noiseParameters.offset.get().floatValue();

        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMin = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.humidityMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMax = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.humidityMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMin = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.erosionMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMax = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.erosionMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMin = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.depthMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMax = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.depthMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax.get().floatValue();
        YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.offset = YCBForgeConfig.other.vanillaBiomeModifications.dripstoneCaves.offset.get().floatValue();
    }
}
