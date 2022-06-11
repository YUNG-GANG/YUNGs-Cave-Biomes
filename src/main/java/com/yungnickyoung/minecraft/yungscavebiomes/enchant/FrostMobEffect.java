package com.yungnickyoung.minecraft.yungscavebiomes.enchant;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class FrostMobEffect extends InstantenousMobEffect {
    private final int minFreezeTicks;
    private final int additionalFreezeTicks;
    private final int maxFreezeTicks;

    public FrostMobEffect(int minFreezeTicks, int additionalFreezeTicks, int maxFreezeTicks) {
        super(MobEffectCategory.HARMFUL, 0x03c2fc);
        this.minFreezeTicks = minFreezeTicks;
        this.additionalFreezeTicks = additionalFreezeTicks;
        this.maxFreezeTicks = maxFreezeTicks;
    }

    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        // Ignore in spectator and peaceful mode
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.isSpectator() || serverPlayer.getLevel().getDifficulty() == Difficulty.PEACEFUL) {
                return;
            }
        }
        int frozenTicks = Math.max(minFreezeTicks * (amplifier + 1), livingEntity.getTicksFrozen() + additionalFreezeTicks * (amplifier + 1));
        frozenTicks = Math.min(frozenTicks, maxFreezeTicks * (amplifier + 1));
        livingEntity.setTicksFrozen(frozenTicks);
    }

    @Override
    public void applyInstantenousEffect(Entity areaCloud, Entity areaCloudThrower, LivingEntity target, int amplifier, double damage) {
        this.applyEffectTick(target, amplifier);
    }
}
