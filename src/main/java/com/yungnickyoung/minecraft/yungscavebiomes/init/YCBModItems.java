package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class YCBModItems {
    public static final Item ICE_CUBE_SPAWN_EGG = new SpawnEggItem(YCBModEntities.ICE_CUBE, 10798332, 15002876, new FabricItemSettings().group(CreativeModeTab.TAB_MISC));

    public static void init() {
        Registry.register(Registry.ITEM, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube_spawn_egg"), ICE_CUBE_SPAWN_EGG);
    }
}
