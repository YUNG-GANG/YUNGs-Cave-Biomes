package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModuleFabric;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModuleFabric;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        ConfigModuleFabric.init();
        BiomeModuleFabric.init();
    }
}
