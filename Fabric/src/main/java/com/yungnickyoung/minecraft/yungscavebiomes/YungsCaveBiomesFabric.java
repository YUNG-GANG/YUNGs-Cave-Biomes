package com.yungnickyoung.minecraft.yungscavebiomes;

import net.fabricmc.api.ModInitializer;

public class YungsCaveBiomesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YungsCaveBiomesCommon.init();
    }
}
