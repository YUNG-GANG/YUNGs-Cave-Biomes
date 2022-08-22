package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;

public class ParticleTypeModule {
    public static ParticleType<BlockParticleOption> ANCIENT_DUST = new ParticleType<>(false, BlockParticleOption.DESERIALIZER) {
        @Override
        public Codec<BlockParticleOption> codec() {
            return BlockParticleOption.codec(this);
        }
    };

    public static void init() {
        Services.REGISTRY.registerParticleType(new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "ancient_dust"), ANCIENT_DUST);
    }
}
