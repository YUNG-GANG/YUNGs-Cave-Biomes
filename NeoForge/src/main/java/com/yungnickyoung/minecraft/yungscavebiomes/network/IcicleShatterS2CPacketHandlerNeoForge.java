package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.client.ClientUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class IcicleShatterS2CPacketHandlerNeoForge {
    public static void receive(IcicleShatterS2CPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            RandomSource random = context.player().level().getRandom();
            for (int i = 0; i < random.nextInt(5) + 10; i++) {
                ClientUtils.spawnIceShatterParticle(payload);
            }
        });
    }
}
