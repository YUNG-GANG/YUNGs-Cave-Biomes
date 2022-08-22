package com.yungnickyoung.minecraft.yungscavebiomes.services;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityModule;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void registerBiome(ResourceKey<Biome> resourceKey, Biome biome) {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, resourceKey, biome);
    }

    @Override
    public void registerEntityType(ResourceLocation resourceLocation, EntityType<?> entityType) {
        Registry.register(Registry.ENTITY_TYPE, resourceLocation, entityType);
    }

    @Override
    public void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(EntityModule.ICE_CUBE, IceCubeEntity.createAttributes());
    }

    @Override
    public void registerFeature(ResourceLocation resourceLocation, Feature<?> feature) {
        Registry.register(Registry.FEATURE, resourceLocation, feature);
    }

    @Override
    public void registerMobEffect(ResourceLocation resourceLocation, MobEffect mobEffect) {
        Registry.register(Registry.MOB_EFFECT, resourceLocation, mobEffect);
    }

    @Override
    public void registerPotion(ResourceLocation resourceLocation, Potion potion) {
        Registry.register(Registry.POTION, resourceLocation, potion);
    }

    @Override
    public void registerParticleType(ResourceLocation resourceLocation, ParticleType<?> particleType) {
        Registry.register(Registry.PARTICLE_TYPE, resourceLocation, particleType);
    }

    @Override
    public void registerSoundEvent(ResourceLocation resourceLocation, SoundEvent soundEvent) {
        Registry.register(Registry.SOUND_EVENT, resourceLocation, soundEvent);
    }
}
