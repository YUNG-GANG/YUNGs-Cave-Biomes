package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceAccessor {
    @Invoker("<init>")
    static DamageSource createDamageSource(String messageId) {
        throw new UnsupportedOperationException();
    }
}
