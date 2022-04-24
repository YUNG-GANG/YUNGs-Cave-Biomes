package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class IceCubeAttackGoal extends Goal {
    private final IceCubeEntity iceCube;
    private int growTiredTimer;

    public IceCubeAttackGoal(IceCubeEntity iceCube) {
        this.iceCube = iceCube;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = this.iceCube.getTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!this.iceCube.canAttack(livingEntity)) {
            return false;
        }
        return this.iceCube.getMoveControl() instanceof IceCubeEntity.IceCubeMoveControl;
    }

    @Override
    public void start() {
        this.growTiredTimer = IceCubeAttackGoal.reducedTickDelay(300);
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity = this.iceCube.getTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!this.iceCube.canAttack(livingEntity)) {
            return false;
        }
        return --this.growTiredTimer > 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.iceCube.getTarget();
        if (livingEntity != null) {
            this.iceCube.lookAt(livingEntity, 10.0f, 10.0f);
        }
        ((IceCubeEntity.IceCubeMoveControl)this.iceCube.getMoveControl()).setDirection(this.iceCube.getYRot(), true);
    }
}
