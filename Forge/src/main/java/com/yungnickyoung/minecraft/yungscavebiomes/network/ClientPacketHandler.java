package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handleIcicleShatter(IcicleShatterS2CPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            Random random = clientLevel.getRandom();
            for (int i = 0; i < random.nextInt(5) + 10; i++) {
                clientLevel.addParticle((SimpleParticleType) ParticleTypeModule.ICE_SHATTER.get(), packet.getPos().x, packet.getPos().y, packet.getPos().z, 0, 0, 0);
            }
        }
    }

    public static void handleSandstormSync(SandstormSyncS2CPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            SandstormClientData sandstormClientData = ((ISandstormClientDataProvider) clientLevel).getSandstormClientData();
            sandstormClientData.setSandstormActive(packet.isActive());
            sandstormClientData.setSandstormTime(packet.getCurrSandstormTicks());
            sandstormClientData.setSandstormSeed(packet.getSandstormSeed());
            sandstormClientData.setTotalSandstormDuration(packet.getTotalSandstormDurationTicks());
        }
    }
}
