package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public interface IPlatformHelper {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    void sendIcicleProjectileShatterS2CPacket(ServerLevel serverLevel, Vec3 pos);

    void syncSandstormDataToClients(SandstormServerData sandstormServerData);
    void syncSandstormDataToPlayer(SandstormServerData sandstormServerData, ServerPlayer serverPlayer);

    Supplier<Item> getIceCubeSpawnEggItem();
    Supplier<Item> getSandSnapperSpawnEggItem();
}
