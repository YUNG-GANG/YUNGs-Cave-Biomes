package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.sounds.LostCavesAmbientSoundsHandler;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.chat.ChatAbilities;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow @Final private List<AmbientSoundHandler> ambientSoundHandlers;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void yungscavebiomes_addLostCavesAmbientSoundHandler(final Minecraft minecraft, final ClientLevel level, final ClientPacketListener connection, final StatsCounter stats, final ClientRecipeBook recipeBook, final Input lastSentInput, final boolean wasSprinting, final ChatAbilities chatAbilities, final CallbackInfo ci) {
        this.ambientSoundHandlers.add(new LostCavesAmbientSoundsHandler(_this(), minecraft.getSoundManager(), level.getBiomeManager()));
    }

    @Unique
    private LocalPlayer _this() {
        return (LocalPlayer) (Object) this;
    }
}
