package com.yungnickyoung.minecraft.yungscavebiomes.mixin.marble_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.BeardifierAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.NoiseChunkAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class MixinNoiseBasedChunkGenerator extends ChunkGenerator {

    @Shadow @Final private NoiseRouter router;

    @Shadow @Final private Aquifer.FluidPicker globalFluidPicker;

    @Shadow @Final protected Holder<NoiseGeneratorSettings> settings;

    @Shadow @Final private long seed;

    public MixinNoiseBasedChunkGenerator(Registry<StructureSet> $$0, Optional<HolderSet<StructureSet>> $$1, BiomeSource $$2) {
        super($$0, $$1, $$2);
    }

    @Inject(method = "createBiomes", at = @At("HEAD"))
    private void yungscavebiomes_captureBiomeRegistry(Registry<Biome> registry, Executor executor, Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunkAccess, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir) {
        NoiseChunk nc = chunkAccess.getOrCreateNoiseChunk(this.router, () -> {
            return BeardifierAccessor.createBeardifier(structureFeatureManager, chunkAccess);
        }, this.settings.value(), this.globalFluidPicker, blender);

        ((NoiseSamplerBiomeHolder) nc).setBiomeSource(this.runtimeBiomeSource);
        ((NoiseSamplerBiomeHolder) nc).setBiomeRegistry(registry);
        ((NoiseSamplerBiomeHolder) nc).setClimateSampler(((NoiseChunkAccessor) nc).callCachedClimateSampler(this.router));
        ((NoiseSamplerBiomeHolder) nc).setWorldSeed(this.seed);
    }


}
