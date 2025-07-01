package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.network.IcicleShatterS2CPacketHandlerNeoForge;
import com.yungnickyoung.minecraft.yungscavebiomes.network.SandstormSyncS2CPacketHandlerNeoForge;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkModuleNeoForge {
    private static final String PROTOCOL_VERSION = "1.0";

    public static void init(IEventBus eventBus) {
        eventBus.addListener(NetworkModuleNeoForge::registerS2CHandlers);
    }

    private static void registerS2CHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToClient(
                SandstormSyncS2CPayload.TYPE,
                SandstormSyncS2CPayload.STREAM_CODEC,
                SandstormSyncS2CPacketHandlerNeoForge::receive
        );
        registrar.playToClient(
                IcicleShatterS2CPayload.TYPE,
                IcicleShatterS2CPayload.STREAM_CODEC,
                IcicleShatterS2CPacketHandlerNeoForge::receive
        );
    }
}
