package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterParticleType;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class ParticleTypeModule {
    @AutoRegister("ancient_dust")
    public static AutoRegisterParticleType<BlockParticleOption> ANCIENT_DUST = AutoRegisterParticleType
            .of(BlockParticleType::new);

    @AutoRegister("ice_shatter")
    public static AutoRegisterParticleType<SimpleParticleType> ICE_SHATTER = AutoRegisterParticleType
            .simple();

    private static class BlockParticleType extends ParticleType<BlockParticleOption> {
        protected BlockParticleType() {
            super(false, BlockParticleOption.DESERIALIZER);
        }

        @Override
        public Codec<BlockParticleOption> codec() {
            return BlockParticleOption.codec(this);
        }
    }
}
