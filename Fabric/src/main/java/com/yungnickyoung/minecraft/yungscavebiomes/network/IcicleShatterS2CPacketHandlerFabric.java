package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.IceShatterParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.RandomSource;

public class IcicleShatterS2CPacketHandlerFabric {
    public static void receive(IcicleShatterS2CPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.client().level != null) {
                RandomSource random = context.client().level.getRandom();
                for (int i = 0; i < random.nextInt(5) + 10; i++) {
                    context.client().particleEngine.add(new IceShatterParticle(context.client().level,
                            payload.x(), payload.y(), payload.z()));
                }
            }
        });
    }
}
