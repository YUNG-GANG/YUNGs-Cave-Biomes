package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IceCubeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class YCBModEntities {
    public static final EntityType<IceCubeEntity> ICE_CUBE = Registry.register(
            Registry.ENTITY_TYPE,
            new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube"),
            FabricEntityTypeBuilder
                    .create(MobCategory.MONSTER, IceCubeEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 1.5f))
                    .build()
    );

    public static void init() {
        FabricDefaultAttributeRegistry.register(ICE_CUBE, IceCubeEntity.createMobAttributes());
    }
}
