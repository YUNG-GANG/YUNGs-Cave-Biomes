package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SpriteResourceLoader.class)
public interface SpriteResourceLoaderAccessor {
    @Accessor
    List<SpriteSource> getSources();
}
