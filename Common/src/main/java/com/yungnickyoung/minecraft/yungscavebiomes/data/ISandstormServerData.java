package com.yungnickyoung.minecraft.yungscavebiomes.data;

public interface ISandstormServerData {
    int SANDSTORM_DURATION = 10 * 20;
    int SANDSTORM_COOLDOWN = 10 * 20;

    boolean isSandstormActive();
    int getSandstormTime();
    long getSandstormSeed();

    void startSandstorm();
    void stopSandstorm();
}
