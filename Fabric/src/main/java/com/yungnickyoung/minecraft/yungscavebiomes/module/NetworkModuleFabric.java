package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.network.IcicleShatterS2CPacketHandlerFabric;
import com.yungnickyoung.minecraft.yungscavebiomes.network.SandstormSyncS2CPacketHandlerFabric;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class NetworkModuleFabric {
    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(SandstormSyncS2CPayload.TYPE, SandstormSyncS2CPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(IcicleShatterS2CPayload.TYPE, IcicleShatterS2CPayload.STREAM_CODEC);
    }

    public static void registerS2CHandlers() {
        ClientPlayNetworking.registerGlobalReceiver(SandstormSyncS2CPayload.TYPE, SandstormSyncS2CPacketHandlerFabric::receive);
        ClientPlayNetworking.registerGlobalReceiver(IcicleShatterS2CPayload.TYPE, IcicleShatterS2CPacketHandlerFabric::receive);
    }
}
