package com.yungnickyoung.minecraft.yungscavebiomes.block.entity;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;

public class SuspiciousAncientSandBlockEntity extends BlockEntity {
    private static final String LOOT_TABLE_TAG = "LootTable";
    private static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
    private static final String HIT_DIRECTION_TAG = "hit_direction";
    private static final String ITEM_TAG = "item";
    private static final int BRUSH_COOLDOWN_TICKS = 10;
    private static final int BRUSH_RESET_TICKS = 40;
    private static final int REQUIRED_BRUSHES_TO_BREAK = 10;

    private int brushCount;
    private long brushCountResetsAtTick;
    private long coolDownEndsAtTick;
    private ItemStack item = ItemStack.EMPTY;
    @Nullable
    private Direction hitDirection;
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    public SuspiciousAncientSandBlockEntity(BlockPos $$0, BlockState $$1) {
        super(EntityTypeModule.SUSPICIOUS_ANCIENT_SAND.get(), $$0, $$1);
    }

    public boolean brush(long $$0, Player $$1, Direction $$2) {
        if (this.hitDirection == null) {
            this.hitDirection = $$2;
        }

        this.brushCountResetsAtTick = $$0 + BRUSH_RESET_TICKS;
        if ($$0 >= this.coolDownEndsAtTick && this.level instanceof ServerLevel) {
            this.coolDownEndsAtTick = $$0 + BRUSH_COOLDOWN_TICKS;
            this.unpackLootTable($$1);
            int $$3 = this.getCompletionState();
            if (++this.brushCount >= REQUIRED_BRUSHES_TO_BREAK) {
                this.brushingCompleted($$1);
                return true;
            } else {
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), BRUSH_RESET_TICKS);
                int $$4 = this.getCompletionState();
                if ($$3 != $$4) {
                    BlockState $$5 = this.getBlockState();
                    BlockState $$6 = $$5.setValue(BlockStateProperties.DUSTED, Integer.valueOf($$4));
                    this.level.setBlock(this.getBlockPos(), $$6, 3);
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public void unpackLootTable(Player $$0) {
        if (this.lootTable != null && this.level != null && !this.level.isClientSide() && this.level.getServer() != null) {
            LootTable $$1 = this.level.getServer().getLootData().getLootTable(this.lootTable);
            if ($$0 instanceof ServerPlayer $$2) {
                CriteriaTriggers.GENERATE_LOOT.trigger($$2, this.lootTable);
            }

            LootParams $$3 = new LootParams.Builder((ServerLevel) this.level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition))
                    .withLuck($$0.getLuck())
                    .withParameter(LootContextParams.THIS_ENTITY, $$0)
                    .create(LootContextParamSets.CHEST);
            ObjectArrayList<ItemStack> $$4 = $$1.getRandomItems($$3, this.lootTableSeed);

            this.item = switch ($$4.size()) {
                case 0 -> ItemStack.EMPTY;
                case 1 -> (ItemStack) $$4.get(0);
                default -> {
                    YungsCaveBiomesCommon.LOGGER.warn("Expected max 1 loot from loot table {} got {}", this.lootTable, $$4.size());
                    yield $$4.get(0);
                }
            };
            this.lootTable = null;
            this.setChanged();
        }
    }

    private void brushingCompleted(Player $$0) {
        if (this.level != null && this.level.getServer() != null) {
            this.dropContent($$0);
            BlockState $$1 = this.getBlockState();
            this.level.levelEvent(3008, this.getBlockPos(), Block.getId($$1));
            Block $$4;
            if (this.getBlockState().getBlock() instanceof BrushableBlock $$3) {
                $$4 = $$3.getTurnsInto();
            } else {
                $$4 = Blocks.AIR;
            }

            this.level.setBlock(this.worldPosition, $$4.defaultBlockState(), 3);
        }
    }

    private void dropContent(Player $$0) {
        if (this.level != null && this.level.getServer() != null) {
            this.unpackLootTable($$0);
            if (!this.item.isEmpty()) {
                double $$1 = (double) EntityType.ITEM.getWidth();
                double $$2 = 1.0 - $$1;
                double $$3 = $$1 / 2.0;
                Direction $$4 = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
                BlockPos $$5 = this.worldPosition.relative($$4, 1);
                double $$6 = (double) $$5.getX() + 0.5 * $$2 + $$3;
                double $$7 = (double) $$5.getY() + 0.5 + (double) (EntityType.ITEM.getHeight() / 2.0F);
                double $$8 = (double) $$5.getZ() + 0.5 * $$2 + $$3;
                ItemEntity $$9 = new ItemEntity(this.level, $$6, $$7, $$8, this.item.split(this.level.random.nextInt(21) + 10));
                $$9.setDeltaMovement(Vec3.ZERO);
                this.level.addFreshEntity($$9);
                this.item = ItemStack.EMPTY;
            }
        }
    }

    public void checkReset() {
        if (this.level != null) {
            if (this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick) {
                int $$0 = this.getCompletionState();
                this.brushCount = Math.max(0, this.brushCount - 2);
                int $$1 = this.getCompletionState();
                if ($$0 != $$1) {
                    this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(BlockStateProperties.DUSTED, Integer.valueOf($$1)), 3);
                }

                int $$2 = 4;
                this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
            }

            if (this.brushCount == 0) {
                this.hitDirection = null;
                this.brushCountResetsAtTick = 0L;
                this.coolDownEndsAtTick = 0L;
            } else {
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), (int) (this.brushCountResetsAtTick - this.level.getGameTime()));
            }
        }
    }

    private boolean tryLoadLootTable(CompoundTag $$0) {
        if ($$0.contains(LOOT_TABLE_TAG, 8)) {
            this.lootTable = new ResourceLocation($$0.getString(LOOT_TABLE_TAG));
            this.lootTableSeed = $$0.getLong(LOOT_TABLE_SEED_TAG);
            return true;
        } else {
            return false;
        }
    }

    private boolean trySaveLootTable(CompoundTag $$0) {
        if (this.lootTable == null) {
            return false;
        } else {
            $$0.putString(LOOT_TABLE_TAG, this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                $$0.putLong(LOOT_TABLE_SEED_TAG, this.lootTableSeed);
            }

            return true;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag $$0 = super.getUpdateTag();
        if (this.hitDirection != null) {
            $$0.putInt(HIT_DIRECTION_TAG, this.hitDirection.ordinal());
        }

        $$0.put(ITEM_TAG, this.item.save(new CompoundTag()));
        return $$0;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void load(CompoundTag $$0) {
        if (!this.tryLoadLootTable($$0) && $$0.contains(ITEM_TAG)) {
            this.item = ItemStack.of($$0.getCompound(ITEM_TAG));
        }

        if ($$0.contains(HIT_DIRECTION_TAG)) {
            this.hitDirection = Direction.values()[$$0.getInt(HIT_DIRECTION_TAG)];
        }
    }

    @Override
    protected void saveAdditional(CompoundTag $$0) {
        if (!this.trySaveLootTable($$0)) {
            $$0.put(ITEM_TAG, this.item.save(new CompoundTag()));
        }
    }

    public void setLootTable(ResourceLocation $$0, long $$1) {
        this.lootTable = $$0;
        this.lootTableSeed = $$1;
    }

    private int getCompletionState() {
        if (this.brushCount == 0) {
            return 0;
        } else if (this.brushCount < 3) {
            return 1;
        } else {
            return this.brushCount < 6 ? 2 : 3;
        }
    }

    @Nullable
    public Direction getHitDirection() {
        return this.hitDirection;
    }

    public ItemStack getItem() {
        return this.item;
    }
}
