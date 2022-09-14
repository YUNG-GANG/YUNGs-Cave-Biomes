package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleForge;
import com.yungnickyoung.minecraft.yungscavebiomes.network.IcicleShatterS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
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
    public void sendIcicleProjectileShatterS2CPacket(ServerLevel serverLevel, BlockPos pos) {
        if (serverLevel.isLoaded(pos)) {
            ChunkAccess chunkAccess = serverLevel.getChunk(pos);
            if (chunkAccess instanceof LevelChunk levelChunk) {
                NetworkModuleForge.sendToClientsTrackingChunk(new IcicleShatterS2CPacket(pos), levelChunk);
            }
        }
    }
}
