package com.yungnickyoung.minecraft.yungscavebiomes.effect;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BuffetedEffect extends MobEffect {
    public BuffetedEffect() {
        super(MobEffectCategory.HARMFUL, 0x996436);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, YungsCaveBiomesCommon.id("effect.buffeted"),
                -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
