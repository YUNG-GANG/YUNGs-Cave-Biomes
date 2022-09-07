package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.world.biome.BiomeMaker;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BiomeModuleForge {
    public static DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, YungsCaveBiomesCommon.MOD_ID);

    public static RegistryObject<Biome> FROSTED_CAVES = REGISTRY.register("frosted_caves", BiomeMaker::frostedCaves);
    public static RegistryObject<Biome> MARBLE_CAVES = REGISTRY.register("marble_caves", BiomeMaker::marbleCaves);
    public static RegistryObject<Biome> ANCIENT_CAVES = REGISTRY.register("ancient_caves", BiomeMaker::ancientCaves);

    public static void init() {
        REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
