package com.yungnickyoung.minecraft.yungscavebiomes.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.IceShatterParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class ClientUtils {
    public static void spawnIceShatterParticle(IcicleShatterS2CPayload payload) {
        Minecraft.getInstance().particleEngine.add(new IceShatterParticle(Minecraft.getInstance().level,
                payload.x(), payload.y(), payload.z(), Minecraft.getInstance().level.getRandom()));
    }

    public static void syncClientSandstorm(SandstormSyncS2CPayload payload) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            SandstormClientData sandstormClientData = ((ISandstormClientDataProvider) clientLevel).getSandstormClientData();
            sandstormClientData.setSandstormActive(payload.isActive());
            sandstormClientData.setSandstormTime(payload.sandstormTime());
            sandstormClientData.setSandstormSeed(payload.sandstormSeed());
            sandstormClientData.setTotalSandstormDuration(payload.totalSandstormDuration());
        }
    }
}
