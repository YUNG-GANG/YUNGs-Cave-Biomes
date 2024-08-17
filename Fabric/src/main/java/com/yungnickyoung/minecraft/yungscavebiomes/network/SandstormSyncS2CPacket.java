package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.sandstorm.SandstormClientData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

public class SandstormSyncS2CPacket {
    public static void receive(Minecraft client,
                               ClientPacketListener clientPacketListener,
                               FriendlyByteBuf buf,
                               PacketSender responseSender) {
        boolean isActive = buf.readBoolean();
        int sandstormTime = buf.readInt();
        long sandstormSeed = buf.readLong();
        int totalSandstormDuration = buf.readInt();

        client.execute(() -> {
            if (client.level != null) {
                // Set state on client level
                SandstormClientData sandstormClientData = ((ISandstormClientDataProvider) client.level).getSandstormClientData();
                sandstormClientData.setSandstormActive(isActive);
                sandstormClientData.setSandstormTime(sandstormTime);
                sandstormClientData.setSandstormSeed(sandstormSeed);
                sandstormClientData.setTotalSandstormDuration(totalSandstormDuration);
            }
        });
    }
}
