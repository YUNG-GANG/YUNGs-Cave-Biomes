package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class IceCubeRandomDirectionGoal extends Goal {
    private final IceCubeEntity iceCube;
    private float chosenDegrees;
    private int nextRandomizeTime;

    public IceCubeRandomDirectionGoal(IceCubeEntity iceCube) {
        this.iceCube = iceCube;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.iceCube.getTarget() == null
                && (this.iceCube.isOnGround() || this.iceCube.isInWater() || this.iceCube.isInLava() || this.iceCube.hasEffect(MobEffects.LEVITATION))
                && this.iceCube.getMoveControl() instanceof IceCubeEntity.IceCubeMoveControl;
    }

    @Override
    public void tick() {
        if (--this.nextRandomizeTime <= 0) {
            this.nextRandomizeTime = this.adjustedTickDelay(40 + this.iceCube.getRandom().nextInt(60));
            this.chosenDegrees = this.iceCube.getRandom().nextInt(360);
        }
        ((IceCubeEntity.IceCubeMoveControl)this.iceCube.getMoveControl()).setDirection(this.chosenDegrees, false);
    }
}
