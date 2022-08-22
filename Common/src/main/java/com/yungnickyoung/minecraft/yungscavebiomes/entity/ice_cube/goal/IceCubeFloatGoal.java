package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class IceCubeFloatGoal extends Goal {
    private final IceCubeEntity iceCube;

    public IceCubeFloatGoal(IceCubeEntity iceCube) {
        this.iceCube = iceCube;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        iceCube.getNavigation().setCanFloat(true);
    }

    @Override
    public boolean canUse() {
        return (this.iceCube.isInWater() || this.iceCube.isInLava()) && this.iceCube.getMoveControl() instanceof IceCubeEntity.IceCubeMoveControl;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.iceCube.getRandom().nextFloat() < 0.8f) {
            this.iceCube.getJumpControl().jump();
        }
        ((IceCubeEntity.IceCubeMoveControl)this.iceCube.getMoveControl()).setMovingWithSpeed(1.2);
    }
}
