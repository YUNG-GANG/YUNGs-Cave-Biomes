package com.yungnickyoung.minecraft.yungscavebiomes.data;

public interface ISandstormServerData {
    int SANDSTORM_DURATION = 200;
    int SANDSTORM_COOLDOWN = 200;

    boolean isSandstormActive();
    int getSandstormTime();
    long getSandstormSeed();

    void startSandstorm();
    void stopSandstorm();
}
