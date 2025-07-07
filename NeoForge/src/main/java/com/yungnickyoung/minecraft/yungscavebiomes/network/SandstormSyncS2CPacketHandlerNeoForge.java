package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.ClientUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SandstormSyncS2CPacketHandlerNeoForge {
    public static void receive(SandstormSyncS2CPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientUtils.syncClientSandstorm(payload);
        });
    }
}
