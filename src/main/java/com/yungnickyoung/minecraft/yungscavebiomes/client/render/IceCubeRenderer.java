package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IceCubeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IceCubeRenderer extends MobRenderer<IceCubeEntity, IceCubeModel<IceCubeEntity>> {
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(YungsCaveBiomes.MOD_ID, "textures/entity/ice_cube/ice_cube.png");
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube"), "main");

    public IceCubeRenderer(EntityRendererProvider.Context context) {
        super(context, new IceCubeModel<>(context.bakeLayer(LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(IceCubeEntity entity) {
        return RESOURCE_LOCATION;
    }
}
