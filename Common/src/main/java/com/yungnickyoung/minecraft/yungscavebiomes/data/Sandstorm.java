package com.yungnickyoung.minecraft.yungscavebiomes.data;

import com.google.common.hash.Hashing;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.ServerLevelAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class Sandstorm extends SavedData {
    public static final int SANDSTORM_DURATION = 300 * 20;
    public static final int SANDSTORM_COOLDOWN = 300 * 20;

    private final ServerLevel serverLevel;
    private int tick;
    public boolean isSandstormActive = false;
    public int sandstormTime = SANDSTORM_DURATION;
    public int sandstormCooldown = SANDSTORM_COOLDOWN;
    public long sandstormSeed;

    public Sandstorm(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
        this.setDirty();
    }

    public void start() {
        //        YungsCaveBiomesCommon.LOGGER.info("STARTING SANDSTORM {}", this.dimensionType());
        this.isSandstormActive = true;
        this.sandstormTime = SANDSTORM_DURATION;
//        this.sandstormCooldown = SANDSTORM_COOLDOWN;
        // Determine seed for this sandstorm & sync sandstorm state to clients
        this.sandstormSeed = Hashing.sha256().hashLong(this.sandstormSeed + ((ServerLevelAccessor) serverLevel).getServerLevelData().getGameTime()).asLong();
        Services.PLATFORM.syncSandstormDataToClients(serverLevel);
        this.setDirty();
    }

    public void stop() {
        //        YungsCaveBiomesCommon.LOGGER.info("STOPPING SANDSTORM");
        this.isSandstormActive = false;
//        this.sandstormTime = SANDSTORM_DURATION;
        this.sandstormCooldown = SANDSTORM_COOLDOWN;
        Services.PLATFORM.syncSandstormDataToClients(serverLevel);
        this.setDirty();
    }

    public void tick() {
        ++this.tick;

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

        if (this.tick % 200 == 0) {
            this.setDirty();
        }
    }

    public static Sandstorm load(ServerLevel serverLevel, CompoundTag compoundTag) {
        Sandstorm sandstorm = new Sandstorm(serverLevel);
        sandstorm.isSandstormActive = compoundTag.getBoolean("isSandstormActive");
        sandstorm.sandstormSeed = compoundTag.getLong("sandstormSeed");
        sandstorm.sandstormTime = compoundTag.getInt("sandstormTime");
        sandstorm.sandstormCooldown = compoundTag.getInt("sandstormCooldown");
        return sandstorm;
    }

    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putBoolean("isSandstormActive", this.isSandstormActive);
        compoundTag.putLong("sandstormSeed", this.sandstormSeed);
        compoundTag.putInt("sandstormTime", this.sandstormTime);
        compoundTag.putInt("sandstormCooldown", this.sandstormCooldown);
        return compoundTag;
    }
}
