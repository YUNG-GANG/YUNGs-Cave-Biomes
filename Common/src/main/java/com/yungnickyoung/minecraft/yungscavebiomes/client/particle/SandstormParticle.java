package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import com.yungnickyoung.minecraft.yungsapi.math.Vector2f;
import com.yungnickyoung.minecraft.yungscavebiomes.data.ISandstormData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class SandstormParticle extends TextureSheetParticle {
    private final float rotSpeed;
    private final SpriteSet sprites;

    SandstormParticle(ClientLevel clientLevel, double xo, double yo, double zo, float r, float g, float b, SpriteSet spriteSet) {
        super(clientLevel, xo, yo, zo);
        this.sprites = spriteSet;
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        this.lifetime = Mth.randomBetweenInclusive(clientLevel.getRandom(), 32, 48);
        this.age = Mth.randomBetweenInclusive(clientLevel.getRandom(), 0, 32);
        this.setSpriteFromAge(spriteSet);
        this.rotSpeed = (float) Math.random() * 0.15f + 0.05f;
        this.roll = (float) Math.random() * ((float) Math.PI * 2);
        this.gravity = 0;
        ISandstormData sandstormData = ((ISandstormData) clientLevel);
        Vector2f angle = sandstormData.getSandstormDirection();
        float speed = 1.5f;
        setParticleSpeed(angle.x * speed, 0, angle.y * speed);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
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

        ISandstormData sandstormData = ((ISandstormData) this.level);
        Vector2f angle = sandstormData.getSandstormDirection();
        float speed = 2.0f;
        setParticleSpeed(angle.x * speed, 0, angle.y * speed);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        @Nullable
        public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double xo, double yo, double zo, double dx, double dy, double dz) {
            int color = 0xd1b482;
            float r = (float) (color >> 16 & 0xFF) / 255.0f;
            float g = (float) (color >> 8 & 0xFF) / 255.0f;
            float b = (float) (color & 0xFF) / 255.0f;
            return new SandstormParticle(clientLevel, xo, yo, zo, r, g, b, this.spriteSet);
        }
    }
}
