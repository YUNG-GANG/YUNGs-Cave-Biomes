package com.yungnickyoung.minecraft.yungscavebiomes.client;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.FallingAncientDustParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.IceShatterParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.LostCavesAmbientParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.SandstormParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.BuffetedOverlay;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IcicleProjectileRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.SandSnapperRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.SuspiciousAncientSandBlockRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@Mod(value = YungsCaveBiomesCommon.MOD_ID, dist = Dist.CLIENT)
public class YungsCaveBiomesClientNeoForge {
    public YungsCaveBiomesClientNeoForge(IEventBus eventBus, ModContainer container) {
        YungsCaveBiomesClientCommon.init();
        eventBus.addListener(YungsCaveBiomesClientNeoForge::registerLayerDefinitions);
        eventBus.addListener(YungsCaveBiomesClientNeoForge::registerRenderers);
        eventBus.addListener(YungsCaveBiomesClientNeoForge::registerParticleFactories);
        eventBus.addListener(YungsCaveBiomesClientNeoForge::registerOverlays);
    }

    private static void registerOverlays(RegisterGuiLayersEvent event) {
        LayeredDraw.Layer buffetedOverlay = BuffetedOverlay.getInstance();
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, YungsCaveBiomesCommon.id("buffeted"), buffetedOverlay);
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IceCubeRenderer.LAYER_LOCATION, IceCubeModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeModule.ICE_CUBE.get(), IceCubeRenderer::new);
        event.registerEntityRenderer(EntityTypeModule.SAND_SNAPPER.get(), SandSnapperRenderer::new);
        event.registerEntityRenderer(EntityTypeModule.ICICLE.get(), IcicleProjectileRenderer::new);
        event.registerBlockEntityRenderer(EntityTypeModule.SUSPICIOUS_ANCIENT_SAND.get(), SuspiciousAncientSandBlockRenderer::new);
    }

    private static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleTypeModule.ANCIENT_DUST.get(), FallingAncientDustParticle.Provider::new);
        event.registerSpriteSet(ParticleTypeModule.SANDSTORM.get(), SandstormParticle.Provider::new);
        event.registerSpriteSet(ParticleTypeModule.LOST_CAVES_AMBIENT.get(), LostCavesAmbientParticle.Provider::new);
        event.registerSpriteSet(ParticleTypeModule.ICE_SHATTER.get(), IceShatterParticle.Provider::new);
    }
}