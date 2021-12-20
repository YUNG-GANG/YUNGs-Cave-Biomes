package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.BiomeMaker;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class YCBModBiomes {
    public static final ResourceKey<Biome> ICE_CAVES = register("ice_caves");

    public static void init() {
        register(ICE_CAVES, BiomeMaker.iceCaves());
    }

    private static ResourceKey<Biome> register(String string) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(YungsCaveBiomes.MOD_ID, string));
    }

    private static void register(ResourceKey<Biome> resourceKey, Biome biome) {
        BuiltinRegistries.registerMapping(BuiltinRegistries.BIOME, resourceKey, biome);
    }
}
