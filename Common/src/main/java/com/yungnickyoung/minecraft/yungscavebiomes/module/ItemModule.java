package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class ItemModule {
    @AutoRegister("ice_cube_spawn_egg")
    public static AutoRegisterItem ICE_CUBE_SPAWN_EGG = AutoRegisterItem.of(() -> new SpawnEggItem(EntityModule.ICE_CUBE, 10798332, 15002876,
            new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get())));
}
