package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModuleForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TerraBlenderModule;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        ConfigModuleForge.init();
        NetworkModuleForge.init();
        TerraBlenderModule.init();
    }
}
