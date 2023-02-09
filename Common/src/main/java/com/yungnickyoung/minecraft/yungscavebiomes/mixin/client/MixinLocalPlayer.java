package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.sounds.LostCavesAmbientSoundsHandler;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.stats.StatsCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Shadow @Final private List<AmbientSoundHandler> ambientSoundHandlers;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void yungscavebiomes_addLostCavesAmbientSoundHandler(Minecraft $$0, ClientLevel $$1, ClientPacketListener $$2, StatsCounter $$3, ClientRecipeBook $$4, boolean $$5, boolean $$6, CallbackInfo ci) {
        this.ambientSoundHandlers.add(new LostCavesAmbientSoundsHandler(_this(), $$0.getSoundManager(), $$1.getBiomeManager()));
    }

    @Unique
    private LocalPlayer _this() {
        return (LocalPlayer) (Object) this;
    }
}
