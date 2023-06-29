package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SandSnapperModel extends AnimatedGeoModel<SandSnapperEntity> {
    private static final ResourceLocation MODEL = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "geo/sand_snapper/sand_snapper.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "textures/entity/sand_snapper/sand_snapper.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "animations/sand_snapper/sand_snapper.animation.json");

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
}
