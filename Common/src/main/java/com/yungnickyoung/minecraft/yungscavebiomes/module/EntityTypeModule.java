package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlockEntityType;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterEntityType;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.entity.MobCategory;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class EntityTypeModule {
    /* Entities */
    @AutoRegister("ice_cube")
    public static AutoRegisterEntityType<IceCubeEntity> ICE_CUBE = AutoRegisterEntityType
            .of(() -> AutoRegisterEntityType.Builder
                    .of(IceCubeEntity::new, MobCategory.MONSTER)
                    .sized(1.5f, 1.5f)
                    .clientTrackingRange(10)
                    .build())
            .attributes(IceCubeEntity::createAttributes);

    @AutoRegister("icicle")
    public static AutoRegisterEntityType<IcicleProjectileEntity> ICICLE = AutoRegisterEntityType
            .of(() -> AutoRegisterEntityType.Builder
                    .of(IcicleProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build());

    /* BlockEntities */
    @AutoRegister("rare_ice")
    public static AutoRegisterBlockEntityType<RareIceBlockEntity> RARE_ICE = AutoRegisterBlockEntityType
            .of(() -> AutoRegisterBlockEntityType.Builder
                    .of(RareIceBlockEntity::new, BlockModule.RARE_ICE.get())
                    .build(null));
}
