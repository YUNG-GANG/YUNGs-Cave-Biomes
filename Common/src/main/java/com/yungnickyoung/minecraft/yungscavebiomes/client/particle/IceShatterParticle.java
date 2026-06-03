package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;


public class IceShatterParticle extends SingleQuadParticle {
    private final float uo;
    private final float vo;

    public IceShatterParticle(ClientLevel clientLevel, double xo, double yo, double zo, RandomSource random) {
        super(clientLevel, xo, yo, zo, 0.0, 0.0, 0.0, Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(Blocks.ICE.defaultBlockState()).sprite());
        this.gravity = 1f;
        this.friction = 0.9f; // acceleration
        this.xd *= random.nextFloat() * 8f;
        this.zd *= random.nextFloat() * 8f;
        this.yd *= random.nextFloat() * 8f;
        this.quadSize /= 5.0F;
        this.lifetime = Mth.randomBetweenInclusive(random, 20, 40);
        this.uo = random.nextFloat();
        this.vo = random.nextFloat();
    }

    @Override protected Layer getLayer() {
        return Layer.OPAQUE_TERRAIN;
    }

    @Override
    protected float getU0() {
        return this.sprite.getU((this.uo + 1.0F) / 4.0F * 16.0F);
    }

    @Override
    protected float getU1() {
        return this.sprite.getU(this.uo / 4.0F * 16.0F);
    }

    @Override
    protected float getV0() {
        return this.sprite.getV(this.vo / 4.0F * 16.0F);
    }

    @Override
    protected float getV1() {
        return this.sprite.getV((this.vo + 1.0F) / 4.0F * 16.0F);
    }

    @Override
    public int getLightCoords(float f) {
        int lightAtPos = super.getLightCoords(f);
        int k = lightAtPos >> 16 & 0xFF;
        return 0xF0 | k << 16;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(final SimpleParticleType simpleParticleType,
                                                 final ClientLevel clientLevel,
                                                 final double xo,
                                                 final double yo,
                                                 final double zo,
                                                 final double dx,
                                                 final double dy,
                                                 final double dz,
                                                 final RandomSource randomSource) {
            return new IceShatterParticle(clientLevel, xo, yo, zo, randomSource);
        }
    }
}
