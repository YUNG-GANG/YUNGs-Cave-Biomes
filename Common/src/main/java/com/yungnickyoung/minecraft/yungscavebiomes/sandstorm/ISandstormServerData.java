package com.yungnickyoung.minecraft.yungscavebiomes.sandstorm;

public interface ISandstormServerData {
    boolean isSandstormActive();
    int getSandstormTime();
    long getSandstormSeed();
    int getTotalSandstormDuration();

    void startSandstorm();
    void stopSandstorm();
}
