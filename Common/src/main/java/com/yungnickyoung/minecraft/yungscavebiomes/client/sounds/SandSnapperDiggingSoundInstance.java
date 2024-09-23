package com.yungnickyoung.minecraft.yungscavebiomes.client.sounds;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class SandSnapperDiggingSoundInstance extends AbstractTickableSoundInstance {
    private final SandSnapperEntity sandSnapper;

    public SandSnapperDiggingSoundInstance(SandSnapperEntity sandSnapper) {
        super(SoundModule.SAND_SNAPPER_DIGGING.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.sandSnapper = sandSnapper;
        this.x = sandSnapper.getX();
        this.y = sandSnapper.getY();
        this.z = sandSnapper.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
    }

    @Override
    public void tick() {
        if (!this.sandSnapper.isRemoved()) {
            this.x = this.sandSnapper.getX();
            this.y = this.sandSnapper.getY();
            this.z = this.sandSnapper.getZ();
            float horizontalSpeed = (float) this.sandSnapper.getDeltaMovement().horizontalDistance();
            if (horizontalSpeed >= 0.01F && this.sandSnapper.isSubmerged()) {
                // Only play sound if snapper is moving and submerged.
                // Sound volume while submerged depends on the snapper's speed.
//                this.volume = Mth.lerp(Mth.clamp(horizontalSpeed, 0.0F, 1.0F), 0.0F, 1.0F);
                this.volume = 0.5F;
            } else {
                this.volume = 0.0F;
            }
        } else {
            this.stop();
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.sandSnapper.isRemoved();
    }
}
