package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IcicleProjectileRenderer extends ArrowRenderer<IcicleProjectileEntity> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "textures/entity/projectiles/icicle.png");

    public IcicleProjectileRenderer(EntityRendererProvider.Context content) {
        super(content);
    }

    @Override
    public ResourceLocation getTextureLocation(IcicleProjectileEntity entity) {
        return TEXTURE_LOCATION;
    }
}
