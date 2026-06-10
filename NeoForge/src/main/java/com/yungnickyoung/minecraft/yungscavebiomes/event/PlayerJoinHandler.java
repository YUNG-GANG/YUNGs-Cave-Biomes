package com.yungnickyoung.minecraft.yungscavebiomes.event;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerJoinHandler {
    /**
     * Syncs sandstorm data to player when they join the server.
     */
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) serverPlayer.level()).getSandstormServerData();
            Services.PLATFORM.syncSandstormDataToPlayer(sandstormServerData, serverPlayer);
        }
    }
}
