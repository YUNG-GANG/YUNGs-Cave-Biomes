package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModuleNeoForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper {
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
                IcicleShatterS2CPayload payload = new IcicleShatterS2CPayload(
                        pos.x(),
                        pos.y(),
                        pos.z());
                PacketDistributor.sendToPlayersTrackingChunk(serverLevel, levelChunk.getPos(), payload);
            }
        }
    }

    @Override
    public void syncSandstormDataToClients(SandstormServerData sandstormServerData) {
        SandstormSyncS2CPayload payload = new SandstormSyncS2CPayload(
                sandstormServerData.isSandstormActive(),
                sandstormServerData.getCurrSandstormTicks(),
                sandstormServerData.getSeed(),
                sandstormServerData.getTotalSandstormDurationTicks());
        PacketDistributor.sendToPlayersInDimension(sandstormServerData.getServerLevel(), payload);
    }

    @Override
    public void syncSandstormDataToPlayer(SandstormServerData sandstormServerData, ServerPlayer serverPlayer) {
        SandstormSyncS2CPayload payload = new SandstormSyncS2CPayload(
                sandstormServerData.isSandstormActive(),
                sandstormServerData.getCurrSandstormTicks(),
                sandstormServerData.getSeed(),
                sandstormServerData.getTotalSandstormDurationTicks());
        PacketDistributor.sendToPlayer(serverPlayer, payload);
    }

    @Override
    public Supplier<Item> getIceCubeSpawnEggItem() {
        return () -> new DeferredSpawnEggItem(() -> EntityTypeModule.ICE_CUBE.get(), 0xA4C4FC, 0xE4ECFC,
                new Item.Properties());
    }

    @Override
    public Supplier<Item> getSandSnapperSpawnEggItem() {
        return () -> new DeferredSpawnEggItem(() -> EntityTypeModule.SAND_SNAPPER.get(), 0xBA852F, 0xCFAC55,
                new Item.Properties());
    }

    @Override
    public Block getPottedPricklyPeachCactusBlock() {
        FlowerPotBlock flowerPotBlock = new FlowerPotBlock(
                () -> (FlowerPotBlock) Blocks.FLOWER_POT,
                () -> BlockModule.PRICKLY_PEACH_CACTUS.get(),
                BlockBehaviour.Properties
                        .of()
                        .instabreak()
                        .noOcclusion()
                        .pushReaction(PushReaction.DESTROY));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(YungsCaveBiomesCommon.id("prickly_peach_cactus"), () -> flowerPotBlock);
        return flowerPotBlock;
    }

    @Override
    public ResourceKey<DecoratedPotPattern> registerDecoratedPotPattern(String name, AutoRegisterItem potterySherdItem) {
        ResourceLocation resourceLocation = YungsCaveBiomesCommon.id(name);

        // Register
        DecoratedPotPatternsModuleNeoForge.queueForRegistration(resourceLocation);

        // Add the resource key and item to relevant data structures
        ResourceKey<DecoratedPotPattern> resourceKey = ResourceKey.create(Registries.DECORATED_POT_PATTERN, resourceLocation);
        DecoratedPotPatternsModule.ALL_PATTERNS.add(resourceKey);
        DecoratedPotPatternsModule.RESOUCE_KEY_BY_ITEM.put(potterySherdItem.get(), resourceKey);

        return resourceKey;
    }
}
