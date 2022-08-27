package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NoiseChunk.class)
public interface NoiseChunkAccessor {
    @Invoker("cachedClimateSampler")
    Climate.Sampler callCachedClimateSampler(NoiseRouter noiseRouter);
}
