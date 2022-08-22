package com.yungnickyoung.minecraft.yungscavebiomes.services;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.function.Supplier;

public interface IRegistryHelper {
    void registerBiome(ResourceKey<Biome> resourceKey, Biome biome);
    void registerEntityType(ResourceLocation resourceLocation, EntityType<?> entityType);
    void registerEntityAttributes();
    void registerFeature(ResourceLocation resourceLocation, Feature<?> feature);
    void registerMobEffect(ResourceLocation resourceLocation, MobEffect mobEffect);
    void registerPotion(ResourceLocation resourceLocation, Potion potion);
    void registerParticleType(ResourceLocation resourceLocation, ParticleType<?> particleType);
    void registerSoundEvent(ResourceLocation resourceLocation, SoundEvent soundEvent);
}
