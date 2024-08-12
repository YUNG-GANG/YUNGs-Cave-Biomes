package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class IceCubeAttackGoal extends MeleeAttackGoal {
    public IceCubeAttackGoal(PathfinderMob mob, double speedModifier, boolean trackWhenUnseen) {
        super(mob, speedModifier, trackWhenUnseen);
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + target.getBbWidth();
    }
}
