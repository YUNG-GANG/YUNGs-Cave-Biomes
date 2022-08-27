package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.*;

public interface IModulesLoader {
    default void loadModules() {
        EntityModule.init();
        FeatureModule.init();
        ConfiguredFeatureModule.init();
        PlacedFeatureModule.init();
        BiomeModule.init();

        MobEffectModule.init();
        PotionModule.init();
        ParticleTypeModule.init();
        SoundModule.init();
    }
}
