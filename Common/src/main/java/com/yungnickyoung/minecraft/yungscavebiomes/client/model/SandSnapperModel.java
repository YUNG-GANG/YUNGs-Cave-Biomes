package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SandSnapperModel extends GeoModel<SandSnapperEntity> {
    private static final ResourceLocation MODEL = YungsCaveBiomesCommon.id("geo/sand_snapper/sand_snapper.geo.json");
    private static final ResourceLocation TEXTURE = YungsCaveBiomesCommon.id("textures/entity/sand_snapper/sand_snapper.png");
    private static final ResourceLocation ANIMATION = YungsCaveBiomesCommon.id("animations/sand_snapper/sand_snapper.animation.json");

    @Override
    public ResourceLocation getModelResource(SandSnapperEntity sandSnapper) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(SandSnapperEntity sandSnapper) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(SandSnapperEntity sandSnapper) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(SandSnapperEntity animatable, long instanceId, AnimationState<SandSnapperEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (!animatable.isLookingAtPlayer()) return;

        CoreGeoBone head = this.getAnimationProcessor().getBone("neck");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
