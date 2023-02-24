package com.yungnickyoung.minecraft.yungscavebiomes.data;

public interface ISandstormServerData {
    boolean isSandstormActive();
    int getSandstormTime();
    long getSandstormSeed();
    int getTotalSandstormDuration();

    void startSandstorm();
    void stopSandstorm();
}
