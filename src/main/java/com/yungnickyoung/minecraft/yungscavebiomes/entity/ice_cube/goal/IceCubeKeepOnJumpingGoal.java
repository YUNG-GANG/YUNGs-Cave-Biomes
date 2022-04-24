package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class IceCubeKeepOnJumpingGoal extends Goal {
    private final IceCubeEntity iceCube;

    public IceCubeKeepOnJumpingGoal(IceCubeEntity iceCube) {
        this.iceCube = iceCube;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !this.iceCube.isPassenger();
    }

    @Override
    public void tick() {
        ((IceCubeEntity.IceCubeMoveControl)this.iceCube.getMoveControl()).setMovingWithSpeed(1.0);
    }
}
