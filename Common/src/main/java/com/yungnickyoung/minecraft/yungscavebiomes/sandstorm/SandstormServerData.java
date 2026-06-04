package com.yungnickyoung.minecraft.yungscavebiomes.sandstorm;

import com.google.common.hash.Hashing;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.ServerLevelAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jetbrains.annotations.NotNull;

import static com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon.id;

/**
 * Data class for storing sandstorm data on the server.
 * An instance of this data is attached to each ServerLevel and is responsible for managing sandstorm state in that level.
 */
public class SandstormServerData extends SavedData {
    private static final int                                          SYNC_INTERVAL = 3 * SharedConstants.TICKS_PER_SECOND;
    private static final Codec<SandstormServerData>                   CODEC         = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("isSandstormActive", false).forGetter(s -> s.isSandstormActive),
            Codec.LONG.optionalFieldOf("sandstormSeed", 0L).forGetter(s -> s.sandstormSeed),
            Codec.INT.optionalFieldOf("sandstormTime", 0).forGetter(s -> s.currSandstormTicks),
            Codec.INT.optionalFieldOf("sandstormCooldown", 0).forGetter(s -> s.cooldownTicks),
            Codec.INT.optionalFieldOf("totalSandstormDuration", 0).forGetter(s -> s.totalSandstormDurationTicks),
            Codec.INT.optionalFieldOf("totalSandstormCooldown", 0).forGetter(s -> s.totalSandstormCooldownTicks)
    ).apply(instance, SandstormServerData::new));
    public static final SavedDataType<? extends SandstormServerData> TYPE          = new SavedDataType<>(
            id("sandstorm"),
            SandstormServerData::new,
            CODEC,
            DataFixTypes.LEVEL);

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

    /**
     * Whether the above values should be reset on next tick.
     */
    private boolean shouldInitialise;

    private SandstormServerData() {
        this.shouldInitialise = true;
    }

    private SandstormServerData(final boolean isSandstormActive, final long sandstormSeed, final int currSandstormTicks, final int cooldownTicks, final int totalSandstormDurationTicks, final int totalSandstormCooldownTicks) {
        this.shouldInitialise = false;
        this.isSandstormActive = isSandstormActive;
        this.sandstormSeed = sandstormSeed;
        this.currSandstormTicks = currSandstormTicks;
        this.cooldownTicks = cooldownTicks;
        this.totalSandstormDurationTicks = totalSandstormDurationTicks;
        this.totalSandstormCooldownTicks = totalSandstormCooldownTicks;
    }

    /**
     * Starts a new sandstorm.
     */
    public void start(ServerLevel level) {
        YungsCaveBiomesCommon.LOGGER.debug("Starting sandstorm in {}", level.dimension().identifier());

        // Determine new sandstorm duration and mark sandstorm as active
        this.resetSandstormTimeAndTotalDuration(level);
        this.sandstormSeed = Hashing.sha256()
                .hashLong(this.sandstormSeed + ((ServerLevelAccessor) level).getServerLevelData().getGameTime())
                .asLong();
        this.isSandstormActive = true;

        this.syncToClients();
    }

    /**
     * Stops the current sandstorm.
     */
    public void stop(ServerLevel level) {
        YungsCaveBiomesCommon.LOGGER.debug("STOPPING SANDSTORM in {}", level.dimension().identifier());

        // Initialize new cooldown and mark sandstorm as inactive
        this.resetSandstormCooldownAndTotalCoolDown(level);
        this.isSandstormActive = false;

        // Trigger sandstorm end criteria for all players in the server level if they are in the Lost Caves biome
        level.players().forEach(player -> {
            if (!player.isSpectator() && level.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES)) {
                CriteriaModule.SANDSTORM_END.trigger(player);
            }
        });

        this.syncToClients();
    }

    public void tick(ServerLevel level) {
        if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("Sandstorm {} >> {} / {} time, {} / {} cooldown",
                    level.dimension().identifier(),
                    this.currSandstormTicks, this.totalSandstormDurationTicks,
                    this.cooldownTicks, this.totalSandstormCooldownTicks);
        }

        if (this.shouldInitialise) {
            this.resetSandstormTimeAndTotalDuration(level);
            this.resetSandstormCooldownAndTotalCoolDown(level);
            this.setDirty();
        }

        ++this.timeSinceSync;

        if (this.isSandstormActive) {
            // Sandstorm is active -> decrement remaining sandstorm timer
            this.currSandstormTicks -= 1;

            // Sandstorm time runs out -> reset sandstorm timer & disable sandstorm
            if (this.currSandstormTicks <= 0) {
                this.stop(level);
            }
        } else {
            // Sandstorm is not active -> decrement cooldown timer
            this.cooldownTicks -= 1;

            // Cooldown runs out -> reset cooldown timer & start new sandstorm
            if (this.cooldownTicks <= 0) {
                this.start(level);
            }
        }

        // Sync to clients every few seconds
        if (this.timeSinceSync > SYNC_INTERVAL) {
            this.syncToClients();
            if (YungsCaveBiomesCommon.DEBUG_LOG) {
                YungsCaveBiomesCommon.LOGGER.info("Force syncing sandstorm...");
            }
        }
    }

    /**
     * Resets the sandstorm total duration to a new value.
     * This new value is randomly selected between the min and max sandstorm duration values in the config.
     * The sandstorm time is then initialized to this new total duration.
     */
    private void resetSandstormTimeAndTotalDuration(ServerLevel level) {
        this.totalSandstormDurationTicks = Mth.randomBetweenInclusive(
                level.getRandom(),
                YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration,
                YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration) * 20;
        this.currSandstormTicks = this.totalSandstormDurationTicks;
    }

    /**
     * Resets the sandstorm cooldown to a new value.
     * This new value is randomly selected between the min and max time between sandstorms values in the config.
     * The sandstorm cooldown is then initialized to this new total cooldown.
     */
    private void resetSandstormCooldownAndTotalCoolDown(ServerLevel level) {
        this.totalSandstormCooldownTicks = Mth.randomBetweenInclusive(
                level.getRandom(),
                YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms,
                YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms) * 20;
        this.cooldownTicks = this.totalSandstormCooldownTicks;
    }

    private void syncToClients() {
        Services.PLATFORM.syncSandstormDataToClients(this);
        this.timeSinceSync = 0;
        this.setDirty();
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
