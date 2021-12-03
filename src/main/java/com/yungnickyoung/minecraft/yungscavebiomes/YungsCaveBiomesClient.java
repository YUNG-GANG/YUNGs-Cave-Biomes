package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModClient;
import net.fabricmc.api.ClientModInitializer;

public class YungsCaveBiomesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        YCBModClient.init();
    }
}
