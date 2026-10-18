package net.jukoz.me.entity.beasts.cave_troll.feature;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollHeldItemFeatureRenderer extends MEFeatureRenderer<CaveTrollEntity, CaveTrollEntityRenderState, CaveTrollEntityModel> {
    public CaveTrollHeldItemFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        this.getContextModel().setArmAngle(matrices);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.translate(0.3f, 0, -2.6);
        matrices.scale(1.5f,1.5f,1.5f);

        if (state.entity instanceof net.minecraft.entity.LivingEntity living) {
            ItemStack held = living.getStackInHand(net.minecraft.util.Hand.MAIN_HAND);
            if (!held.isEmpty()) {
                MinecraftClient.getInstance().getItemRenderer().renderItem(held, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND,
                        light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, living.getWorld(), living.getId());
            }
        }
    }

}
