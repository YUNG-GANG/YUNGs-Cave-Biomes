package com.yungnickyoung.minecraft.yungscavebiomes.client;

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
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class YungsCaveBiomesClientForge {
    public static void init() {
        YungsCaveBiomesClientCommon.init(); // Run loader-independent client-side logic
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerLayerDefinitions);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerParticleFactories);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerOverlays);
    }

    private static void registerOverlays(RegisterGuiOverlaysEvent event) {
        IGuiOverlay buffetedOverlay = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            gui.setupOverlayRenderState(true, false);
            BuffetedOverlay.render(partialTick, screenWidth, screenHeight);
        };
        event.registerAboveAll("buffeted", buffetedOverlay);
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
        Minecraft.getInstance().particleEngine.register(ParticleTypeModule.ANCIENT_DUST.get(), FallingAncientDustParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypeModule.SANDSTORM.get(), SandstormParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypeModule.LOST_CAVES_AMBIENT.get(), LostCavesAmbientParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypeModule.ICE_SHATTER.get(), IceShatterParticle.Provider::new);
    }
}