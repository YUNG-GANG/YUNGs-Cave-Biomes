package com.yungnickyoung.minecraft.yungscavebiomes.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handleIcicleShatter(IcicleShatterS2CPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            clientLevel.addDestroyBlockEffect(packet.getPos(), Blocks.ICE.defaultBlockState());
        }
    }
}
