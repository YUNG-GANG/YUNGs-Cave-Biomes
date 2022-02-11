package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.init.*;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YungsCaveBiomes implements ModInitializer {
	public static final String MOD_ID = "yungscavebiomes";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		YCBModConfig.init();
		YCBModBlocks.init();
		YCBModItems.init();
		YCBModEntities.init();
		YCBModSounds.init();
		YCBModFeatures.init();
		YCBModConfiguredFeatures.init();
		YCBModPlacedFeatures.init();
		YCBModBiomes.init();
	}
}
