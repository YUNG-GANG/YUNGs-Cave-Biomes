package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import com.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class LostCavesAmbientParticle extends TextureSheetParticle {
    private final float rotSpeed;
    private final SpriteSet sprites;

    LostCavesAmbientParticle(ClientLevel clientLevel, double xo, double yo, double zo, float r, float g, float b, SpriteSet spriteSet) {
        super(clientLevel, xo, yo, zo);
        this.sprites = spriteSet;
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        this.quadSize *= 0.67499995f;
        int k = Mth.randomBetweenInclusive(clientLevel.getRandom(), 32, 64);
        this.lifetime = (int)Math.max((float)k, 1.0f);
        this.age = Mth.randomBetweenInclusive(clientLevel.getRandom(), 0, 16);
        this.setSpriteFromAge(spriteSet);
        this.rotSpeed = ((float)Math.random() - 0.5f) * 0.1f;
        this.roll = (float)Math.random() * ((float)Math.PI * 2);
        this.setParticleSpeed(
                Mth.lerp(clientLevel.random.nextDouble(), -0.05, 0.05),
                Mth.lerp(clientLevel.random.nextDouble(), -0.05, 0.05),
                Mth.lerp(clientLevel.random.nextDouble(), -0.05, 0.05)
        );
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
        this.roll += (float)Math.PI * this.rotSpeed * 2.0f;
        if (this.onGround) {
            this.roll = 0.0f;
            this.oRoll = 0.0f;
        }
        this.move(this.xd, this.yd, this.zd);
//        this.yd -= 0.001f;
//        this.yd = Math.max(this.yd, -0.9f);
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
            return new LostCavesAmbientParticle(clientLevel, xo, yo, zo, r, g, b, this.spriteSet);
        }
    }
}
