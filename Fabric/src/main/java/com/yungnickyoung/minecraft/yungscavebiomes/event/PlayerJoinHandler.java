package com.yungnickyoung.minecraft.yungscavebiomes.event;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PlayerJoinHandler implements ServerEntityEvents.Load {
    /**
     * Syncs sandstorm data to player when they join the server.
     */
    @Override
    public void onLoad(Entity entity, ServerLevel serverLevel) {
        if (entity instanceof ServerPlayer serverPlayer) {
            SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) serverLevel).getSandstormServerData();
            Services.PLATFORM.syncSandstormDataToPlayer(sandstormServerData, serverPlayer);
        }
    }
}
