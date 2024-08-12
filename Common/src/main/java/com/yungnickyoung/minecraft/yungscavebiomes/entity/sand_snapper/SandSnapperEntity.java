package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper;

import com.yungnickyoung.minecraft.yungscavebiomes.module.TagModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SandSnapperEntity extends Mob implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

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
                return !blockState.isAir() && !blockState.is(TagModule.SAND_SNAPPER_BLOCKS) && blockState.isSuffocating(this.level, pos) && Shapes.joinIsNotEmpty(blockState.getCollisionShape(this.level, pos).move(pos.getX(), pos.getY(), pos.getZ()), Shapes.create(aabb), BooleanOp.AND);
            });
        }
    }

    private <E extends IAnimatable> PlayState generalPredicate(AnimationEvent<E> event) {
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
