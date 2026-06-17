package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import com.yungnickyoung.minecraft.yungscavebiomes.block.BrittleSandstoneBlock;
import net.minecraft.Optionull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FallingAncientDustParticle extends SingleQuadParticle {
    private final float rotSpeed;
    private final SpriteSet sprites;

    FallingAncientDustParticle(ClientLevel clientLevel, double xo, double yo, double zo, float r, float g, float b, SpriteSet spriteSet, final RandomSource randomSource) {
        super(clientLevel, xo, yo, zo, spriteSet.first());
        this.sprites = spriteSet;
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
//        this.quadSize *= 0.67499995f;
        int k = Mth.randomBetweenInclusive(randomSource, 256, 512);
        this.lifetime = (int) Math.max((float) k, 1.0f);
        this.age = Mth.randomBetweenInclusive(randomSource, 0, 96);
        this.setSpriteFromAge(spriteSet);
        this.rotSpeed = (randomSource.nextFloat() - 0.5f) * 0.1f;
        this.roll = randomSource.nextFloat() * ((float) Math.PI * 2);
    }

    @Override protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float) this.age + f) / (float) this.lifetime * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }
        this.setSpriteFromAge(this.sprites);
        this.oRoll = this.roll;
        this.roll += (float) Math.PI * this.rotSpeed * 2.0f;
        if (this.onGround) {
            this.roll = 0.0f;
            this.oRoll = 0.0f;
        }
        this.move(this.xd, this.yd, this.zd);
        this.yd -= 0.003f;
        this.yd = Math.max(this.yd, -0.14f);
    }

    public static class Provider implements ParticleProvider<BlockParticleOption> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        @Nullable
        public Particle createParticle(BlockParticleOption blockParticleOption, @NotNull ClientLevel clientLevel,
                                       double xo, double yo, double zo, double dx, double dy, double dz, RandomSource randomSource) {
            BlockState blockState = blockParticleOption.getState();
            if (!blockState.isAir() && blockState.getRenderShape() == RenderShape.INVISIBLE) {
                return null;
            }
            BlockPos blockPos = BlockPos.containing(xo, yo, zo);
            int color = Optionull.mapOrDefault(Minecraft.getInstance().getBlockColors().getTintSource(blockState, 0),
                    ts -> ts.colorInWorld(blockState, clientLevel,  blockPos),
                    0xFFFFFFFF);
            if (blockState.getBlock() instanceof BrittleSandstoneBlock) {
                color = ((BrittleSandstoneBlock) blockState.getBlock()).getDustColor();
            }
            float r = (float) (color >> 16 & 0xFF) / 255.0f;
            float g = (float) (color >> 8 & 0xFF) / 255.0f;
            float b = (float) (color & 0xFF) / 255.0f;
            return new FallingAncientDustParticle(clientLevel, xo, yo, zo, r, g, b, this.sprite, randomSource);
        }
    }
}
