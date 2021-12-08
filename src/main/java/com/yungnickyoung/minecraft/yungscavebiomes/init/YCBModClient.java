package com.yungnickyoung.minecraft.yungscavebiomes.init;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class YCBModClient {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(YCBModBlocks.ICICLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(YCBModBlocks.FROST_LILY, RenderType.cutout());
    }
}
