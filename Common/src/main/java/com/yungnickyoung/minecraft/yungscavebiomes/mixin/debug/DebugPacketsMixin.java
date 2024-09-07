package com.yungnickyoung.minecraft.yungscavebiomes.mixin.debug;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
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
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.Target;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Mixin(DebugPackets.class)
public abstract class DebugPacketsMixin {
    @Shadow
    private static void sendPacketToAllPlayers(ServerLevel $$0, FriendlyByteBuf $$1, ResourceLocation $$2) {
    }

    @Inject(method = "sendGoalSelector", at = @At("HEAD"))
    private static void yungscavebiomes_debugSendGoalSelector(Level level, Mob mob, GoalSelector goalSelector, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel && YungsCaveBiomesCommon.DEBUG_RENDERING) {
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

    @Inject(method = "sendPathFindingPacket", at = @At("HEAD"))
    private static void yungscavebiomes_debugPathfinding(Level level, Mob mob, @Nullable Path path, float maxDistanceToWaypoint, CallbackInfo ci) {
        if (path == null) return;

        if (level instanceof ServerLevel serverLevel && YungsCaveBiomesCommon.DEBUG_RENDERING) {
            if (((PathAccessor) path).getTargetNodes() == null || ((PathAccessor) path).getTargetNodes().isEmpty()) {
                // Fill path target nodes with dummy values.
                // targetNodes is not actually used, but the client expects it to be non-null for some reason.
                Set<Target> targetNodes = new HashSet<>();
                targetNodes.add(new Target(0, 0, 0));
                ((PathAccessor) path).setTargetNodes(targetNodes);
            }

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(mob.getId());
            buf.writeFloat(maxDistanceToWaypoint);
            path.writeToStream(buf);

            sendPacketToAllPlayers(serverLevel, buf, ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET);
        }
    }
}
