package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class ItemModule {
//    @AutoRegister("ice_cube_spawn_egg")
//    public static AutoRegisterItem ICE_CUBE_SPAWN_EGG = AutoRegisterItem.of(() ->
//            new SpawnEggItem(
//                    EntityTypeModule.ICE_CUBE.get(), 10798332, 15002876,
//                    new Item.Properties().tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get())));

    @AutoRegister("prickly_peach")
    public static AutoRegisterItem PRICKLY_PEACH_ITEM = AutoRegisterItem.of(() -> new Item(
                new Item.Properties()
                        .tab(YungsCaveBiomesCommon.TAB_CAVEBIOMES.get())
                        .food(new FoodProperties.Builder()
                                .nutrition(2)
                                .saturationMod(0.1F)
                                .build())));
}
