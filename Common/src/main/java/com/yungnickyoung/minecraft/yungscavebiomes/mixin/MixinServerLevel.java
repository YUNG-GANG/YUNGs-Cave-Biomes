package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerData;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.Sandstorm;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements ISandstormServerData {
    @Unique private Sandstorm sandstorm;

    @Shadow public abstract DimensionDataStorage getDataStorage();

    protected MixinServerLevel(WritableLevelData $$0, ResourceKey<Level> $$1, Holder<DimensionType> $$2, Supplier<ProfilerFiller> $$3, boolean $$4, boolean $$5, long $$6) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
    }

    @Override
    @Unique
    public boolean isSandstormActive() {
        return this.sandstorm.isSandstormActive;
    }

    @Override
    @Unique
    public int getSandstormTime() {
        return this.sandstorm.sandstormTime;
    }

    @Override
    @Unique
    public long getSandstormSeed() {
        return this.sandstorm.sandstormSeed;
    }

    @Override
    @Unique
    public int getTotalSandstormDuration() {
        return this.sandstorm.totalSandstormDuration;
    }

    @Override
    @Unique
    public void startSandstorm() {
        this.sandstorm.start();
    }

    @Override
    @Unique
    public void stopSandstorm() {
        this.sandstorm.stop();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void yungscavebiomes_initServerLevel(MinecraftServer $$0, Executor $$1, LevelStorageSource.LevelStorageAccess $$2, ServerLevelData $$3, ResourceKey $$4, Holder $$5, ChunkProgressListener $$6, ChunkGenerator $$7, boolean $$8, long $$9, List $$10, boolean $$11, CallbackInfo ci) {
        this.sandstorm = this.getDataStorage().computeIfAbsent(
                (compoundTag) -> Sandstorm.load((ServerLevel) (Object) this, compoundTag),
                () -> new Sandstorm((ServerLevel) (Object) this),
                "sandstorms");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        this.sandstorm.tick();
    }
}
