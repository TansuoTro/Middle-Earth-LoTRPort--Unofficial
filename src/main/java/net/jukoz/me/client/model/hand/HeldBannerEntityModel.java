package net.jukoz.me.client.model.hand;

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

public class HeldBannerEntityModel extends Model {
    private final ModelPart root;
    private final ModelPart pole;
    private final ModelPart banner;

    public HeldBannerEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
        this.pole = root.getChild("pole");
        this.banner = root.getChild("banner");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild("pole", CubeListBuilder.create()
                        .uv(44, 0).cuboid(-1.0F, -72.0F, -1.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F))
                        .uv(44, 12).cuboid(-1.0F, -30.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
                        .uv(0, 42).mirrored().cuboid(-10.0F, -74.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.NONE);

        modelPartData.addChild("banner", CubeListBuilder.create()
                .uv(0, 0).cuboid(-10.0F, -74.0F, -2.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.NONE);

        return LayerDefinition.of(modelData, 64, 64);
    }

    public ModelPart getPole() {
        return pole;
    }

    public ModelPart getBanner() {
        return banner;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay);
    }
}
