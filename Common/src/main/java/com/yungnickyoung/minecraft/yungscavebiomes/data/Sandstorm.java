package com.yungnickyoung.minecraft.yungscavebiomes.data;

import com.google.common.hash.Hashing;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.ServerLevelAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.saveddata.SavedData;

public class Sandstorm extends SavedData {
    private final ServerLevel serverLevel;
    private int timeSinceSync;
    public boolean isSandstormActive = false;
    public int sandstormTime;
    public int sandstormCooldown;
    public long sandstormSeed;
    public int totalSandstormDuration;
    public int totalSandstormCooldown;

    public Sandstorm(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
        this.resetSandstormDuration();
        this.resetSandstormCooldown();
        this.setDirty();
    }

    public void start() {
        if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("STARTING SANDSTORM in {}", serverLevel.dimension().location());
        }

        this.isSandstormActive = true;
        resetSandstormDuration();
//        resetSandstormCooldown();

        // Determine seed for this sandstorm & sync sandstorm state to clients
        this.sandstormSeed = Hashing.sha256().hashLong(this.sandstormSeed + ((ServerLevelAccessor) serverLevel).getServerLevelData().getGameTime()).asLong();
        Services.PLATFORM.syncSandstormDataToClients(serverLevel);
        this.timeSinceSync = 0;
        this.setDirty();
    }

    public void stop() {
        if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("STOPPING SANDSTORM in {}", serverLevel.dimension().location());
        }

        this.isSandstormActive = false;
        resetSandstormCooldown();
//        resetSandstormDuration();

        Services.PLATFORM.syncSandstormDataToClients(serverLevel);
        this.timeSinceSync = 0;
        this.setDirty();
    }

    public void tick() {
        if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("Sandstorm {} >> {} / {} time, {} / {} cooldown",
                    this.serverLevel.dimension().location(),
                    this.sandstormTime, this.totalSandstormDuration,
                    this.sandstormCooldown, this.totalSandstormCooldown);
        }

        ++this.timeSinceSync;

        if (this.isSandstormActive) {
            // Sandstorm is active -> decrement remaining sandstorm timer
            this.sandstormTime -= 1;

            // Sandstorm time runs out -> reset sandstorm timer & disable sandstorm
            if (this.sandstormTime <= 0) {
                this.stop();
            }
        } else {
            // Sandstorm is not active -> decrement cooldown timer
            this.sandstormCooldown -= 1;

            // Cooldown runs out -> reset cooldown timer & start new sandstorm
            if (this.sandstormCooldown <= 0) {
                this.start();
            }
        }

        // Sync to clients every few seconds
        if (this.timeSinceSync > 60) {
            Services.PLATFORM.syncSandstormDataToClients(serverLevel);
            this.timeSinceSync = 0;
            this.setDirty();
            if (YungsCaveBiomesCommon.DEBUG_LOG) {
                YungsCaveBiomesCommon.LOGGER.info("Force syncing sandstorm...");
            }
        }
    }

    public static Sandstorm load(ServerLevel serverLevel, CompoundTag compoundTag) {
        Sandstorm sandstorm = new Sandstorm(serverLevel);
        sandstorm.isSandstormActive = compoundTag.getBoolean("isSandstormActive");
        sandstorm.sandstormSeed = compoundTag.getLong("sandstormSeed");
        sandstorm.sandstormTime = compoundTag.getInt("sandstormTime");
        sandstorm.sandstormCooldown = compoundTag.getInt("sandstormCooldown");
        sandstorm.totalSandstormDuration = compoundTag.getInt("totalSandstormDuration");
        sandstorm.totalSandstormCooldown = compoundTag.getInt("totalSandstormCooldown");
        return sandstorm;
    }

    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putBoolean("isSandstormActive", this.isSandstormActive);
        compoundTag.putLong("sandstormSeed", this.sandstormSeed);
        compoundTag.putInt("sandstormTime", this.sandstormTime);
        compoundTag.putInt("sandstormCooldown", this.sandstormCooldown);
        compoundTag.putInt("totalSandstormDuration", this.totalSandstormDuration);
        compoundTag.putInt("totalSandstormCooldown", this.totalSandstormCooldown);
        return compoundTag;
    }

    private void resetSandstormDuration() {
        this.totalSandstormDuration = Mth.randomBetweenInclusive(
                this.serverLevel.getRandom(),
                YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration,
                YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration) * 20;
        this.sandstormTime = totalSandstormDuration;
    }

    private void resetSandstormCooldown() {
        this.totalSandstormCooldown = Mth.randomBetweenInclusive(
                this.serverLevel.getRandom(),
                YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms,
                YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms) * 20;
        this.sandstormCooldown = totalSandstormCooldown;
    }
}
