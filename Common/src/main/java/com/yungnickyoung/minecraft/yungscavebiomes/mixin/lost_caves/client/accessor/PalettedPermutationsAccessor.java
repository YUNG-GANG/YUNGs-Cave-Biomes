package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client.accessor;

import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PalettedPermutations.class)
public interface PalettedPermutationsAccessor {

    @Accessor
    List<Identifier> getTextures();

    @Accessor("textures")
    @Mutable
    void setTextures(List<Identifier> value);

    @Accessor
    Identifier getPaletteKey();
}
