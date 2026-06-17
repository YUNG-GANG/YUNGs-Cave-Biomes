package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.IcicleShatterS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.network.payload.SandstormSyncS2CPayload;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

import static com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon.id;

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
    public void syncSandstormDataToClients(SandstormServerData sandstormServerData, final ServerLevel level) {
        SandstormSyncS2CPayload payload = new SandstormSyncS2CPayload(
                sandstormServerData.isSandstormActive(),
                sandstormServerData.getCurrSandstormTicks(),
                sandstormServerData.getSeed(),
                sandstormServerData.getTotalSandstormDurationTicks());
        PlayerLookup.level(level)
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
        return () -> new SpawnEggItem(new Item.Properties().spawnEgg(EntityTypeModule.ICE_CUBE.get()).setId(ResourceKey.create(Registries.ITEM, id("ice_cube_spawn_egg"))));
    }

    @Override
    public Supplier<Item> getSandSnapperSpawnEggItem() {
        return () -> new SpawnEggItem(new Item.Properties().spawnEgg(EntityTypeModule.SAND_SNAPPER.get()).setId(ResourceKey.create(Registries.ITEM, id("sand_snapper_spawn_egg"))));
    }

    @Override
    public Block getPottedPricklyPeachCactusBlock() {
        return new FlowerPotBlock(BlockModule.PRICKLY_PEACH_CACTUS.get(), BlockBehaviour.Properties
                .of()
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .setId(ResourceKey.create(Registries.BLOCK, id("potted_prickly_peach_cactus"))));
    }

    @Override
    public ResourceKey<DecoratedPotPattern> registerDecoratedPotPattern(String name, AutoRegisterItem potterySherdItem) {
        Identifier resourceLocation = id(name);

        // Register
        ResourceKey<DecoratedPotPattern> resourceKey = ResourceKey.create(Registries.DECORATED_POT_PATTERN, resourceLocation);
        Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, resourceKey, new DecoratedPotPattern(resourceLocation));

        // Add the resource key and item to relevant data structures
        DecoratedPotPatternsModule.ALL_PATTERNS.add(resourceKey);
        DecoratedPotPatternsModule.RESOUCE_KEY_BY_ITEM.put(potterySherdItem.get(), resourceKey);

        return resourceKey;

    }
}
