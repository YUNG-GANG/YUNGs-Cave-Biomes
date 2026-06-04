package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.SavedDataStorage;
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
public abstract class ServerLevelMixin extends Level implements ISandstormServerDataProvider {
    protected ServerLevelMixin(final WritableLevelData levelData, final ResourceKey<Level> dimension, final RegistryAccess registryAccess, final Holder<DimensionType> dimensionTypeRegistration, final boolean isClientSide, final boolean isDebug, final long biomeZoomSeed, final int maxChainedNeighborUpdates) {
        super(
                levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed,
                maxChainedNeighborUpdates);
    }

    @Shadow public abstract SavedDataStorage getDataStorage();

    @Unique
    private SandstormServerData sandstormServerData;

    @Override
    @Unique
    public SandstormServerData getSandstormServerData() {
        return sandstormServerData;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void yungscavebiomes_initSandstorm(final MinecraftServer server, final Executor executor, final LevelStorageSource.LevelStorageAccess levelStorage, final ServerLevelData levelData, final ResourceKey dimension, final LevelStem levelStem, final boolean isDebug, final long biomeZoomSeed, final List customSpawners, final boolean tickTime, final CallbackInfo ci) {
        this.sandstormServerData = this.getDataStorage().computeIfAbsent(SandstormServerData.TYPE);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        this.sandstormServerData.tick(_this());
    }

    @Unique
    private ServerLevel _this() {
        return (ServerLevel) (Object) this;
    }
}
