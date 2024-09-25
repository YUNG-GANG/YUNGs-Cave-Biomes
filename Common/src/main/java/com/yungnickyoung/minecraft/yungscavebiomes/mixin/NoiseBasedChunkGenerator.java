package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.NoiseChunkAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.StructureManagerAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGenerator extends ChunkGenerator {
    @Shadow
    @Final
    private Holder<NoiseGeneratorSettings> settings;

    @Shadow
    protected abstract NoiseChunk createNoiseChunk(ChunkAccess p_224257_, StructureManager p_224258_, Blender p_224259_, RandomState p_224260_);

    public NoiseBasedChunkGenerator(BiomeSource $$0) {
        super($$0);
    }

    /**
     * Captures biome registry and other data and stores it in the NoiseChunk for later use.
     */
    @Inject(method = "doCreateBiomes", at = @At("RETURN"))
    private void yungscavebiomes_captureBiomeRegistry(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess, CallbackInfo ci) {
        NoiseChunk nc = chunkAccess.getOrCreateNoiseChunk(ca -> this.createNoiseChunk(ca, structureManager, blender, randomState));

        ((NoiseSamplerBiomeHolder) nc).setBiomeSource(this.biomeSource);
        ((NoiseSamplerBiomeHolder) nc).setBiomeRegistry(((StructureManagerAccessor) structureManager).getLevel().registryAccess().registryOrThrow(Registries.BIOME));
        ((NoiseSamplerBiomeHolder) nc).setClimateSampler(((NoiseChunkAccessor) nc).callCachedClimateSampler(randomState.router(), this.settings.value().spawnTarget()));
        ((NoiseSamplerBiomeHolder) nc).setWorldSeed(((StructureManagerAccessor) structureManager).getWorldOptions().seed());
    }
}
