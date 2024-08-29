package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.sounds.SandSnapperDiggingSoundInstance;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    @Shadow
    @Final
    private Minecraft minecraft;

    /**
     * Creates a ticking sound instance every time a Sand Snapper is created.
     * Uses Redirect instead of Inject because for some reason I couldn't capture the locals...
     */
    @Redirect(method = "handleAddMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;recreateFromPacket(Lnet/minecraft/network/protocol/game/ClientboundAddMobPacket;)V"))
    public void yungscavebiomes_addSandSnapperDiggingTickableSound(LivingEntity livingEntity, ClientboundAddMobPacket packet) {
        livingEntity.recreateFromPacket(packet); // call original method
        if (livingEntity instanceof SandSnapperEntity sandSnapper) {
            this.minecraft.getSoundManager().queueTickingSound(new SandSnapperDiggingSoundInstance(sandSnapper));
        }
    }
}
