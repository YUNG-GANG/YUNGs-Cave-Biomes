package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SandstormSyncS2CPacketHandlerFabric {
    public static void receive(SandstormSyncS2CPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.client().level != null) {
                // Set state on client level
                SandstormClientData sandstormClientData = ((ISandstormClientDataProvider) context.client().level).getSandstormClientData();
                sandstormClientData.setSandstormActive(payload.isActive());
                sandstormClientData.setSandstormTime(payload.sandstormTime());
                sandstormClientData.setSandstormSeed(payload.sandstormSeed());
                sandstormClientData.setTotalSandstormDuration(payload.totalSandstormDuration());
            }
        });
    }
}
