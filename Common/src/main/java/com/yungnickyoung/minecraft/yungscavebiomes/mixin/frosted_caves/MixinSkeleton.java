package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Skeleton.class)
public abstract class MixinSkeleton extends AbstractSkeleton {
    protected MixinSkeleton(EntityType<? extends AbstractSkeleton> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Shadow
    public abstract boolean isFreezeConverting();

    /**
     * Skeletons in Frosted Caves transform into strays.
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void yungscavebiomes_transformSkeletonsInFrostedCaves(CallbackInfo ci) {
        if (!this.level().isClientSide
                && this.isAlive()
                && !this.isNoAi()
                && !this.isFreezeConverting()
                && this.level().getBiome(this.blockPosition()).is(BiomeModule.FROSTED_CAVES)) {
            this.isInPowderSnow = true;
        }
    }
}
