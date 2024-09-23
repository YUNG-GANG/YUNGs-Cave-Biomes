package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import javax.annotation.ParametersAreNonnullByDefault;

public class IceCubeModel<T extends IceCubeEntity> extends EntityModel<T> {
    private final ModelPart root;

    public IceCubeModel(ModelPart root) {
        this.root = root.getChild("bb_main");
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

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getLeaping()) {
            this.root.yRot += 9 * Mth.DEG_TO_RAD;
        } else {
            this.root.yRot = 0;
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, buffer, packedLight, packedOverlay);
    }
}