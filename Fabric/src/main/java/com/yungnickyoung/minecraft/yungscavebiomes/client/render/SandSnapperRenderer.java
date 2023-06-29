package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.SandSnapperModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SandSnapperRenderer extends GeoEntityRenderer<SandSnapperEntity> {

    public SandSnapperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SandSnapperModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SandSnapperEntity entity) {
        return super.getTextureLocationGeckoLib(entity);
    }

    @Override
    public RenderType getRenderType(SandSnapperEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
