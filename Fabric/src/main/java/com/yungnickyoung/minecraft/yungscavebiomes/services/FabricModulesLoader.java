package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModuleFabric;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleFabric;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        ConfigModuleFabric.init();
        NetworkModuleFabric.registerC2SPackets();
    }
}
