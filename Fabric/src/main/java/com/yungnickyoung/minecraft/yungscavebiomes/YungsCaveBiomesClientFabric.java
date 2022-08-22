package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.FallingAncientDustParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class YungsCaveBiomesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        YungsCaveBiomesClientCommon.init(); // Run loader-independent client-side logic
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.ICICLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.FROST_LILY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.RARE_ICE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.ICE_SHEET.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.PRICKLY_PEAR_CACTUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.POTTED_PRICKLY_PEAR_CACTUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.PRICKLY_VINES.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.PRICKLY_VINES_PLANT.get(), RenderType.cutout());
        EntityRendererRegistry.register(EntityModule.ICE_CUBE, IceCubeRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(IceCubeRenderer.LAYER_LOCATION, IceCubeModel::createBodyLayer);
        ParticleFactoryRegistry.getInstance().register(ParticleTypeModule.ANCIENT_DUST, FallingAncientDustParticle.Provider::new);
    }
}