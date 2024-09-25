package com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.util.DistributionUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Manages sandstorm state on the ClientLevel.
 * This class does not actually manage the sandstorm itself; it only manages the client-side representation of the sandstorm
 * for rendering purposes.
 * No data is written to/from disk on the client.
 */
public class SandstormClientData {
    private static final double SANDSTORM_NOISE_FREQUENCY_Y_RELATIVE = 1.0 / 3.0;
    private static final double SANDSTORM_NOISE_FREQUENCY_XZ = 1.0 / 128.0;
    private static final double SANDSTORM_NOISE_FREQUENCY_Y = SANDSTORM_NOISE_FREQUENCY_Y_RELATIVE * SANDSTORM_NOISE_FREQUENCY_XZ;

    private static final double SANDSTORM_NOISE_FREQUENCY_TIME = 1.0 / 768.0;

    private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_Y_RELATIVE = 1.0f / 3.0f;
    private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_XZ = 0.9f;
    private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_Y = SANDSTORM_PARTICLE_SPEED_SCALAR_Y_RELATIVE * SANDSTORM_PARTICLE_SPEED_SCALAR_XZ;

    private static final long SEED_2_OFFSET = 0x635D783F323BF442L;

    /**
     * Whether the sandstorm is currently active.
     */
    private boolean isSandstormActive = false;

    // Sandstorm time is basically just used as a frequency for noise-based particle directions on the client
    private int sandstormTime = 0;

    private int totalSandstormDuration = 0;

    private long sandstormSeed;

    /**
     * Gets the speed vector for a sandstorm particle at the given world position.
     *
     * @param worldX                  The world X position of the particle.
     * @param worldY                  The world Y position of the particle.
     * @param worldZ                  The world Z position of the particle.
     * @param prevParticleSpeedVector The previous speed vector of the particle.
     * @return The new speed vector of the particle.
     */
    public Vector3f getSandstormParticleSpeedVector(double worldX, double worldY, double worldZ, Vector3f prevParticleSpeedVector) {
        Vector3f output = new Vector3f(prevParticleSpeedVector);

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

        // Smoothing during sandstorm start transition
        if (this.sandstormTime > this.totalSandstormDuration - 20) {
            output.mul(
                    (this.totalSandstormDuration - sandstormTime) / 20f,
                    (this.totalSandstormDuration - sandstormTime) / 20f,
                    (this.totalSandstormDuration - sandstormTime) / 20f
            );
        }

        return output;
    }

    public void tick() {
        if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("Sandstorm CLIENT {} / {} time", this.sandstormTime, this.totalSandstormDuration);
        }

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
    public void addSandstormParticles(ClientLevel clientLevel, int x, int y, int z, int maxDistance, RandomSource random, BlockPos.MutableBlockPos pos) {
        if (!this.isSandstormActive) {
            return;
        }

        // Random chance of spawning particle.
        // Chance is lower when sandstorm is first starting up.
        double chance = Mth.clamp(Mth.lerp((this.totalSandstormDuration - sandstormTime) / 20.0, 0, 0.03), 0, 0.03);
        if (random.nextDouble() > chance) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        boolean isPlayerInLostCaves = clientLevel.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES);
        if (!isPlayerInLostCaves) {
            return;
        }

        this.addSandstormParticle(clientLevel, x, y, z, false, maxDistance, random, pos);
    }

    /**
     * Adds extra sandstorm particles that spawn further away than particles normally do.
     */
    public void addExtraSandstormParticles(ClientLevel clientLevel, int x, int y, int z) {
        if (!YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles) {
            return;
        }

        if (!isSandstormActive) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        boolean isPlayerInLostCaves = clientLevel.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES);
        if (!isPlayerInLostCaves) {
            return;
        }

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 667; ++i) {
            // Random chance of spawning particle.
            // Chance is lower when sandstorm is first starting up.
            double chance = Mth.clamp(Mth.lerp((this.totalSandstormDuration - sandstormTime) / 20.0, 0, 0.03), 0, 0.03);
            if (clientLevel.getRandom().nextDouble() > chance) {
                continue;
            }

            this.addSandstormParticle(clientLevel, x, y, z, true, 64, clientLevel.getRandom(), mutable);
        }
    }

    private void addSandstormParticle(ClientLevel clientLevel, int x, int y, int z, boolean overrideLimiter, int maxDistance, RandomSource random, BlockPos.MutableBlockPos pos) {
        Vector3d particlePos = DistributionUtils.ellipsoidCenterBiasedSpread(maxDistance, maxDistance, random, Vector3d::new);
        particlePos.x += x;
        particlePos.y += y;
        particlePos.z += z;
        pos.set(Mth.floor(particlePos.x), Mth.floor(particlePos.y), Mth.floor(particlePos.z));
        BlockState blockState = clientLevel.getBlockState(pos);
        if (blockState.isAir()) {
            clientLevel.addParticle((ParticleOptions) ParticleTypeModule.SANDSTORM.get(), overrideLimiter,
                    particlePos.x, particlePos.y, particlePos.z,
                    0.0, 0.0, 0.0);
        }
    }

    public boolean isSandstormActive() {
        return isSandstormActive;
    }

    public void setSandstormActive(boolean sandstormActive) {
        this.isSandstormActive = sandstormActive;
    }

    public void setSandstormTime(int time) {
        this.sandstormTime = time;
    }

    public void setSandstormSeed(long sandstormSeed) {
        this.sandstormSeed = sandstormSeed;
    }

    public void setTotalSandstormDuration(int totalSandstormDuration) {
        this.totalSandstormDuration = totalSandstormDuration;
    }
}
