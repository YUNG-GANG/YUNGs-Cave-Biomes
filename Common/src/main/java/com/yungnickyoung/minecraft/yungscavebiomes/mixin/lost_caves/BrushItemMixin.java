package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.SuspiciousAncientSandBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BrushItem.class)
public abstract class BrushItemMixin {
    @Inject(
            method = "onUseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void yungscavebiomes_brushSuspiciousAncientSand(
            Level level, LivingEntity entity, ItemStack itemStack, int ticks, CallbackInfo ci, Player player, HitResult hitResult
    ) {
        if (hitResult instanceof BlockHitResult blockHitResult && hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = blockHitResult.getBlockPos();
            if (!level.isClientSide() && level.getBlockEntity(hitPos) instanceof SuspiciousAncientSandBlockEntity blockEntity) {
                boolean brushed = blockEntity.brush(level.getGameTime(), player, blockHitResult.getDirection());
                if (brushed) {
                    EquipmentSlot brushSlot = itemStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                    itemStack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(brushSlot));
                }
            }
        }
    }
}
