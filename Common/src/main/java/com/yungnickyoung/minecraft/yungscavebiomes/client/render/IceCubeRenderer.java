package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class IceCubeRenderer extends MobRenderer<IceCubeEntity, IceCubeRenderState, IceCubeModel<IceCubeRenderState>> {
    public static final Identifier RESOURCE_LOCATION = YungsCaveBiomesCommon.id("textures/entity/ice_cube/ice_cube.png");
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(YungsCaveBiomesCommon.id("ice_cube"), "main");

    public IceCubeRenderer(EntityRendererProvider.Context context) {
        super(context, new IceCubeModel<IceCubeRenderState>(context.bakeLayer(LAYER_LOCATION)), 0.7f);
    }

    @Override public IceCubeRenderState createRenderState() {
        return new IceCubeRenderState();
    }

    @Override
    public void extractRenderState(final IceCubeEntity entity, final IceCubeRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.size = 1;
        state.squish = Mth.lerp(partialTicks, entity.oSquish, entity.squish);
        state.leaping = entity.getLeaping();
    }

    @Override protected void scale(final IceCubeRenderState state, final PoseStack poseStack) {
        poseStack.scale(0.999f, 0.999f, 0.999f);
        poseStack.translate(0.0, 0.001f, 0.0);
        float iceCubeSize = state.size;
        float i = state.squish / (iceCubeSize * 0.5f + 1.0f);
        float j = 1.0f / (i + 1.0f);
        poseStack.scale(j * iceCubeSize, 1.0f / j * iceCubeSize, j * iceCubeSize);
    }

    @Override public Identifier getTextureLocation(final IceCubeRenderState state) {
        return RESOURCE_LOCATION;
    }
}
