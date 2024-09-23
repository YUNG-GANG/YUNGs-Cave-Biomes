package com.yungnickyoung.minecraft.yungscavebiomes.criteria;

import com.google.gson.JsonObject;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class SandstormEndTrigger extends SimpleCriterionTrigger<SandstormEndTrigger.TriggerInstance> {
    private static final ResourceLocation ID = YungsCaveBiomesCommon.id("sandstorm_end");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected @NotNull TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate predicate, DeserializationContext ctx) {
        return new TriggerInstance(predicate);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, TriggerInstance::matches);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate predicate) {
            super(SandstormEndTrigger.ID, predicate);
        }

        public boolean matches() {
            return true;
        }
    }
}
