package com.yungnickyoung.minecraft.yungscavebiomes.mixin;

import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.ArrowAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow extends Entity {
    private MixinAbstractArrow(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHitBlock", at = @At("RETURN"))
    public void yungscavebiomes_tippedArrowOnHitBlockSpawnIceSheets(BlockHitResult blockHitResult, CallbackInfo ci) {
        if (!this.level.isClientSide && isArrow(this)) {
            Arrow arrow = asArrow(this);
            for (MobEffectInstance mobEffectInstance : ((ArrowAccessor) arrow).getPotion().getEffects()) {
                if (mobEffectInstance.getEffect() != MobEffectModule.FROZEN_EFFECT.get()) {
                    continue;
                }

                BlockPos originPos = blockHitResult.getBlockPos();
                int amplifier = mobEffectInstance.getAmplifier();

                placeIceSheets(level, originPos, amplifier);
            }
        }
    }

    @Inject(method = "onHitEntity", at = @At("RETURN"))
    public void yungscavebiomes_tippedArrowOnHitEntitySpawnIceSheets(EntityHitResult entityHitResult, CallbackInfo ci) {
        if (!this.level.isClientSide && isArrow(this)) {
            Arrow arrow = asArrow(this);
            for (MobEffectInstance mobEffectInstance : ((ArrowAccessor) arrow).getPotion().getEffects()) {
                if (mobEffectInstance.getEffect() != MobEffectModule.FROZEN_EFFECT.get()) {
                    continue;
                }

                BlockPos originPos = entityHitResult.getEntity().getOnPos();
                int amplifier = mobEffectInstance.getAmplifier();

                placeIceSheets(level, originPos, amplifier);
            }
        }
    }

    private static void placeIceSheets(Level level, BlockPos originPos, int amplifier) {
        BlockPos.MutableBlockPos currPos = originPos.mutable();
        BlockPos.MutableBlockPos mutable = currPos.mutable();

        int attemptDistance = amplifier == 0 ? 3 :4;
        int maxDist = amplifier == 0 ? 8 : 14;

        // Create AOE freeze
        for (int x = -attemptDistance; x <= attemptDistance; x++) {
            for (int y = -attemptDistance; y <= attemptDistance; y++) {
                for (int z = -attemptDistance; z <= attemptDistance; z++) {
                    if (x * x + y * y + z * z > maxDist) {
                        continue;
                    }

                    currPos.setWithOffset(originPos, x, y, z);
                    BlockState currState = level.getBlockState(currPos);

                    if (!currState.isAir() && !currState.is(Blocks.WATER) && !(currState.is(BlockTags.REPLACEABLE_PLANTS))) {
                        continue;
                    }

                    for (Direction direction : Direction.values()) {
                        mutable.setWithOffset(currPos, direction);

                        IceSheetBlock iceSheetBlock = (IceSheetBlock) BlockModule.ICE_SHEET.get();
                        BlockState updatedBlockState = iceSheetBlock.getStateForPlacement(currState, level, currPos, direction);
                        if (updatedBlockState == null) {
                            continue;
                        }
                        updatedBlockState = updatedBlockState.setValue(IceSheetBlock.GROWTH_DISTANCE, 0);

                        currState = updatedBlockState;
                        level.setBlock(currPos, updatedBlockState, 3);
                    }
                }
            }
        }
    }

    private static boolean isArrow(Object object) {
        return object instanceof Arrow;
    }

    private static Arrow asArrow(Object object) {
        return (Arrow) object;
    }
}
