package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlockEntityType;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class EntityModule {
    /* Entities */
    public static EntityType<IceCubeEntity> ICE_CUBE = EntityType.Builder
            .of(IceCubeEntity::new, MobCategory.MONSTER)
            .sized(1.5f, 1.5f)
            .clientTrackingRange(10)
            .build(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ice_cube").toString());

    /* BlockEntities */
    @AutoRegister("rare_ice")
    public static AutoRegisterBlockEntityType<RareIceBlockEntity> RARE_ICE = AutoRegisterBlockEntityType
            .of(() -> AutoRegisterBlockEntityType.Builder
                    .of(RareIceBlockEntity::new, BlockModule.RARE_ICE.get())
                    .build(null));

    public static void init() {
        Services.REGISTRY.registerEntityType(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ice_cube"), ICE_CUBE);
        Services.REGISTRY.registerEntityAttributes();
    }
}
