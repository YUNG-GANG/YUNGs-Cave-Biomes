package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class YCBModParticles {
    public static ParticleType<BlockParticleOption> ANCIENT_DUST;

    public static void init() {
        ANCIENT_DUST = register("ancient_dust", BlockParticleOption.DESERIALIZER, BlockParticleOption::codec);
    }

    private static <T extends ParticleOptions> ParticleType<T> register(String name, ParticleOptions.Deserializer<T> deserializer, final Function<ParticleType<T>, Codec<T>> function) {
        return Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(YungsCaveBiomes.MOD_ID, name), new ParticleType<T>(false, deserializer){

            @Override
            public Codec<T> codec() {
                return function.apply(this);
            }
        });
    }
}
