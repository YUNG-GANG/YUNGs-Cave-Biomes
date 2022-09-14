package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class IceShatterParticle extends TextureSheetParticle {
    private final float uo;
    private final float vo;

    public IceShatterParticle(ClientLevel clientLevel, double xo, double yo, double zo) {
        super(clientLevel, xo, yo, zo, 0.0, 0.0, 0.0);
        this.setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(Blocks.ICE.defaultBlockState()));
        this.gravity = 1f;
        this.friction = 0.9f; // acceleration
        this.xd *= random.nextFloat() * 8f;
        this.zd *= random.nextFloat() * 8f;
        this.yd *= random.nextFloat() * 8f;
        this.quadSize /= 5.0F;
        this.lifetime = Mth.randomBetweenInclusive(this.random, 20, 40);;
        this.uo = this.random.nextFloat();
        this.vo = this.random.nextFloat();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
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
    public int getLightColor(float f) {
        int lightAtPos = super.getLightColor(f);
        int k = lightAtPos >> 16 & 0xFF;
        return 0xF0 | k << 16;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double xo, double yo, double zo, double dx, double dy, double dz){
            return new IceShatterParticle(clientLevel, xo, yo, zo);
        }
    }
}
