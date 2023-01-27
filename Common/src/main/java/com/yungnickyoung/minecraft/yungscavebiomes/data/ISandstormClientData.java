package com.yungnickyoung.minecraft.yungscavebiomes.data;

import com.mojang.math.Vector3f;
import com.yungnickyoung.minecraft.yungsapi.math.Vector2f;

public interface ISandstormClientData {
    void getSandstormDirection(double worldX, double worldY, double worldZ, Vector3f output);

    void setSandstormActive(boolean isActive);
    void setSandstormTime(int time);
    void setSandstormSeed(long seed);
}
