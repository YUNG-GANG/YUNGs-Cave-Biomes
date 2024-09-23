package com.yungnickyoung.minecraft.yungscavebiomes.event;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public class PlayerJoinHandler {
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer && event.getLevel() instanceof ServerLevel serverLevel) {
            SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) serverLevel).getSandstormServerData();
            Services.PLATFORM.syncSandstormDataToPlayer(sandstormServerData, serverPlayer);
        }
    }
}
