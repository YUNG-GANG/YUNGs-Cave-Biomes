package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleFabric;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void sendIcicleProjectileShatterS2CPacket(ServerLevel level, Vec3 pos) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(pos.x());
        buf.writeDouble(pos.y());
        buf.writeDouble(pos.z());
        for (ServerPlayer player : PlayerLookup.tracking(level, new BlockPos(pos))) {
            ServerPlayNetworking.send(player, NetworkModuleFabric.ICICLE_SHATTER_ID, buf);
        }
    }
}
