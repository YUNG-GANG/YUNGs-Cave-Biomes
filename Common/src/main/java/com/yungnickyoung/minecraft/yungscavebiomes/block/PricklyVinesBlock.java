package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DamageTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PricklyVinesBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public PricklyVinesBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return BlockModule.PRICKLY_VINES_PLANT.get();
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return NetherVines.isValidGrowthState(blockState);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getType() == EntityType.FOX || entity.getType() == EntityType.BEE || entity.getType() == EntityTypeModule.SAND_SNAPPER.get()) {
            return;
        }
        entity.makeStuckInBlock(blockState, new Vec3(0.8f, 0.75, 0.8f));
        if (!(level.isClientSide || blockState.getValue(AGE) <= 0 || entity.xOld == entity.getX() && entity.zOld == entity.getZ())) {
            double d = Math.abs(entity.getX() - entity.xOld);
            double e = Math.abs(entity.getZ() - entity.zOld);
            if (d >= (double) 0.003f || e >= (double) 0.003f) {
                entity.hurt(DamageTypeModule.of(level.registryAccess(), DamageTypeModule.PRICKLY_VINES), 1.0f);
            }
        }
    }
}
