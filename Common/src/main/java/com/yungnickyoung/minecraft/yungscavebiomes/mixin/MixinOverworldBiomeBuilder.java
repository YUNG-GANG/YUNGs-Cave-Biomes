package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.yungscavebiomes.init.YCBModBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class MixinOverworldBiomeBuilder {
    @Shadow protected abstract void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float depth, ResourceKey<Biome> biome);

    @Shadow @Final private Climate.Parameter FULL_RANGE;

    @Shadow @Final private Climate.Parameter FROZEN_RANGE;

    @Shadow @Final private Climate.Parameter UNFROZEN_RANGE;

    @Shadow @Final private Climate.Parameter[] temperatures;

    @Inject(method = "addUndergroundBiomes", at = @At("HEAD"))
    private void addYCBBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        this.addUndergroundBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, BiomeModule.ICE_CAVES);
        this.addUndergroundBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(-1.05F, -0.19F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, BiomeModule.MARBLE_CAVES);
        this.addUndergroundBiome(consumer, this.temperatures[4], this.FULL_RANGE, Climate.Parameter.span(0.2F, 0.7F), Climate.Parameter.span(0.2F, 1.0F), this.FULL_RANGE, 0.0F, BiomeModule.DESERT_CAVES);
    }

    @Inject(method = "addUndergroundBiome", at = @At("HEAD"), cancellable = true)
    private void modifyDripstone(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float depth, ResourceKey<Biome> biome, CallbackInfo ci) {
        if (biome == Biomes.DRIPSTONE_CAVES) {
            temperature = this.UNFROZEN_RANGE;
            consumer.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.2F, 0.9F), weirdness, depth), biome));
            ci.cancel();
        }
    }
}
