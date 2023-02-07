package com.yungnickyoung.minecraft.yungscavebiomes.data;

import com.yungnickyoung.minecraft.yungsapi.math.Vector2f;

public interface ISandstormClientData {
    Vector2f getSandstormDirection();
    boolean isSandstormActive();
    int getSandstormTime();

    void setSandstormActive(boolean isActive);
    void setSandstormTime(int time);
    void setSandstormSeed(long seed);
}
