package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SandSnapperModel extends AnimatedGeoModel<SandSnapperEntity> {
    private static final ResourceLocation MODEL = YungsCaveBiomesCommon.id("geo/sand_snapper/sand_snapper.geo.json");
    private static final ResourceLocation TEXTURE = YungsCaveBiomesCommon.id("textures/entity/sand_snapper/sand_snapper.png");
    private static final ResourceLocation ANIMATION = YungsCaveBiomesCommon.id("animations/sand_snapper/sand_snapper.animation.json");

    @Override
    public ResourceLocation getModelLocation(SandSnapperEntity sandSnapper) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(SandSnapperEntity sandSnapper) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SandSnapperEntity sandSnapper) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(SandSnapperEntity animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);

        if (!animatable.isLookingAtPlayer()) return;

        IBone head = this.getAnimationProcessor().getBone("neck");

        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * Mth.DEG_TO_RAD);
            head.setRotationY(extraData.netHeadYaw * Mth.DEG_TO_RAD);
        }
    }
}
