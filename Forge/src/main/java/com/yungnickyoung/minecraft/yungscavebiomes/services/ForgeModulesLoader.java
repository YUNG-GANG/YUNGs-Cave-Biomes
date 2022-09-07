package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModuleForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModuleForge;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        ConfigModuleForge.init();
        BiomeModuleForge.init();
    }
}
