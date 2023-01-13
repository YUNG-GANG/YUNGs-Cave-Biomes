package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.mojang.math.Vector3f;
import com.yungnickyoung.minecraft.yungsapi.math.Vector2f;
import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends Level implements ISandstormData {
    @Unique
    public float sandstormTime = 1000;

    @Unique
    private long seed;

    @Override
    public Vector2f getSandstormDirection() {
        float x = OpenSimplex2S.noise3_ImproveXZ(this.seed, sandstormTime, 0, 0);
        float z = OpenSimplex2S.noise3_ImproveXZ(this.seed, sandstormTime, 0, 10000);
        return new Vector2f(x, z);
    }

    @Shadow
    public abstract void addParticle(ParticleOptions p_104706_, double p_104707_, double p_104708_, double p_104709_, double p_104710_, double p_104711_, double p_104712_);

    protected MixinClientLevel(WritableLevelData $$0, ResourceKey<Level> $$1, Holder<DimensionType> $$2, Supplier<ProfilerFiller> $$3, boolean $$4, boolean $$5, long $$6) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void yungscavebiomes_initSandstormNoise(ClientPacketListener $$0, ClientLevel.ClientLevelData $$1, ResourceKey $$2, Holder $$3, int $$4, int $$5, Supplier $$6, LevelRenderer $$7, boolean $$8, long $$9, CallbackInfo ci) {
        this.seed = $$9;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        sandstormTime -= 0.001f;
        if (sandstormTime <= 0) {
            sandstormTime = 1000;
        }
    }

    /**
     * Adds extra sandstorm particles that spawn further away than particles normally do.
     */
    @Inject(method = "animateTick", at = @At("TAIL"))
    private void yungscavebiomes_spawnExtraSandstormParticles(int x, int y, int z, CallbackInfo ci) {
        Random random = new Random();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        if (!Minecraft.getInstance().player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get())) {
            return;
        }

        for (int i = 0; i < 20; ++i) {
            this.addSandstormParticle(x, y, z, 32, 64, random, mutable);
        }
    }

    /**
     * Spawns sandstorm particles around the player.
     */
    @Inject(method = "doAnimateTick", at = @At("TAIL"))
    private void yungscavebiomes_addSandstormParticles2(int x, int y, int z, int maxDistance, Random random, Block markerTarget, BlockPos.MutableBlockPos pos, CallbackInfo ci) {
        if (Minecraft.getInstance().player.hasEffect(MobEffectModule.BUFFETED_EFFECT.get()) && random.nextDouble() < 0.03) {
            int particleX = x + random.nextInt(maxDistance) - this.random.nextInt(maxDistance);
            int particleY = y + random.nextInt(maxDistance) - this.random.nextInt(maxDistance);
            int particleZ = z + random.nextInt(maxDistance) - this.random.nextInt(maxDistance);
            pos.set(particleX, particleY, particleZ);
            BlockState blockState = this.getBlockState(pos);
            if (blockState.isAir()) {
                this.addParticle((ParticleOptions) ParticleTypeModule.SANDSTORM.get(),
                        (double) pos.getX() + this.random.nextDouble(),
                        (double) pos.getY() + this.random.nextDouble(),
                        (double) pos.getZ() + this.random.nextDouble(),
                        0.0, 0.0, 0.0);
            }
        }
    }

    @Unique
    private void addSandstormParticle(int x, int y, int z, int minDistance, int maxDistance, Random random, BlockPos.MutableBlockPos pos) {
        float particleX = random.nextFloat() - random.nextFloat();
        float particleY = random.nextFloat() - random.nextFloat();
        float particleZ = random.nextFloat() - random.nextFloat();
        Vector3f vec3f = new Vector3f(particleX, particleY, particleZ);
        vec3f.mul(Mth.randomBetween(random, minDistance, maxDistance));
        pos.set(x + vec3f.x(), y + vec3f.y(), z + vec3f.z());
        BlockState blockState = this.getBlockState(pos);
        if (blockState.isAir()) {
            this.addParticle((ParticleOptions) ParticleTypeModule.SANDSTORM.get(), true,
                    (double) pos.getX() + this.random.nextDouble(),
                    (double) pos.getY() + this.random.nextDouble(),
                    (double) pos.getZ() + this.random.nextDouble(),
                    0.0, 0.0, 0.0);
        }
    }
}
