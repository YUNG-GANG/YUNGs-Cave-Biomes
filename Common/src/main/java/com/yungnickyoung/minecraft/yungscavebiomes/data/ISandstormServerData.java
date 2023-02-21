package com.yungnickyoung.minecraft.yungscavebiomes.data;

public interface ISandstormServerData {
    boolean isSandstormActive();
    int getSandstormTime();
    long getSandstormSeed();

    void startSandstorm();
    void stopSandstorm();
}
