package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlockEntityType;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterEntityType;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.SpawnPlacementsAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class EntityTypeModule {
    /* Entities */
    @AutoRegister("ice_cube")
    public static AutoRegisterEntityType<IceCubeEntity> ICE_CUBE = AutoRegisterEntityType
            .of(() -> AutoRegisterEntityType.Builder
                    .of(IceCubeEntity::new, MobCategory.MONSTER)
                    .sized(1.6f, 1.5f)
                    .clientTrackingRange(10)
                    .build())
            .attributes(IceCubeEntity::createAttributes);

    @AutoRegister("sand_snapper")
    public static AutoRegisterEntityType<SandSnapperEntity> SAND_SNAPPER = AutoRegisterEntityType
            .of(() -> AutoRegisterEntityType.Builder
                    .of(SandSnapperEntity::new, MobCategory.MONSTER)
                    .sized(1.4f, 0.5f)
                    .clientTrackingRange(10)
                    .build())
            .attributes(SandSnapperEntity::createAttributes);

    @AutoRegister("icicle")
    public static final AutoRegisterEntityType<IcicleProjectileEntity> ICICLE = AutoRegisterEntityType
            .of(() -> AutoRegisterEntityType.Builder
                    .of(IcicleProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build());

    /* BlockEntities */
    @AutoRegister("rare_ice")
    public static final AutoRegisterBlockEntityType<RareIceBlockEntity> RARE_ICE = AutoRegisterBlockEntityType
            .of(() -> AutoRegisterBlockEntityType.Builder
                    .of(RareIceBlockEntity::new, BlockModule.RARE_ICE.get())
                    .build(null));

    /**
     * Methods with the AutoRegister annotations will be executed after registration.
     *
     * For Fabric, this means the method is executed during mod initialization as normal.
     * For Forge, the method is queued to execute in common setup.
     *
     * Any methods used with the AutoRegister annotation must be static and take no arguments.
     * Note that the annotation value is ignored.
     */
    @AutoRegister("_ignored")
    private static void init() {
        SpawnPlacementsAccessor.callRegister(ICE_CUBE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacementsAccessor.callRegister(SAND_SNAPPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
