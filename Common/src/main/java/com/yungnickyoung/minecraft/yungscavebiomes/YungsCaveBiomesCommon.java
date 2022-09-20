package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCreativeTab;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class YungsCaveBiomesCommon {
	public static final String MOD_ID = "yungscavebiomes";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ConfigModule CONFIG = new ConfigModule();

    public static boolean FROSTED_CAVES_ENABLED = true;
    public static boolean LOST_CAVES_ENABLED = false;
    public static boolean MARBLE_CAVES_ENABLED = false;

    @AutoRegister("general")
    public static AutoRegisterCreativeTab TAB_CAVEBIOMES = new AutoRegisterCreativeTab.Builder()
            .iconItem(() -> new ItemStack(BlockModule.LAYERED_ANCIENT_SANDSTONE.get()))
            .build();

	public static void init() {
		Services.MODULES.loadModules();
	}
}
