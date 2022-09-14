package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungscavebiomes.client.model.IceCubeModel;
import com.yungnickyoung.minecraft.yungscavebiomes.client.particle.FallingAncientDustParticle;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IcicleProjectileRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class YungsCaveBiomesClientForge {
    public static void init() {
        YungsCaveBiomesClientCommon.init(); // Run loader-independent client-side logic
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerLayerDefinitions);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(YungsCaveBiomesClientForge::registerParticleFactories);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockModule.ICICLE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.FROST_LILY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.RARE_ICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.ICE_SHEET.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.PRICKLY_PEACH_CACTUS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.POTTED_PRICKLY_PEACH_CACTUS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.PRICKLY_VINES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockModule.PRICKLY_VINES_PLANT.get(), RenderType.cutout());
        });
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IceCubeRenderer.LAYER_LOCATION, IceCubeModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeModule.ICE_CUBE.get(), IceCubeRenderer::new);
        event.registerEntityRenderer(EntityTypeModule.ICICLE.get(), IcicleProjectileRenderer::new);
    }

    private static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleTypeModule.ANCIENT_DUST.get(), FallingAncientDustParticle.Provider::new);
    }
}