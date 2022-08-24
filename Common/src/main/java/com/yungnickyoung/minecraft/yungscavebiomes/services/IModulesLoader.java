package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.*;

public interface IModulesLoader {
    default void loadModules() {
        BiomeModule.init();
        EntityModule.init();
        FeatureModule.init();
        ConfiguredFeatureModule.init();
        PlacedFeatureModule.init();
        MobEffectModule.init();
        PotionModule.init();
        ParticleTypeModule.init();
        SoundModule.init();
    }
}
