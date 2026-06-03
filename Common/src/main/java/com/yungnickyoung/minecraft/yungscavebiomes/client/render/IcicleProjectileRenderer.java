package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class IcicleProjectileRenderer extends ArrowRenderer<IcicleProjectileEntity, ArrowRenderState> {
    public static final Identifier TEXTURE_LOCATION = YungsCaveBiomesCommon.id("textures/entity/projectiles/icicle.png");

    public IcicleProjectileRenderer(EntityRendererProvider.Context content) {
        super(content);
    }

    @Override public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override protected Identifier getTextureLocation(final ArrowRenderState state) {
        return TEXTURE_LOCATION;
    }
}
