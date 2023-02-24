package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.config.YCBConfigForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ConfigModuleForge {
    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, YCBConfigForge.SPEC, "YungsCaveBiomes-forge-1_18_2.toml");
        MinecraftForge.EVENT_BUS.addListener(ConfigModuleForge::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigModuleForge::onConfigChange);
    }

    private static void onWorldLoad(WorldEvent.Load event) {
        bakeConfig();
    }

    private static void onConfigChange(ModConfigEvent event) {
        if (event.getConfig().getSpec() == YCBConfigForge.SPEC) {
            bakeConfig();
        }
    }

    private static void bakeConfig() {
        YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms = YCBConfigForge.lostCaves.enableSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration = YCBConfigForge.lostCaves.minSandstormDuration.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration = YCBConfigForge.lostCaves.maxSandstormDuration.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms = YCBConfigForge.lostCaves.minTimeBetweenSandstorms.get();
        YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms = YCBConfigForge.lostCaves.maxTimeBetweenSandstorms.get();
    }
}
