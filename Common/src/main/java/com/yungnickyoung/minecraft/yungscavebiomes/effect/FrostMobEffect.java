package com.yungnickyoung.minecraft.yungscavebiomes.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.InstantaneousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class FrostMobEffect extends InstantaneousMobEffect {
    private final int minFreezeTicks;
    private final int additionalFreezeTicks;
    private final int maxFreezeTicks;

    public FrostMobEffect(int minFreezeTicks, int additionalFreezeTicks, int maxFreezeTicks) {
        super(MobEffectCategory.HARMFUL, 0x03c2fc);
        this.minFreezeTicks = minFreezeTicks;
        this.additionalFreezeTicks = additionalFreezeTicks;
        this.maxFreezeTicks = maxFreezeTicks;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, @NotNull LivingEntity livingEntity, int amplifier) {
        // Ignore in spectator and peaceful mode
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.isSpectator() || level.getDifficulty() == Difficulty.PEACEFUL) {
                return true;
            }
        }
        int frozenTicks = Math.max(minFreezeTicks * (amplifier + 1), livingEntity.getTicksFrozen() + additionalFreezeTicks * (amplifier + 1));
        frozenTicks = Math.min(frozenTicks, maxFreezeTicks * (amplifier + 1));
        livingEntity.setTicksFrozen(frozenTicks);
        return true;
    }
}
