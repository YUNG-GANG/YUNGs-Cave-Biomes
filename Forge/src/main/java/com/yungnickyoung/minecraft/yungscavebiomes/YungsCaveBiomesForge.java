package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.client.YungsCaveBiomesClientForge;
import com.yungnickyoung.minecraft.yungscavebiomes.event.PlayerJoinHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(YungsCaveBiomesCommon.MOD_ID)
public class YungsCaveBiomesForge {
    public YungsCaveBiomesForge() {
        YungsCaveBiomesCommon.init();
        MinecraftForge.EVENT_BUS.addListener(PlayerJoinHandler::onPlayerJoin);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> YungsCaveBiomesClientForge::init);
    }
}
