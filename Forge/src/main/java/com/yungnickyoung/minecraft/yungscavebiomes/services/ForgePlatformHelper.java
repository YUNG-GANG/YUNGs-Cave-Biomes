package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleForge;
import com.yungnickyoung.minecraft.yungscavebiomes.network.IcicleShatterS2CPacket;
import com.yungnickyoung.minecraft.yungscavebiomes.network.SandstormSyncS2CPacket;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.Supplier;

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
        if (serverLevel.isLoaded(BlockPos.containing(pos))) {
            ChunkAccess chunkAccess = serverLevel.getChunk(BlockPos.containing(pos));
            if (chunkAccess instanceof LevelChunk levelChunk) {
                NetworkModuleForge.sendToClientsTrackingChunk(new IcicleShatterS2CPacket(pos), levelChunk);
            }
        }
    }

    @Override
    public void syncSandstormDataToClients(SandstormServerData sandstormServerData) {
        NetworkModuleForge.sendToClientsInLevel(new SandstormSyncS2CPacket(sandstormServerData),
                sandstormServerData.getServerLevel().dimension());
    }

    @Override
    public void syncSandstormDataToPlayer(SandstormServerData sandstormServerData, ServerPlayer serverPlayer) {
        NetworkModuleForge.sendToClient(new SandstormSyncS2CPacket(sandstormServerData), serverPlayer);
    }

    @Override
    public Supplier<Item> getIceCubeSpawnEggItem() {
        return () -> new ForgeSpawnEggItem(() -> EntityTypeModule.ICE_CUBE.get(), 0xA4C4FC, 0xE4ECFC,
                new Item.Properties());
    }

    @Override
    public Supplier<Item> getSandSnapperSpawnEggItem() {
        return () -> new ForgeSpawnEggItem(() -> EntityTypeModule.SAND_SNAPPER.get(), 0xBA852F, 0xCFAC55,
                new Item.Properties());
    }
}
