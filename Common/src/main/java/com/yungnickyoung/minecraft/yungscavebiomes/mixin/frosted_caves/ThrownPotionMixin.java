package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.PotionModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile {
    public ThrownPotionMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Makes frost splash potions spawn ice sheets on impact.
     */
    @Inject(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ThrownPotion;discard()V", ordinal = 0))
    protected void yungscavebiomes_onFrostSplashPotionHit(HitResult hitResult, CallbackInfo ci) {
        if (!this.level().isClientSide && !this.level().dimensionType().ultraWarm()) {
            ItemStack itemStack = this.getItem();
            Potion potion = PotionUtils.getPotion(itemStack);
            if (potion == PotionModule.FROST_POTION.get() || potion == PotionModule.STRONG_FROST_POTION.get()) {
                // Determine hit pos
                BlockPos originPos = null;
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    originPos = ((BlockHitResult) hitResult).getBlockPos();
                } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                    originPos = ((EntityHitResult) hitResult).getEntity().getOnPos();
                }

                if (originPos == null) {
                    return;
                }

                BlockPos.MutableBlockPos currPos = originPos.mutable();
                BlockPos.MutableBlockPos mutable = currPos.mutable();

                int attemptDistance = potion == PotionModule.FROST_POTION.get() ? 3 : 4;
                int maxDist = potion == PotionModule.FROST_POTION.get() ? 8 : 14;

                // Create AOE freeze
                for (int x = -attemptDistance; x <= attemptDistance; x++) {
                    for (int y = -attemptDistance; y <= attemptDistance; y++) {
                        for (int z = -attemptDistance; z <= attemptDistance; z++) {
                            if (x * x + y * y + z * z > maxDist) {
                                continue;
                            }

                            currPos.setWithOffset(originPos, x, y, z);
                            BlockState currState = this.level().getBlockState(currPos);

                            if (!currState.isAir() && !currState.is(Blocks.WATER) && !(currState.is(BlockTags.REPLACEABLE))) {
                                continue;
                            }

                            for (Direction direction : Direction.values()) {
                                mutable.setWithOffset(currPos, direction);

                                IceSheetBlock iceSheetBlock = (IceSheetBlock) BlockModule.ICE_SHEET.get();
                                BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(currState, this.level(), currPos, direction);
                                if (updatedBlockState == null) {
                                    continue;
                                }
                                updatedBlockState = updatedBlockState.setValue(IceSheetBlock.GROWTH_DISTANCE, 0);

                                currState = updatedBlockState;
                                this.level().setBlock(currPos, updatedBlockState, 3);
                            }
                        }
                    }
                }
            }
        }
    }
}
