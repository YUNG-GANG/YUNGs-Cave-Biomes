package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormFogEnvironment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Mixin to handle rendering of the sandstorm fog in Lost Caves.
 */
@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @Shadow @Final private static List<FogEnvironment> FOG_ENVIRONMENTS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void yungscavebiomes_setupLostCavesFog(final CallbackInfo ci) {
        FOG_ENVIRONMENTS.add(new SandstormFogEnvironment());
    }
}
