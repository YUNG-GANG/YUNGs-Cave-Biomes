package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.google.common.hash.Hashing;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormServerData;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel implements ISandstormServerData {
    @Shadow
    @Final
    private ServerLevelData serverLevelData;
    @Unique
    private boolean isSandstormActive = false;

    @Unique
    private int sandstormTime = SANDSTORM_DURATION;

    @Unique
    private int sandstormCooldown = SANDSTORM_COOLDOWN;

    @Unique
    private long sandstormSeed;

    @Override
    public boolean isSandstormActive() {
        return this.isSandstormActive;
    }

    @Override
    public int getSandstormTime() {
        return this.sandstormTime;
    }

    @Override
    public long getSandstormSeed() {
        return this.sandstormSeed;
    }

    @Override
    public void startSandstorm() {
        YungsCaveBiomesCommon.LOGGER.info("STARTING SANDSTORM");
        this.isSandstormActive = true;
        this.sandstormTime = SANDSTORM_DURATION;
//        this.sandstormCooldown = SANDSTORM_COOLDOWN;
        // Determine seed for this sandstorm & sync sandstorm state to clients
        this.sandstormSeed = Hashing.sha256().hashLong(this.sandstormSeed + this.serverLevelData.getGameTime()).asLong();
        Services.PLATFORM.syncSandstormDataToClients((ServerLevel) (Object) this);
    }

    @Override
    public void stopSandstorm() {
        YungsCaveBiomesCommon.LOGGER.info("STOPPING SANDSTORM");
        this.isSandstormActive = false;
//        this.sandstormTime = SANDSTORM_DURATION;
        this.sandstormCooldown = SANDSTORM_COOLDOWN;
        Services.PLATFORM.syncSandstormDataToClients((ServerLevel) (Object) this);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void yungscavebiomes_initSandstormNoiseServer(MinecraftServer $$0, Executor $$1, LevelStorageSource.LevelStorageAccess $$2, ServerLevelData $$3, ResourceKey $$4, Holder $$5, ChunkProgressListener $$6, ChunkGenerator $$7, boolean $$8, long $$9, List $$10, boolean $$11, CallbackInfo ci) {
        this.sandstormSeed = Hashing.sha256().hashLong($$9 + this.serverLevelData.getGameTime()).asLong();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        if (this.isSandstormActive) {
            // Sandstorm is active -> decrement remaining sandstorm timer
            this.sandstormTime -= 1;

            // Sandstorm time runs out -> reset sandstorm timer & disable sandstorm
            if (this.sandstormTime <= 0) {
                stopSandstorm();
            }
        } else {
            // Sandstorm is not active -> decrement cooldown timer
            this.sandstormCooldown -= 1;

            // Cooldown runs out -> reset cooldown timer & start new sandstorm
            if (this.sandstormCooldown <= 0) {
                startSandstorm();
            }
        }
    }
}
