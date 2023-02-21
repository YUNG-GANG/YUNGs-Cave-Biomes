package com.yungnickyoung.minecraft.yungscavebiomes.event;

import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PlayerJoinHandler implements ServerEntityEvents.Load {
    @Override
    public void onLoad(Entity entity, ServerLevel serverLevel) {
        if (entity instanceof ServerPlayer serverPlayer) {
            Services.PLATFORM.syncSandstormDataToPlayer(serverLevel, serverPlayer);
        }
    }
}
