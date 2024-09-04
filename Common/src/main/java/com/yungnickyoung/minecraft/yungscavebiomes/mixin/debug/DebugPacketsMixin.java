package com.yungnickyoung.minecraft.yungscavebiomes.mixin.debug;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(DebugPackets.class)
public abstract class DebugPacketsMixin {
    @Shadow
    private static void sendPacketToAllPlayers(ServerLevel $$0, FriendlyByteBuf $$1, ResourceLocation $$2) {
    }

    @Inject(method = "sendGoalSelector", at = @At("HEAD"))
    private static void yungscavebiomes_debugSendGoalSelector(Level level, Mob mob, GoalSelector goalSelector, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBlockPos(mob.blockPosition());
            buf.writeInt(mob.getId());

            Set<WrappedGoal> goals = ((MobAccessor) mob).getGoalSelector().getAvailableGoals();
            buf.writeInt(goals.size());

            goals.stream().sorted((a, b) -> Integer.compare(b.getPriority(), a.getPriority())).forEach((goal) -> {
                buf.writeInt(goal.getPriority());
                buf.writeBoolean(goal.isRunning());
                buf.writeUtf(goal.getGoal().toString());
            });

            sendPacketToAllPlayers(serverLevel, buf, ClientboundCustomPayloadPacket.DEBUG_GOAL_SELECTOR);
        }
    }
}
