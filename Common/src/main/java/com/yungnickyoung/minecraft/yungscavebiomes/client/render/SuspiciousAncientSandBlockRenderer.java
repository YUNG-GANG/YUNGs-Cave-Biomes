package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.SuspiciousAncientSandBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SuspiciousAncientSandBlockRenderer implements BlockEntityRenderer<SuspiciousAncientSandBlockEntity> {
    private final ItemRenderer itemRenderer;

    public SuspiciousAncientSandBlockRenderer(BlockEntityRendererProvider.Context $$0) {
        this.itemRenderer = $$0.getItemRenderer();
    }

    public void render(SuspiciousAncientSandBlockEntity blockEntity, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
        if (blockEntity.getLevel() != null) {
            int $$6 = blockEntity.getBlockState().getValue(BlockStateProperties.DUSTED);
            if ($$6 > 0) {
                Direction $$7 = blockEntity.getHitDirection();
                if ($$7 != null) {
                    ItemStack $$8 = blockEntity.getItem();
                    if (!$$8.isEmpty()) {
                        $$2.pushPose();
                        $$2.translate(0.0F, 0.5F, 0.0F);
                        float[] $$9 = this.translations($$7, $$6);
                        $$2.translate($$9[0], $$9[1], $$9[2]);
                        $$2.mulPose(Axis.YP.rotationDegrees(75.0F));
                        boolean $$10 = $$7 == Direction.EAST || $$7 == Direction.WEST;
                        $$2.mulPose(Axis.YP.rotationDegrees((float) (($$10 ? 90 : 0) + 11)));
                        $$2.scale(0.5F, 0.5F, 0.5F);
                        int $$11 = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockState(), blockEntity.getBlockPos().relative($$7));
                        this.itemRenderer.renderStatic($$8, ItemDisplayContext.FIXED, $$11, OverlayTexture.NO_OVERLAY, $$2, $$3, blockEntity.getLevel(), 0);
                        $$2.popPose();
                    }
                }
            }
        }
    }

    private float[] translations(Direction $$0, int $$1) {
        float[] $$2 = new float[]{0.5F, 0.0F, 0.5F};
        float $$3 = (float) $$1 / 10.0F * 0.75F;
        switch ($$0) {
            case EAST:
                $$2[0] = 0.73F + $$3;
                break;
            case WEST:
                $$2[0] = 0.25F - $$3;
                break;
            case UP:
                $$2[1] = 0.25F + $$3;
                break;
            case DOWN:
                $$2[1] = -0.23F - $$3;
                break;
            case NORTH:
                $$2[2] = 0.25F - $$3;
                break;
            case SOUTH:
                $$2[2] = 0.73F + $$3;
        }

        return $$2;
    }
}
