package com.yungnickyoung.minecraft.yungscavebiomes.sandstorm;

import com.google.common.hash.Hashing;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.ServerLevelAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Data class for storing sandstorm data on the server.
 * An instance of this data is attached to each ServerLevel and is responsible for managing sandstorm state in that level.
 */
public class SandstormServerData extends SavedData {
    private static final int SYNC_INTERVAL = 60; // Force sync to clients every 3 seconds

    /**
     * The server level this data is associated with.
     */
    private final ServerLevel serverLevel;

    /**
     * Time since last sync to clients, in ticks.
     * We use this to sync to clients every few seconds, to ensure that the client and server are in sync.
     */
    private int timeSinceSync;

    /**
     * Whether the sandstorm in the attached ServerLevel is currently active.
     */
    private boolean isSandstormActive = false;

    /**
     * How long the current sandstorm has been active for in ticks, if active.
     */
    private int currSandstormTicks;

    /**
     * How long until the next sandstorm can start in ticks, if not active.
     */
    private int cooldownTicks;

    /**
     * Total duration of the current sandstorm, in ticks.
     * Note that this is the total duration, from start to finish, not how long the current sandstorm has been
     * active for (use sandstormTime for that).
     */
    private int totalSandstormDurationTicks;

    /**
     * Total cooldown time between sandstorms, in ticks.
     * Note that this is the total cooldown time, not how long until the next sandstorm can start
     * (use sandstormCooldown for that).
     */
    private int totalSandstormCooldownTicks;

    /**
     * Random seed for the current sandstorm.
     * Every sandstorm has a unique seed.
     * This is used for syncing sandstorm particle wind direction to clients.
     */
    private long sandstormSeed;

    public SandstormServerData(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
        this.resetSandstormTimeAndTotalDuration();
        this.resetSandstormCooldownAndTotalCoolDown();
        this.setDirty();
    }

    public SandstormServerData(ServerLevel serverLevel, CompoundTag compoundTag) {
        this(serverLevel);
        this.isSandstormActive = compoundTag.getBoolean("isSandstormActive");
        this.sandstormSeed = compoundTag.getLong("sandstormSeed");
        this.currSandstormTicks = compoundTag.getInt("sandstormTime");
        this.cooldownTicks = compoundTag.getInt("sandstormCooldown");
        this.totalSandstormDurationTicks = compoundTag.getInt("totalSandstormDuration");
        this.totalSandstormCooldownTicks = compoundTag.getInt("totalSandstormCooldown");
    }

    /**
     * Starts a new sandstorm.
     */
    public void start() {
        YungsCaveBiomesCommon.LOGGER.debug("Starting sandstorm in {}", serverLevel.dimension().location());

        // Determine new sandstorm duration and mark sandstorm as active
        resetSandstormTimeAndTotalDuration();
        this.sandstormSeed = Hashing.sha256()
                .hashLong(this.sandstormSeed + ((ServerLevelAccessor) serverLevel).getServerLevelData().getGameTime())
                .asLong();
        this.isSandstormActive = true;

        syncToClients();
    }

    /**
     * Stops the current sandstorm.
     */
    public void stop() {
        YungsCaveBiomesCommon.LOGGER.debug("STOPPING SANDSTORM in {}", serverLevel.dimension().location());

        // Initialize new cooldown and mark sandstorm as inactive
        resetSandstormCooldownAndTotalCoolDown();
        this.isSandstormActive = false;

        syncToClients();
    }

    public void tick() {
        if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("Sandstorm {} >> {} / {} time, {} / {} cooldown",
                    this.serverLevel.dimension().location(),
                    this.currSandstormTicks, this.totalSandstormDurationTicks,
                    this.cooldownTicks, this.totalSandstormCooldownTicks);
        }

        ++this.timeSinceSync;

        if (this.isSandstormActive) {
            // Sandstorm is active -> decrement remaining sandstorm timer
            this.currSandstormTicks -= 1;

            // Sandstorm time runs out -> reset sandstorm timer & disable sandstorm
            if (this.currSandstormTicks <= 0) {
                this.stop();
            }
        } else {
            // Sandstorm is not active -> decrement cooldown timer
            this.cooldownTicks -= 1;

            // Cooldown runs out -> reset cooldown timer & start new sandstorm
            if (this.cooldownTicks <= 0) {
                this.start();
            }
        }

        // Sync to clients every few seconds
        if (this.timeSinceSync > SYNC_INTERVAL) {
            syncToClients();
            if (YungsCaveBiomesCommon.DEBUG_LOG) {
                YungsCaveBiomesCommon.LOGGER.info("Force syncing sandstorm...");
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putBoolean("isSandstormActive", this.isSandstormActive);
        compoundTag.putLong("sandstormSeed", this.sandstormSeed);
        compoundTag.putInt("sandstormTime", this.currSandstormTicks);
        compoundTag.putInt("sandstormCooldown", this.cooldownTicks);
        compoundTag.putInt("totalSandstormDuration", this.totalSandstormDurationTicks);
        compoundTag.putInt("totalSandstormCooldown", this.totalSandstormCooldownTicks);
        return compoundTag;
    }

    /**
     * Resets the sandstorm total duration to a new value.
     * This new value is randomly selected between the min and max sandstorm duration values in the config.
     * The sandstorm time is then initialized to this new total duration.
     */
    private void resetSandstormTimeAndTotalDuration() {
        this.totalSandstormDurationTicks = Mth.randomBetweenInclusive(
                this.serverLevel.getRandom(),
                YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration,
                YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration) * 20;
        this.currSandstormTicks = totalSandstormDurationTicks;
    }

    /**
     * Resets the sandstorm cooldown to a new value.
     * This new value is randomly selected between the min and max time between sandstorms values in the config.
     * The sandstorm cooldown is then initialized to this new total cooldown.
     */
    private void resetSandstormCooldownAndTotalCoolDown() {
        this.totalSandstormCooldownTicks = Mth.randomBetweenInclusive(
                this.serverLevel.getRandom(),
                YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms,
                YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms) * 20;
        this.cooldownTicks = totalSandstormCooldownTicks;
    }

    private void syncToClients() {
        Services.PLATFORM.syncSandstormDataToClients(this);
        this.timeSinceSync = 0;
        this.setDirty();
    }

    public ServerLevel getServerLevel() {
        return this.serverLevel;
    }

    public boolean isSandstormActive() {
        return this.isSandstormActive;
    }

    public int getCurrSandstormTicks() {
        return this.currSandstormTicks;
    }

    public long getSeed() {
        return this.sandstormSeed;
    }

    public int getTotalSandstormDurationTicks() {
        return this.totalSandstormDurationTicks;
    }
}
