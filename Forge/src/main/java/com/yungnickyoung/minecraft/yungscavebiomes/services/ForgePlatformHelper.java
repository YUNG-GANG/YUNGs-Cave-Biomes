package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormServerData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleForge;
import com.yungnickyoung.minecraft.yungscavebiomes.network.IcicleShatterS2CPacket;
import com.yungnickyoung.minecraft.yungscavebiomes.network.SandstormSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public void sendIcicleProjectileShatterS2CPacket(ServerLevel serverLevel, Vec3 pos) {
        if (serverLevel.isLoaded(new BlockPos(pos))) {
            ChunkAccess chunkAccess = serverLevel.getChunk(new BlockPos(pos));
            if (chunkAccess instanceof LevelChunk levelChunk) {
                NetworkModuleForge.sendToClientsTrackingChunk(new IcicleShatterS2CPacket(pos), levelChunk);
            }
        }
    }

    @Override
    public void syncSandstormDataToClients(ServerLevel serverLevel) {
        ISandstormServerData sandstormData = (ISandstormServerData) serverLevel;
        NetworkModuleForge.sendToClientsInLevel(new SandstormSyncS2CPacket(
                sandstormData.isSandstormActive(),
                sandstormData.getSandstormTime(),
                sandstormData.getSandstormSeed()),
                serverLevel.dimension());
    }
}
