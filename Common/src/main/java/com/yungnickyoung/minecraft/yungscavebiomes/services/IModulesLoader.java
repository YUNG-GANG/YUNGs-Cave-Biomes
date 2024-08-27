package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DamageSourceModule;

public interface IModulesLoader {
    default void loadModules() {
        DamageSourceModule.init();
    }
}
