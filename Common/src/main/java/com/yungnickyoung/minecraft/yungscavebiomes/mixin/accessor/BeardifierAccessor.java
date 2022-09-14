package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Beardifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Beardifier.class)
public interface BeardifierAccessor {
    @Invoker("<init>")
    static Beardifier createBeardifier(StructureFeatureManager $$0, ChunkAccess $$1) {
        throw new UnsupportedOperationException();
    }
}
