package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class SnapperStrollGoal extends WaterAvoidingRandomStrollGoal {
    private final SandSnapperEntity sandSnapper;
    private final double submergedSpeedModifier;

    public SnapperStrollGoal(SandSnapperEntity sandSnapper, double speedModifier, double submergedSpeedModifier) {
        super(sandSnapper, speedModifier);
        this.sandSnapper = sandSnapper;
        this.submergedSpeedModifier = submergedSpeedModifier;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.sandSnapper.isSubmerged()) {
            this.sandSnapper.getNavigation().setSpeedModifier(this.submergedSpeedModifier * this.speedModifier);
        } else {
            this.sandSnapper.getNavigation().setSpeedModifier(this.speedModifier);
        }
    }
}
