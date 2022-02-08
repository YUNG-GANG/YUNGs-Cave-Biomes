package com.yungnickyoung.minecraft.yungscavebiomes.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public class IceCubeEntity extends PathfinderMob implements Enemy {
    public IceCubeEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }


}
