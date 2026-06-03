package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderState;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.IceCubeRenderer;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;



public class IceCubeModel<S extends IceCubeRenderState> extends EntityModel<S> {
    public IceCubeModel(ModelPart root) {
        super(root.getChild("bb_main"));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-12.0F, -24.0F, -12.0F, 24.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override public void setupAnim(final S state) {
        if (state.leaping) {
            this.root.yRot += 9 * Mth.DEG_TO_RAD;
        } else {
            this.root.yRot = 0;
        }
    }
}