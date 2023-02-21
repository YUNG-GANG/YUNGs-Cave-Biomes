package com.yungnickyoung.minecraft.yungscavebiomes.event;

import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class PlayerJoinHandler {
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            Services.PLATFORM.syncSandstormDataToPlayer((ServerLevel) event.getWorld(), serverPlayer);
        }
    }
}
