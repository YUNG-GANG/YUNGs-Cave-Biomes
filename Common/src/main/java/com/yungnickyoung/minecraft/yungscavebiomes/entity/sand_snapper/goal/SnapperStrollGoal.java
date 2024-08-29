package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class SnapperStrollGoal extends WaterAvoidingRandomStrollGoal {
    private final SandSnapperEntity sandSnapper;

    public SnapperStrollGoal(SandSnapperEntity sandSnapper, double speedModifier) {
        super(sandSnapper, speedModifier);
        this.sandSnapper = sandSnapper;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.sandSnapper.isSubmerged()) {
            this.sandSnapper.getNavigation().setSpeedModifier(2.0f * this.speedModifier);
        }
    }
}
