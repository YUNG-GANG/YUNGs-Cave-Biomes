package com.yungnickyoung.minecraft.yungscavebiomes.sandstorm;

import com.mojang.math.Vector3f;

public interface ISandstormClientData {
    void getSandstormDirection(double worldX, double worldY, double worldZ, Vector3f output);
    boolean isSandstormActive();
    int getSandstormTime();

    void setSandstormActive(boolean isActive);
    void setSandstormTime(int time);
    void setSandstormSeed(long seed);
    void setTotalSandstormDuration(int time);
}
