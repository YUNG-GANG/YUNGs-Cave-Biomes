package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.Nullable;

import static com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon.id;

//todo register me
public class SandSnapperRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SandSnapperEntity, R> {
    private static final DataTicket<Boolean> IS_LOOKING_AT_PLAYER = DataTicket.create(id("is_looking_at_player").toString(), Boolean.class);

    public SandSnapperRenderer(final EntityRendererProvider.Context context, final EntityType<? extends SandSnapperEntity> entityType) {
        super(context, entityType);
    }

    @Override
    public void addRenderData(final SandSnapperEntity animatable, @Nullable final Void relatedObject, final R renderState, final float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(IS_LOOKING_AT_PLAYER, animatable.isLookingAtPlayer());
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
