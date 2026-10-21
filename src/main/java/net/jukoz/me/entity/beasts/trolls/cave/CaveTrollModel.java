package net.jukoz.me.entity.beasts.trolls.cave;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.entity.beasts.trolls.TrollAnimations;
import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class CaveTrollModel extends HierarchicalModel<CaveTrollEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart boneJaw;
    private final ModelPart legRight;
    private final ModelPart legLeft;
    private final ModelPart armLeft;
    private final ModelPart armRight;

    private static final float ROTATION_SPEED = 0.8f;

    public CaveTrollModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.boneJaw = head.getChild("boneJaw");
        this.legRight = root.getChild("legRight");
        this.legLeft = root.getChild("legLeft");
        this.armLeft = root.getChild("armLeft");
        this.armRight = root.getChild("armRight");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(66, 33).addBox(-9.0F, -35.0F, -7.0F, 18.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, -32.0F, -7.0F, 28.0F, 16.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-12.0F, -16.0F, -9.0F, 24.0F, 16.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(84, 122).addBox(-10.0F, -29.0F, -10.0F, 20.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 67).addBox(-7.5F, -7.8F, -16.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(118, 0).addBox(-6.5F, -7.8F, -18.0F, 13.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.5F, -2.8F, -17.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(116, 107).addBox(-6.0F, 0.2F, -16.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.0F, -6.0F));

        PartDefinition boneJaw = head.addOrReplaceChild("boneJaw", CubeListBuilder.create().texOffs(84, 50).addBox(-6.0F, 0.75F, -13.0F, 12.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(117, 91).addBox(-5.5F, -1.25F, -12.5F, 11.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.45F, -5.0F));

        PartDefinition legLeft = modelPartData.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(106, 67).addBox(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 0.0F, 0.0F));

        PartDefinition kneeLeft = legLeft.addOrReplaceChild("kneeLeft", CubeListBuilder.create().texOffs(0, 118).addBox(3.6F, -13.0F, -5.0F, 10.0F, 13.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(73, 0).addBox(3.6F, -5.0F, -7.0F, 10.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.6F, 24.0F, 0.0F));

        PartDefinition legRight = modelPartData.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(84, 95).addBox(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 0.0F, 0.0F));

        PartDefinition kneeRight = legRight.addOrReplaceChild("kneeRight", CubeListBuilder.create().texOffs(45, 67).addBox(-5.0F, 19.0F, -7.0F, 10.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(118, 23).addBox(-5.0F, 11.0F, -5.0F, 10.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armRight = modelPartData.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(60, 67).addBox(-11.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, -26.0F, 1.0F));

        PartDefinition forearmRight = armRight.addOrReplaceChild("forearmRight", CubeListBuilder.create().texOffs(90, 0).addBox(-11.5F, 9.2F, -5.5F, 9.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.8F, 0.5F));

        PartDefinition armLeft = modelPartData.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(0, 90).addBox(0.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, -26.0F, 1.0F));

        PartDefinition forearmLeft = armLeft.addOrReplaceChild("forearmLeft", CubeListBuilder.create().texOffs(46, 95).addBox(-4.5F, 0.2F, -5.5F, 9.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 11.8F, 0.5F));
        return LayerDefinition.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(CaveTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().getAllParts().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(TrollAnimations.MOVING, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, TrollAnimations.IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, TrollAnimations.ATTACK, ageInTicks, 1.5f);
        this.updateAnimation(entity.chargeAnimationState, TrollAnimations.CHARGING, ageInTicks, 1f);
        this.updateAnimation(entity.throwingAnimationState, TrollAnimations.THROWING, ageInTicks, 1.1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F);

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
