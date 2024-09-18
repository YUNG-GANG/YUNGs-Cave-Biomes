package com.yungnickyoung.minecraft.yungscavebiomes.criteria;

import com.google.gson.JsonObject;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SandstormEndTrigger extends SimpleCriterionTrigger<SandstormEndTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "sandstorm_end");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return new SandstormEndTrigger.TriggerInstance(composite);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, TriggerInstance::matches);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(EntityPredicate.Composite $$0) {
            super(SandstormEndTrigger.ID, $$0);
        }

        public boolean matches() {
            return true;
        }
    }
}
