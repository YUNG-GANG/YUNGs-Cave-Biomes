package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.event.PlayerJoinHandler;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModuleNeoForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModuleNeoForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleNeoForge;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TerraBlenderModuleNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = YungsCaveBiomesCommon.MOD_ID)
public class YungsCaveBiomesNeoForge {
    public YungsCaveBiomesNeoForge(IEventBus eventBus, ModContainer container) {
        YungsCaveBiomesCommon.init();
        ConfigModuleNeoForge.init(eventBus, container);
        NetworkModuleNeoForge.init(eventBus);
        TerraBlenderModuleNeoForge.init(eventBus);
        DecoratedPotPatternsModuleNeoForge.init(eventBus);
        NeoForge.EVENT_BUS.addListener(PlayerJoinHandler::onPlayerJoin);
    }
}
