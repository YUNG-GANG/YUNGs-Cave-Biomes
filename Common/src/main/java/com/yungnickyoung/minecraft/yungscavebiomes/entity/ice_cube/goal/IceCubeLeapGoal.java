package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;

public class IceCubeLeapGoal extends LeapAtTargetGoal {
    private final IceCubeEntity iceCube;

    public IceCubeLeapGoal(IceCubeEntity iceCube, float jumpHeight) {
        super(iceCube, jumpHeight);
        this.iceCube = iceCube;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.iceCube.getLeaping();
    }

    @Override
    public void start() {
        super.start();
        this.iceCube.setLeaping(true);
    }
}
