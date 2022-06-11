package com.yungnickyoung.minecraft.yungscavebiomes.init;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;

public class YCBModItems {
    public static final Item ICE_CUBE_SPAWN_EGG = new SpawnEggItem(YCBModEntities.ICE_CUBE, 10798332, 15002876, new FabricItemSettings().group(CreativeModeTab.TAB_MISC));
    public static final MobEffect FROZEN_EFFECT = new InstantenousMobEffect(MobEffectCategory.HARMFUL, 0x03c2fc) {
        public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
            // Ignore in spectator and peaceful mode
            if (livingEntity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.isSpectator() || serverPlayer.getLevel().getDifficulty() == Difficulty.PEACEFUL) {
                    return;
                }
            }
            int frozenTicks = Math.max(200, livingEntity.getTicksFrozen() + 100);
            frozenTicks = Math.min(frozenTicks, 600);
            livingEntity.setTicksFrozen(frozenTicks);
        }

        @Override
        public void applyInstantenousEffect(Entity areaCloud, Entity areaCloudThrower, LivingEntity target, int amplifier, double damage) {
            this.applyEffectTick(target, amplifier);
        }
    };

    public static final Potion FROST_POTION = new Potion(YungsCaveBiomes.MOD_ID + ".frost", new MobEffectInstance(FROZEN_EFFECT));

    public static void init() {
        Registry.register(Registry.ITEM, new ResourceLocation(YungsCaveBiomes.MOD_ID, "ice_cube_spawn_egg"), ICE_CUBE_SPAWN_EGG);
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(YungsCaveBiomes.MOD_ID, "frost"), FROZEN_EFFECT);
        Registry.register(Registry.POTION, new ResourceLocation(YungsCaveBiomes.MOD_ID, "frost"), FROST_POTION);

    }
}
