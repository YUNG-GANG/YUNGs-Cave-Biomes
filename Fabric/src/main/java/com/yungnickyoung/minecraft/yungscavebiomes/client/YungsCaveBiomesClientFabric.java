package com.yungnickyoung.minecraft.yungscavebiomes.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.FallingAncientDustParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.IceShatterParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.LostCavesAmbientParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.SandstormParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IcicleProjectileRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleFabric;
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
        NetworkModuleFabric.registerS2CPackets();

        // Block rendering
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.ICICLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.FROST_LILY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.RARE_ICE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.ICE_SHEET.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.PRICKLY_PEACH_CACTUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.POTTED_PRICKLY_PEACH_CACTUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.PRICKLY_VINES.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockModule.PRICKLY_VINES_PLANT.get(), RenderType.cutout());

        // Entity rendering
        EntityRendererRegistry.register(EntityTypeModule.ICICLE.get(), IcicleProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(IceCubeRenderer.LAYER_LOCATION, IceCubeModel::createBodyLayer);
        EntityRendererRegistry.register(EntityTypeModule.ICE_CUBE.get(), IceCubeRenderer::new);

        // Particle rendering
        ParticleFactoryRegistry.getInstance().register(ParticleTypeModule.ANCIENT_DUST.get(), FallingAncientDustParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleTypeModule.SANDSTORM.get(), SandstormParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleTypeModule.LOST_CAVES_AMBIENT.get(), LostCavesAmbientParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleTypeModule.ICE_SHATTER.get(), IceShatterParticle.Provider::new);
    }
}