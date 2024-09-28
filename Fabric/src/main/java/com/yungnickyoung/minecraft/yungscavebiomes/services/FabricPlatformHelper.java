package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
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
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(pos.x());
        buf.writeDouble(pos.y());
        buf.writeDouble(pos.z());
        for (ServerPlayer player : PlayerLookup.tracking(level, BlockPos.containing(pos))) {
            ServerPlayNetworking.send(player, NetworkModuleFabric.ICICLE_SHATTER_ID, buf);
        }
    }

    @Override
    public void syncSandstormDataToClients(SandstormServerData sandstormServerData) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(sandstormServerData.isSandstormActive());
        buf.writeInt(sandstormServerData.getCurrSandstormTicks());
        buf.writeLong(sandstormServerData.getSeed());
        buf.writeInt(sandstormServerData.getTotalSandstormDurationTicks());
        PlayerLookup.world(sandstormServerData.getServerLevel())
                .forEach(player -> ServerPlayNetworking.send(player, NetworkModuleFabric.SANDSTORM_SYNC_ID, buf));
    }

    @Override
    public void syncSandstormDataToPlayer(SandstormServerData sandstormServerData, ServerPlayer serverPlayer) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(sandstormServerData.isSandstormActive());
        buf.writeInt(sandstormServerData.getCurrSandstormTicks());
        buf.writeLong(sandstormServerData.getSeed());
        buf.writeInt(sandstormServerData.getTotalSandstormDurationTicks());
        ServerPlayNetworking.send(serverPlayer, NetworkModuleFabric.SANDSTORM_SYNC_ID, buf);
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
