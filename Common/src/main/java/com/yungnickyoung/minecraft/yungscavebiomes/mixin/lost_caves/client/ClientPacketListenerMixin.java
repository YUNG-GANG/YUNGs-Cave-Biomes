package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.sounds.SandSnapperDiggingSoundInstance;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {

    protected ClientPacketListenerMixin(Minecraft $$0, Connection $$1, CommonListenerCookie $$2) {
        super($$0, $$1, $$2);
    }

    /**
     * Creates a ticking sound instance every time a Sand Snapper is created.
     */
    @Inject(method = "postAddEntitySoundInstance", at = @At("HEAD"))
    public void yungscavebiomes_addSandSnapperDiggingTickableSound(Entity entity, CallbackInfo ci) {
        if (entity instanceof SandSnapperEntity sandSnapper) {
            this.minecraft.getSoundManager().queueTickingSound(new SandSnapperDiggingSoundInstance(sandSnapper));
        }
    }
}
