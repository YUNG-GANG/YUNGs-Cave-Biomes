package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.SandSnapperModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.Nullable;

import static com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon.id;

public class SandSnapperRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SandSnapperEntity, R> {
    private static final DataTicket<Boolean> IS_LOOKING_AT_PLAYER = DataTicket.create(id("is_looking_at_player").toString(), Boolean.class);
    private static final DataTicket<Boolean> IS_SUBMERGED         = DataTicket.create(id("is_submerged").toString(), Boolean.class);

    public SandSnapperRenderer(final EntityRendererProvider.Context context, final EntityType<? extends SandSnapperEntity> entityType) {
        super(context, new SandSnapperModel());
    }

    @Override
    public void addRenderData(final SandSnapperEntity animatable, @Nullable final Void relatedObject, final R renderState, final float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(IS_LOOKING_AT_PLAYER, animatable.isLookingAtPlayer());
        renderState.addGeckolibData(IS_SUBMERGED,
                                    animatable.isSubmerged()
                                    && !animatable.isDiving()
                                    && !animatable.isEmerging()
                                    && !animatable.isDiggingDown()
                                    && !animatable.isDiggingUp());
    }

    @Override public void adjustRenderPose(final RenderPassInfo<R> renderPassInfo) {
        super.adjustRenderPose(renderPassInfo);
        if (renderPassInfo.getGeckolibData(IS_SUBMERGED)) {
            renderPassInfo.poseStack().translate(0.0, -16.0, 0.0);
        }
    }

    @Override
    public void adjustModelBonesForRender(final RenderPassInfo<R> renderPassInfo, final BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        var state = renderPassInfo.renderState();
        if (!state.getOrDefaultGeckolibData(IS_LOOKING_AT_PLAYER, false)) {
            return;
        }

        snapshots.ifPresent("neck", bone -> {
            bone.setRotX(state.yRot * Mth.DEG_TO_RAD);
            bone.setRotY(state.yRot * Mth.DEG_TO_RAD);
        });
    }
}
