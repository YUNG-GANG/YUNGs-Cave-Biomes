package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

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
        IcicleShatterS2CPayload payload = new IcicleShatterS2CPayload(pos.x(), pos.y(), pos.z());
        PlayerLookup.tracking(level, BlockPos.containing(pos))
                .forEach(player -> ServerPlayNetworking.send(player, payload));
    }

    @Override
    public void syncSandstormDataToClients(SandstormServerData sandstormServerData) {
        SandstormSyncS2CPayload payload = new SandstormSyncS2CPayload(
                sandstormServerData.isSandstormActive(),
                sandstormServerData.getCurrSandstormTicks(),
                sandstormServerData.getSeed(),
                sandstormServerData.getTotalSandstormDurationTicks());
        PlayerLookup.world(sandstormServerData.getServerLevel())
                .forEach(player -> ServerPlayNetworking.send(player, payload));
    }

    @Override
    public void syncSandstormDataToPlayer(SandstormServerData sandstormServerData, ServerPlayer serverPlayer) {
        SandstormSyncS2CPayload payload = new SandstormSyncS2CPayload(
                sandstormServerData.isSandstormActive(),
                sandstormServerData.getCurrSandstormTicks(),
                sandstormServerData.getSeed(),
                sandstormServerData.getTotalSandstormDurationTicks());
        ServerPlayNetworking.send(serverPlayer, payload);
    }

    @Override
    public Supplier<Item> getIceCubeSpawnEggItem() {
        return () -> new SpawnEggItem(EntityTypeModule.ICE_CUBE.get(), 0xA4C4FC, 0xE4ECFC,
                new Item.Properties());
    }

    @Override
    public Supplier<Item> getSandSnapperSpawnEggItem() {
        return () -> new SpawnEggItem(EntityTypeModule.SAND_SNAPPER.get(), 0xBA852F, 0xCFAC55,
                new Item.Properties());
    }

    @Override
    public Block getPottedPricklyPeachCactusBlock() {
        return new FlowerPotBlock(BlockModule.PRICKLY_PEACH_CACTUS.get(), BlockBehaviour.Properties
                .of()
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY));
    }
}
