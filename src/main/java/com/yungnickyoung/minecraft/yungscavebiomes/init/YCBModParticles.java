package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class YCBModParticles {
    public static final SimpleParticleType ANCIENT_DUST = FabricParticleTypes.simple(true);

    public static void init() {
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ancient_dust"), ANCIENT_DUST);
    }
}
