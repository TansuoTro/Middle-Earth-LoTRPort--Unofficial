package net.jukoz.me.entity.spider;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class EnwebbedModel extends HierarchicalModel<LivingEntity> {
    private final ModelPart bigBody;
    private final ModelPart smallBody;

    private final ModelPart root;

    public EnwebbedModel(ModelPart root) {
        this.root = root;
        this.bigBody = root.getChild("big_body");
        this.smallBody = root.getChild("small_body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition dataRoot = modelData.getRoot();
        PartDefinition bigBody = dataRoot.addOrReplaceChild("big_body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new CubeDeformation(1.15F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition smallBody = dataRoot.addOrReplaceChild("small_body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new CubeDeformation(0.65F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(LivingEntity entity, float limbAngle, float limbDistance,
                          float animationProgress, float headYaw, float headPitch) {
        boolean hasChestplate = !entity.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
        this.bigBody.hidden = !hasChestplate;
        this.smallBody.hidden = hasChestplate;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
