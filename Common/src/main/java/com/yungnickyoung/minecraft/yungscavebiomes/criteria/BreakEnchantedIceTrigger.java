package com.yungnickyoung.minecraft.yungscavebiomes.criteria;

import com.google.gson.JsonObject;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class BreakEnchantedIceTrigger extends SimpleCriterionTrigger<BreakEnchantedIceTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(YungsCaveBiomesCommon.MOD_ID, "break_enchanted_ice");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return new BreakEnchantedIceTrigger.TriggerInstance(composite);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, TriggerInstance::matches);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(EntityPredicate.Composite $$0) {
            super(BreakEnchantedIceTrigger.ID, $$0);
        }

        public boolean matches() {
            return true;
        }
    }
}
