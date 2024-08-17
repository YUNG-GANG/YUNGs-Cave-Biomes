package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleFabric;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
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

    @Override
    public void syncSandstormDataToClients(SandstormServerData sandstormServerData) {
        FriendlyByteBuf buf = PacketByteBufs.create();
//        ISandstormServerData sandstormData = (ISandstormServerData) serverLevel;
        buf.writeBoolean(sandstormServerData.isSandstormActive());
        buf.writeInt(sandstormServerData.getCurrSandstormTicks());
        buf.writeLong(sandstormServerData.getSeed());
        buf.writeInt(sandstormServerData.getTotalSandstormDurationTicks());

//        buf.writeBoolean(sandstormData.isSandstormActive());
//        buf.writeInt(sandstormData.getSandstormTime());
//        buf.writeLong(sandstormData.getSandstormSeed());
//        buf.writeInt(sandstormData.getTotalSandstormDuration());
        PlayerLookup.world(sandstormServerData.getServerLevel())
                .forEach(player -> ServerPlayNetworking.send(player, NetworkModuleFabric.SANDSTORM_SYNC_ID, buf));
    }

    @Override
    public void syncSandstormDataToPlayer(SandstormServerData sandstormServerData, ServerPlayer serverPlayer) {
        FriendlyByteBuf buf = PacketByteBufs.create();
//        ISandstormServerData sandstormData = (ISandstormServerData) serverLevel;
        buf.writeBoolean(sandstormServerData.isSandstormActive());
        buf.writeInt(sandstormServerData.getCurrSandstormTicks());
        buf.writeLong(sandstormServerData.getSeed());
        buf.writeInt(sandstormServerData.getTotalSandstormDurationTicks());

//        buf.writeBoolean(sandstormData.isSandstormActive());
//        buf.writeInt(sandstormData.getSandstormTime());
//        buf.writeLong(sandstormData.getSandstormSeed());
//        buf.writeInt(sandstormData.getTotalSandstormDuration());
        ServerPlayNetworking.send(serverPlayer, NetworkModuleFabric.SANDSTORM_SYNC_ID, buf);
    }
}
