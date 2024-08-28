package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.EmergeGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TagModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SandSnapperEntity extends PathfinderMob implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private final AnimationBuilder EMERGE = new AnimationBuilder().addAnimation("look");
    private final AnimationBuilder SWIM = new AnimationBuilder().addAnimation("swim");

    private static final EntityDataAccessor<Boolean> EMERGED = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean emergedHolder;

    public SandSnapperEntity(EntityType<SandSnapperEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EMERGED, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 8.0f, 1.0f, 1.25f));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0f));
        this.goalSelector.addGoal(2, new EmergeGoal(this, 8.0f, 2.0f, 100));
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.isEmerging()) {
            return EntityDimensions.fixed(0.6f, 1.4f);
        }

        return super.getDimensions(pose);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        super.onSyncedDataUpdated(dataAccessor);

        if (dataAccessor.equals(EMERGED)) {
            this.emergedHolder = this.entityData.get(EMERGED);
            this.refreshDimensions();
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.is(ItemModule.PRICKLY_PEACH_ITEM.get())) {
            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            }

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            this.heal(4.0f);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            this.level.broadcastEntityEvent(this, (byte)7);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void handleEntityEvent(byte eventId) {
        if (eventId == 7) {
            this.spawnHeartParticles();
        } else {
            super.handleEntityEvent(eventId);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level.isClientSide()) {
            float width = this.getDimensions(this.getPose()).width * 0.8F;
            AABB aabb = AABB.ofSize(this.getEyePosition(), width, 1.0E-6, width);
            BlockPos.betweenClosedStream(aabb).forEach((pos) -> {
                BlockState blockState = this.level.getBlockState(pos);
                Vec3 vec3 = this.getDeltaMovement();
                this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX() + (this.random.nextDouble() - 0.5) * (double)width, this.getY() + 1.1, this.getZ() + (this.random.nextDouble() - 0.5) * (double)width, vec3.x * -4.0, 1.5, vec3.z * -4.0);
            });
        }
    }

    @Override
    public boolean isInWall() {
        if (this.noPhysics || this.isSleeping()) {
            return false;
        } else {
            float width = this.getDimensions(this.getPose()).width * 0.8F;
            AABB aabb = AABB.ofSize(this.getEyePosition(), width, 1.0E-6, width);
            return BlockPos.betweenClosedStream(aabb).anyMatch((pos) -> {
                BlockState blockState = this.level.getBlockState(pos);
                BlockState stateAbove = this.level.getBlockState(pos.above());
                return !blockState.isAir() && !(blockState.is(TagModule.SAND_SNAPPER_BLOCKS) && stateAbove.is(Blocks.AIR)) && blockState.isSuffocating(this.level, pos) && Shapes.joinIsNotEmpty(blockState.getCollisionShape(this.level, pos).move(pos.getX(), pos.getY(), pos.getZ()), Shapes.create(aabb), BooleanOp.AND);
            });
        }
    }

    protected void spawnHeartParticles() {
        for (int i = 0; i < 7; i++) {
            double xSpeed = this.random.nextGaussian() * 0.02;
            double ySpeed = this.random.nextGaussian() * 0.02;
            double zSpeed = this.random.nextGaussian() * 0.02;
            this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), xSpeed, ySpeed, zSpeed);
        }
    }

    public void setEmerging(boolean isEmerging) {
        this.entityData.set(EMERGED, isEmerging);
    }

    public boolean isEmerging() {
        return this.emergedHolder;
    }

    private <E extends IAnimatable> PlayState generalPredicate(AnimationEvent<E> event) {
        if (this.isEmerging()) {
            event.getController().setAnimation(EMERGE);
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(SWIM);
            return PlayState.CONTINUE;
        }

        event.getController().markNeedsReload();

        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "generalController", 2, this::generalPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
