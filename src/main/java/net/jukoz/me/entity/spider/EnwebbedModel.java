package net.jukoz.me.entity.spider;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class EnwebbedModel extends SinglePartEntityModel<LivingEntity> {
    private final ModelPart bigBody;
    private final ModelPart smallBody;

    private final ModelPart root;

    public EnwebbedModel(ModelPart root) {
        this.root = root;
        this.bigBody = root.getChild("big_body");
        this.smallBody = root.getChild("small_body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData dataRoot = modelData.getRoot();
        ModelPartData bigBody = dataRoot.addChild("big_body", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new Dilation(1.15F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData smallBody = dataRoot.addChild("small_body", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new Dilation(0.65F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
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
