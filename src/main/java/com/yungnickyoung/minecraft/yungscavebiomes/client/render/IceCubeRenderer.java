package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class IceCubeRenderer extends MobRenderer<IceCubeEntity, IceCubeModel<IceCubeEntity>> {
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(YungsCaveBiomes.MOD_ID, "textures/entity/ice_cube/ice_cube.png");
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube"), "main");

    public IceCubeRenderer(EntityRendererProvider.Context context) {
        super(context, new IceCubeModel<>(context.bakeLayer(LAYER_LOCATION)), 0.7f);
    }

    @Override
    protected void scale(IceCubeEntity iceCube, PoseStack poseStack, float f) {
        poseStack.scale(0.999f, 0.999f, 0.999f);
        poseStack.translate(0.0, 0.001f, 0.0);
        float iceCubeSize = 1;
        float i = Mth.lerp(f, iceCube.oSquish, iceCube.squish) / (iceCubeSize * 0.5f + 1.0f);
        float j = 1.0f / (i + 1.0f);
        poseStack.scale(j * iceCubeSize, 1.0f / j * iceCubeSize, j * iceCubeSize);
    }

    @Override
    public ResourceLocation getTextureLocation(IceCubeEntity entity) {
        return RESOURCE_LOCATION;
    }
}
