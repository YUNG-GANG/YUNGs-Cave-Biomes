package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.PotionModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
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

@Mixin(AbstractThrownPotion.class)
public abstract class AbstractThrownPotionMixin extends ThrowableItemProjectile {
    public AbstractThrownPotionMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Makes frost splash potions spawn ice sheets on impact.
     */
    @Inject(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/throwableitemprojectile/AbstractThrownPotion;discard()V", ordinal = 0))
    protected void yungscavebiomes_onFrostSplashPotionHit(HitResult hitResult, CallbackInfo ci) {
        if (!this.level().isClientSide() && !this.level().environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, hitResult.getLocation())) {
            ItemStack itemStack = this.getItem();
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (potionContents.potion().isEmpty()) {
                return;
            }
            Holder<Potion> potion = potionContents.potion().get();
            if (potion.is(PotionModule.FROST_POTION.getHolder()) || potion.is(PotionModule.STRONG_FROST_POTION.getHolder())) {
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

                int attemptDistance = potion == PotionModule.FROST_POTION.getHolder() ? 3 : 4;
                int maxDist = potion == PotionModule.FROST_POTION.getHolder() ? 8 : 14;

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
