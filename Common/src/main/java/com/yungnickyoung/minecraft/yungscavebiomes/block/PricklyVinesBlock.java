package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DamageTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InsideBlockEffectApplier;
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




public class PricklyVinesBlock extends GrowingPlantHeadBlock {
    static final float HURT_SPEED_THRESHOLD = 0.003F;
    public static final MapCodec<PricklyVinesBlock> CODEC = simpleCodec(PricklyVinesBlock::new);

    protected static final VoxelShape SHAPE = Block.box(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public PricklyVinesBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    protected @NotNull MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
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
    protected void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
        if (!(entity instanceof LivingEntity) || entity.getType() == BuiltInRegistries.ENTITY_TYPE.get(Identifier.fromNamespaceAndPath("minecraft", "fox")).orElseThrow().value() || entity.getType() == BuiltInRegistries.ENTITY_TYPE.get(Identifier.fromNamespaceAndPath("minecraft", "bee")).orElseThrow().value() || entity.getType() == EntityTypeModule.SAND_SNAPPER.get()) {
            return;
        }
        entity.makeStuckInBlock(state, new Vec3(0.8f, 0.75, 0.8f));
        if (level instanceof ServerLevel serverLevel) {
            if (state.getValue(AGE) != 0) {
                Vec3 movement = entity.isClientAuthoritative() ? entity.getKnownMovement()
                                                               : entity.oldPosition().subtract(entity.position());
                if (movement.horizontalDistanceSqr() > (double)0.0F) {
                    double xs = Math.abs(movement.x());
                    double zs = Math.abs(movement.z());
                    if (xs >= HURT_SPEED_THRESHOLD || zs >= HURT_SPEED_THRESHOLD) {
                        entity.hurtServer(serverLevel, DamageTypeModule.of(level.registryAccess(), DamageTypeModule.PRICKLY_VINES), 1.0f);
                    }
                }
            }
        }
    }
}
