package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements ISandstormClientDataProvider {
    @Unique
    private final SandstormClientData sandstormClientData = new SandstormClientData();

    protected ClientLevelMixin(final WritableLevelData levelData, final ResourceKey<Level> dimension, final RegistryAccess registryAccess, final Holder<DimensionType> dimensionTypeRegistration, final boolean isClientSide, final boolean isDebug, final long biomeZoomSeed, final int maxChainedNeighborUpdates) {
        super(
                levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed,
                maxChainedNeighborUpdates);
    }

    @Override
    @Unique
    public SandstormClientData getSandstormClientData() {
        return sandstormClientData;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        sandstormClientData.tick();
    }

    /**
     * Spawns sandstorm particles around the player.
     */
    @Inject(method = "doAnimateTick", at = @At("TAIL"))
    private void yungscavebiomes_addSandstormParticles(int x, int y, int z, int maxDistance, RandomSource random, Block markerTarget, BlockPos.MutableBlockPos pos, CallbackInfo ci) {
        sandstormClientData.addSandstormParticles(((ClientLevel) (Object) this), x, y, z, maxDistance, random, pos);
    }

    /**
     * Adds extra sandstorm particles that spawn further away than particles normally do.
     */
    @Inject(method = "animateTick", at = @At("TAIL"))
    private void yungscavebiomes_addExtraSandstormParticles(int x, int y, int z, CallbackInfo ci) {
        sandstormClientData.addExtraSandstormParticles(((ClientLevel) (Object) this), x, y, z);
    }
}
