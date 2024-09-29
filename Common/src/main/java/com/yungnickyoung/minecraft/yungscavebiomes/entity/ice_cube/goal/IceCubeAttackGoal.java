package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class IceCubeAttackGoal extends MeleeAttackGoal {
    private final float attackReachMultiplier;

    public IceCubeAttackGoal(PathfinderMob mob, double speedModifier, float attackReachMultiplier, boolean trackWhenUnseen) {
        super(mob, speedModifier, trackWhenUnseen);
        this.attackReachMultiplier = attackReachMultiplier;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * attackReachMultiplier * this.mob.getBbWidth() * attackReachMultiplier + target.getBbWidth();
    }
}
