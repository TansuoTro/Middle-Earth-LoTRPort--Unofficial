package net.jukoz.me.client.model.hand;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;


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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("pole", ModelPartBuilder.create()
                        .uv(44, 0).cuboid(-1.0F, -72.0F, -1.0F, 2.0F, 42.0F, 2.0F, new Dilation(0.0F))
                        .uv(44, 12).cuboid(-1.0F, -30.0F, -1.0F, 2.0F, 30.0F, 2.0F, new Dilation(0.0F))
                        .uv(0, 42).mirrored().cuboid(-10.0F, -74.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.NONE);

        modelPartData.addChild("banner", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-10.0F, -74.0F, -2.0F, 20.0F, 40.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
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
