package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.IceShatterParticle;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class IcicleShatterS2CPacket {
    public static void receive(Minecraft client,
                               ClientPacketListener clientPacketListener,
                               FriendlyByteBuf buf,
                               PacketSender responseSender) {
        Vec3 pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        client.execute(() -> {
            if (client.level != null) {
                Random random = client.level.getRandom();
                for (int i = 0; i < random.nextInt(5) + 10; i++) {
                    client.particleEngine.add(new IceShatterParticle(client.level, pos.x, pos.y, pos.z));
                }
            }
        });
    }
}
