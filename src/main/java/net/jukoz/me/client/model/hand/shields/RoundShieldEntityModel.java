package net.jukoz.me.client.model.hand.shields;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class RoundShieldEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart plate;
    private final ModelPart handle;

    public RoundShieldEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
        this.plate = root.getChild("plate");
        this.handle = root.getChild("handle");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild("handle", CubeListBuilder.create().uv(17, 17).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.NONE);

        modelPartData.addChild("plate", CubeListBuilder.create().uv(0, 6).cuboid(-8.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(46, 6).cuboid(7.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(4, 4).cuboid(-7.0F, -4.0F, -2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(42, 4).cuboid(6.0F, -4.0F, -2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(8, 2).cuboid(-6.0F, -6.0F, -2.0F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(36, 2).cuboid(4.0F, -6.0F, -2.0F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(14, 1).cuboid(-4.0F, -7.0F, -2.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(30, 1).cuboid(2.0F, -7.0F, -2.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(20, 0).cuboid(-2.0F, -8.0F, -2.0F, 4.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.NONE);

        return LayerDefinition.of(modelData, 64, 64);
    }

    public ModelPart getHandle() {
        return handle;
    }

    public ModelPart getPlate() {
        return plate;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay);
    }
}
