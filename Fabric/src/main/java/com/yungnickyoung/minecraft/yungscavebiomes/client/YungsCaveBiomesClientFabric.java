package com.yungnickyoung.minecraft.yungscavebiomes.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.FallingAncientDustParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.IceShatterParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.LostCavesAmbientParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.SandstormParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IcicleProjectileRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.SandSnapperRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.NetworkModuleFabric;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class YungsCaveBiomesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        YungsCaveBiomesClientCommon.init(); // Run loader-independent client-side logic
        NetworkModuleFabric.registerS2CHandlers();

        // Block rendering
        // Block Entity rendering
        BlockEntityRenderers.register(EntityTypeModule.BRUSHABLE.get(), BrushableBlockRenderer::new);

        // Entity rendering
        EntityRenderers.register(EntityTypeModule.ICICLE.get(), IcicleProjectileRenderer::new);
        ModelLayerRegistry.registerModelLayer(IceCubeRenderer.LAYER_LOCATION, IceCubeModel::createBodyLayer);
        EntityRenderers.register(EntityTypeModule.ICE_CUBE.get(), IceCubeRenderer::new);
        EntityRenderers.register(EntityTypeModule.SAND_SNAPPER.get(), ctx -> new SandSnapperRenderer<>(ctx, EntityTypeModule.SAND_SNAPPER.get()));

        // Particle rendering
        ParticleProviderRegistry.getInstance().register(ParticleTypeModule.ANCIENT_DUST.get(), FallingAncientDustParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ParticleTypeModule.SANDSTORM.get(), SandstormParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ParticleTypeModule.LOST_CAVES_AMBIENT.get(), LostCavesAmbientParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ParticleTypeModule.ICE_SHATTER.get(), IceShatterParticle.Provider::new);
    }
}