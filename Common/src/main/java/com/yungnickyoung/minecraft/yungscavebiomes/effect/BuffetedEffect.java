package com.yungnickyoung.minecraft.yungscavebiomes.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BuffetedEffect extends MobEffect {
    public BuffetedEffect() {
        super(MobEffectCategory.HARMFUL, 0x996436);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "AAE883F3-00D3-9804-67FF-AFFE449F8BC4",
                -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
