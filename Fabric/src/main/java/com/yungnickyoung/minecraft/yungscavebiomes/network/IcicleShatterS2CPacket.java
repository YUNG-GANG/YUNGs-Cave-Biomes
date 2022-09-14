package com.yungnickyoung.minecraft.yungscavebiomes.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Blocks;

public class IcicleShatterS2CPacket {
    public static void receive(Minecraft client,
                               ClientPacketListener clientPacketListener,
                               FriendlyByteBuf buf,
                               PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();
        client.execute(() -> {
            if (client.level != null) {
                client.level.addDestroyBlockEffect(blockPos, Blocks.ICE.defaultBlockState());
            }
        });
    }
}
