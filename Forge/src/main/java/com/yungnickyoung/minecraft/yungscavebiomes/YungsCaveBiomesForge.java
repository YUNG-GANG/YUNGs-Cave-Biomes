package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.client.YungsCaveBiomesClientForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(YungsCaveBiomesCommon.MOD_ID)
public class YungsCaveBiomesForge {
    public YungsCaveBiomesForge() {
        YungsCaveBiomesCommon.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> YungsCaveBiomesClientForge::init);
    }
}
