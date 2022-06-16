package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class YCBModEntities {
    public static EntityType<IceCubeEntity> ICE_CUBE = FabricEntityTypeBuilder
            .create(MobCategory.MONSTER, IceCubeEntity::new)
            .dimensions(EntityDimensions.fixed(1.5f, 1.5f))
            .build();

    public static BlockEntityType<RareIceBlockEntity> RARE_ICE  = FabricBlockEntityTypeBuilder
            .create(RareIceBlockEntity::new, YCBModBlocks.RARE_ICE)
            .build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube"), ICE_CUBE);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(YungsCaveBiomes.MOD_ID, "rare_ice"), RARE_ICE);
        FabricDefaultAttributeRegistry.register(ICE_CUBE, IceCubeEntity.createAttributes());
    }
}
