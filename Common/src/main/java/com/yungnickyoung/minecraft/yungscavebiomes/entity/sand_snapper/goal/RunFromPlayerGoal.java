package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;

public class RunFromPlayerGoal extends AvoidEntityGoal<Player> {
    private final SandSnapperEntity sandSnapper;

    public RunFromPlayerGoal(SandSnapperEntity sandSnapper, float dist, double walkSpeedModifier, double runSpeedModifier) {
        super(sandSnapper, Player.class, dist, walkSpeedModifier, runSpeedModifier);
        this.sandSnapper = sandSnapper;
    }

    @Override
    public boolean canUse() {
        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        return super.canUse() && !sandstormServerData.isSandstormActive();
    }

    @Override
    public boolean canContinueToUse() {
        SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) this.sandSnapper.level).getSandstormServerData();

        return super.canContinueToUse() && !sandstormServerData.isSandstormActive();
    }
}
