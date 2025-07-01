package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SandstormSyncS2CPacketHandlerNeoForge {
    public static void receive(SandstormSyncS2CPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (clientLevel != null) {
                SandstormClientData sandstormClientData = ((ISandstormClientDataProvider) clientLevel).getSandstormClientData();
                sandstormClientData.setSandstormActive(payload.isActive());
                sandstormClientData.setSandstormTime(payload.sandstormTime());
                sandstormClientData.setSandstormSeed(payload.sandstormSeed());
                sandstormClientData.setTotalSandstormDuration(payload.totalSandstormDuration());
            }
        });
    }
}
