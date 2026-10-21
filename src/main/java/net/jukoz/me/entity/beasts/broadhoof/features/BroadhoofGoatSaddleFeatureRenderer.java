package net.jukoz.me.entity.beasts.broadhoof.features;

import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import com.mojang.blaze3d.vertex.PoseStack;

public class BroadhoofGoatSaddleFeatureRenderer extends MEFeatureRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private final BroadhoofGoatSaddleModel model;

    public BroadhoofGoatSaddleFeatureRenderer(RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);

        this.model = new BroadhoofGoatSaddleModel(loader.getModelPart(EntityModelLayersME.BROADHOOF_GOAT_SADDLE));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/broadhoof_goat/feature/broadhoof_goat_saddle.png")), true, itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
