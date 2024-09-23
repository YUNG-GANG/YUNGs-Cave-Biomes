package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RareIceBlock extends HalfTransparentBlock implements EntityBlock {
    public RareIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            int xp = 15 + serverLevel.random.nextInt(20) + serverLevel.random.nextInt(20);
            this.popExperience(serverLevel, blockPos, xp);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
        if (!level.isClientSide) {
            CriteriaModule.BREAK_ENCHANTED_ICE.trigger((ServerPlayer) player);
        }
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        double x = (double)blockPos.getX() + 0.5 + ((0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1));
        double y = (double)blockPos.getY() + 0.5 + ((0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1));
        double z = (double)blockPos.getZ() + 0.5 + ((0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1));
//        Vec3 dir = new Vec3(x, y, z).subtract(new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5)).normalize();
//            level.addParticle(ParticleTypes.GLOW, x, y, z, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005);
        level.addParticle(ParticleTypes.GLOW, x, y, z, 0, 0, 0);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RareIceBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, EntityTypeModule.RARE_ICE.get(), RareIceBlockEntity::tick);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel serverLevel, T blockEntity) {
        return EntityBlock.super.getListener(serverLevel, blockEntity);
    }

    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> p_152133_,
            BlockEntityType<E> p_152134_,
            BlockEntityTicker<? super E> p_152135_
    ) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }
}
