package com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class IceCubeLeapGoal extends Goal {
    private final IceCubeEntity iceCube;
    private LivingEntity target;
    private final float jumpHeight;

    public IceCubeLeapGoal(IceCubeEntity iceCube, float jumpHeight) {
        this.iceCube = iceCube;
        this.jumpHeight = jumpHeight;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.iceCube.onGround();
    }

    @Override
    public boolean canUse() {
        if (this.iceCube.isVehicle()) {
            return false;
        } else {
            this.target = this.iceCube.getTarget();
            if (this.target == null) {
                return false;
            } else {
                double dist = this.iceCube.distanceToSqr(this.target);
                double velocity = this.iceCube.getDeltaMovement().multiply(1, 0, 1).length();

                if (dist - velocity > 16.0) {
                    return false;
                } else if (!this.iceCube.onGround()) {
                    return false;
                }
            }
        }

        return this.iceCube.getRandom().nextInt(reducedTickDelay(5)) == 0 && !this.iceCube.getLeaping();
    }

    @Override
    public void start() {
        Vec3 velocity = this.iceCube.getDeltaMovement();
        Vec3 dirToTarget = new Vec3(this.target.getX() - this.iceCube.getX(), 0.0, this.target.getZ() - this.iceCube.getZ());
        if (dirToTarget.lengthSqr() > 1.0E-7) {
            dirToTarget = dirToTarget.normalize().scale(0.4).add(velocity.scale(0.2));
        }

        this.iceCube.setDeltaMovement(dirToTarget.x, this.jumpHeight, dirToTarget.z);
        this.iceCube.setLeaping(true);
    }
}
