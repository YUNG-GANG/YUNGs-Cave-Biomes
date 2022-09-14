package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.network.IcicleShatterS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class NetworkModuleFabric {
    public static final ResourceLocation ICICLE_SHATTER_ID = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "icicle_shatter");

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ICICLE_SHATTER_ID, IcicleShatterS2CPacket::receive);
    }
}
