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
		// Here, I typically call static 'init' methods from classes in the 'init' package.
		// See any of my mods (esp. more recent ones) to see how I typically split up mod init classes.
		// Feel free to modify this structure or use one that you are more comfortable with.
		YCBModConfig.init();
		YCBModBlocks.init();

		YCBModFeatures.init();
		YCBModConfiguredFeatures.init();
		YCBModPlacedFeatures.init();

		YCBModBiomes.init();
	}
}
