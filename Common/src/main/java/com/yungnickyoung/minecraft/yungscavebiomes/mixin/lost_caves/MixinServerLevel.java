package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
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

/**
 * Attaches a SandstormServerData to each ServerLevel and ticks it.
 */
@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements ISandstormServerDataProvider {
    @Unique
    private SandstormServerData sandstormServerData;

    @Shadow
    public abstract DimensionDataStorage getDataStorage();

    protected MixinServerLevel(WritableLevelData $$0, ResourceKey<Level> $$1, RegistryAccess $$2, Holder<DimensionType> $$3, Supplier<ProfilerFiller> $$4, boolean $$5, boolean $$6, long $$7, int $$8) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }

    @Override
    @Unique
    public SandstormServerData getSandstormServerData() {
        return sandstormServerData;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void yungscavebiomes_initSandstorm(MinecraftServer $$0, Executor $$1, LevelStorageSource.LevelStorageAccess $$2, ServerLevelData $$3, ResourceKey $$4, LevelStem $$5, ChunkProgressListener $$6, boolean $$7, long $$8, List $$9, boolean $$10, RandomSequences $$11, CallbackInfo ci) {
        this.sandstormServerData = this.getDataStorage().computeIfAbsent(
                (compoundTag) -> new SandstormServerData((ServerLevel) (Object) this, compoundTag),
                () -> new SandstormServerData((ServerLevel) (Object) this),
                "sandstorms");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        this.sandstormServerData.tick();
    }
}
