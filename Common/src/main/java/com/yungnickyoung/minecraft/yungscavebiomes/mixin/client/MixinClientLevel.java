package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.yungnickyoung.minecraft.yungsapi.math.Vector2f;
import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.util.DistributionUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
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
import org.spongepowered.asm.mixin.Final;
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
public abstract class MixinClientLevel extends Level implements ISandstormClientData {
    @Unique
    private boolean isSandstormActive = false;

    // Sandstorm time is basically just used as a frequency on the client
    @Unique
    private int sandstormTime = 0;

    @Unique
    private long sandstormSeed;

    private static final double SANDSTORM_NOISE_FREQUENCY_Y_RELATIVE = 1.0 / 3.0;
    private static final double SANDSTORM_NOISE_FREQUENCY_XZ = 1.0 / 128.0;
    private static final double SANDSTORM_NOISE_FREQUENCY_Y =
            SANDSTORM_NOISE_FREQUENCY_Y_RELATIVE * SANDSTORM_NOISE_FREQUENCY_XZ;
    private static final double SANDSTORM_NOISE_FREQUENCY_TIME = 1.0 / 768.0;

    private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_Y_RELATIVE = 1.0f / 3.0f;
    private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_XZ = 0.75f;
    private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_Y =
            SANDSTORM_PARTICLE_SPEED_SCALAR_Y_RELATIVE * SANDSTORM_PARTICLE_SPEED_SCALAR_XZ;

    private static final long SEED_2_OFFSET = 0x635D783F323BF442L;

    @Override
    public void getSandstormDirection(double worldX, double worldY, double worldZ, Vector3f output) {
        Vector4f noise4GradientA = new Vector4f();
        Vector4f noise4GradientB = new Vector4f();

        OpenSimplex2S.noise4Grad_ImproveXYZ_ImproveXZ(this.sandstormSeed,
                worldX * SANDSTORM_NOISE_FREQUENCY_XZ,
                worldY * SANDSTORM_NOISE_FREQUENCY_Y,
                worldZ * SANDSTORM_NOISE_FREQUENCY_XZ,
                this.sandstormTime * SANDSTORM_NOISE_FREQUENCY_TIME,
                noise4GradientA
        );
        OpenSimplex2S.noise4Grad_ImproveXYZ_ImproveXZ(this.sandstormSeed + SEED_2_OFFSET,
                worldX * SANDSTORM_NOISE_FREQUENCY_XZ,
                worldY * SANDSTORM_NOISE_FREQUENCY_Y,
                worldZ * SANDSTORM_NOISE_FREQUENCY_XZ,
                this.sandstormTime * SANDSTORM_NOISE_FREQUENCY_TIME,
                noise4GradientB
        );

        // Cross-product of XYZ component
        output.set(
                (noise4GradientA.y() * noise4GradientB.z() - noise4GradientA.z() * noise4GradientB.y()),
                (noise4GradientA.z() * noise4GradientB.x() - noise4GradientA.x() * noise4GradientB.z()),
                (noise4GradientA.x() * noise4GradientB.y() - noise4GradientA.y() * noise4GradientB.x())
        );

        output.mul(
                SANDSTORM_PARTICLE_SPEED_SCALAR_XZ,
                SANDSTORM_PARTICLE_SPEED_SCALAR_Y,
                SANDSTORM_PARTICLE_SPEED_SCALAR_XZ
        );
        output.normalize();
        output.mul(
                SANDSTORM_PARTICLE_SPEED_SCALAR_XZ,
                SANDSTORM_PARTICLE_SPEED_SCALAR_Y,
                SANDSTORM_PARTICLE_SPEED_SCALAR_XZ
        );
    }

    @Override
    public void setSandstormActive(boolean sandstormActive) {
        this.isSandstormActive = sandstormActive;
    }

    @Override
    public void setSandstormTime(int time) {
        this.sandstormTime = time;
    }

    @Override
    public void setSandstormSeed(long sandstormSeed) {
        this.sandstormSeed = sandstormSeed;
    }

    @Shadow
    public abstract void addParticle(ParticleOptions p_104706_, double p_104707_, double p_104708_, double p_104709_, double p_104710_, double p_104711_, double p_104712_);

    protected MixinClientLevel(WritableLevelData $$0, ResourceKey<Level> $$1, Holder<DimensionType> $$2, Supplier<ProfilerFiller> $$3, boolean $$4, boolean $$5, long $$6) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
        if (this.isSandstormActive) {
            if (this.sandstormTime > 0) {
                // Sandstorm is active -> decrement remaining sandstorm timer
                this.sandstormTime -= 1;
            } else {
                // Sandstorm time runs out -> disable sandstorm
                this.isSandstormActive = false;
            }
        }
    }

    /**
     * Spawns sandstorm particles around the player.
     */
    @Inject(method = "doAnimateTick", at = @At("TAIL"))
    private void yungscavebiomes_addSandstormParticles(int x, int y, int z, int maxDistance, Random random, Block markerTarget, BlockPos.MutableBlockPos pos, CallbackInfo ci) {
        if (!this.isSandstormActive || random.nextDouble() > 0.03) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        boolean playerInSandstorm = this.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES.getResourceKey());
        if (!playerInSandstorm) {
            return;
        }

        this.addSandstormParticle(x, y, z, false, maxDistance, random, pos);
    }

    /**
     * Adds extra sandstorm particles that spawn further away than particles normally do.
     * TODO - config option to disable this for performance reasons
     */
    @Inject(method = "animateTick", at = @At("TAIL"))
    private void yungscavebiomes_addExtraSandstormParticles(int x, int y, int z, CallbackInfo ci) {
        if (!isSandstormActive) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        boolean playerInSandstormBiome = this.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES.getResourceKey());
        if (!playerInSandstormBiome) {
            return;
        }

        Random random = new Random();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 20; ++i) {
            this.addSandstormParticle(x, y, z, true, 64, random, mutable);
        }
    }

    @Unique
    private void addSandstormParticle(int x, int y, int z, boolean overrideLimiter, int maxDistance, Random random, BlockPos.MutableBlockPos pos) {
        Vector3d particlePosition = DistributionUtils.ellipsoidCenterBiasedSpread(maxDistance, maxDistance, random, Vector3d::new);
        particlePosition.x += x;
        particlePosition.y += y;
        particlePosition.z += z;
        pos.set(Mth.fastFloor(particlePosition.x), Mth.fastFloor(particlePosition.y), Mth.fastFloor(particlePosition.z));
        BlockState blockState = this.getBlockState(pos);
        if (blockState.isAir()) {
            this.addParticle((ParticleOptions) ParticleTypeModule.SANDSTORM.get(), overrideLimiter,
                    particlePosition.x, particlePosition.y, particlePosition.z,
                    0.0, 0.0, 0.0);
        }
    }
}
