package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class SandSnapperModel extends GeoModel<SandSnapperEntity> {
    private static final Identifier MODEL = YungsCaveBiomesCommon.id("sand_snapper/sand_snapper");
    private static final Identifier TEXTURE = YungsCaveBiomesCommon.id("textures/entity/sand_snapper/sand_snapper.png");
    private static final Identifier ANIMATION = YungsCaveBiomesCommon.id("sand_snapper/sand_snapper");

    @Override public Identifier getModelResource(final GeoRenderState renderState) {
        return MODEL;
    }

    @Override public Identifier getTextureResource(final GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(SandSnapperEntity sandSnapper) {
        return ANIMATION;
    }
}
