package net.jukoz.me.entity.beasts.cave_troll.feature;

import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.entity.HumanoidArm;
import com.mojang.math.Axis;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityRenderState;
import net.minecraft.world.InteractionHand;

public class CaveTrollHeldItemFeatureRenderer extends MEFeatureRenderer<CaveTrollEntity, CaveTrollEntityRenderState, CaveTrollEntityModel> {
    public CaveTrollHeldItemFeatureRenderer(RenderLayerParent context) {
        super(context);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        this.getParentModel().setArmAngle(matrices);
        matrices.multiply(Axis.POSITIVE_X.rotationDegrees(-90.0F));
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.translate(0.3f, 0, -2.6);
        matrices.scale(1.5f,1.5f,1.5f);

        if (state.entity instanceof net.minecraft.world.entity.LivingEntity living) {
            ItemStack held = living.getItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND);
            if (!held.isEmpty()) {
                Minecraft.getInstance().getItemRenderer().renderItem(held, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, living.getWorld(), living.getId());
            }
        }
    }

}
