package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpawnPlacements.class)
public interface SpawnPlacementsAccessor {
    @Invoker
    static <T extends Mob> void callRegister(EntityType<T> $$0, SpawnPlacements.Type $$1, Heightmap.Types $$2, SpawnPlacements.SpawnPredicate<T> $$3) {
        throw new AssertionError();
    }
}
