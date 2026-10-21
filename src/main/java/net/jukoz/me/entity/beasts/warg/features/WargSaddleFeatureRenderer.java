package net.jukoz.me.entity.beasts.warg.features;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.WargEntityRenderState;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import com.mojang.blaze3d.vertex.PoseStack;

public class WargSaddleFeatureRenderer extends MEFeatureRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private final WargSaddleModel model;

    public WargSaddleFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargSaddleModel(loader.getModelPart(EntityModelLayersME.WARG_SADDLE));
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean hasArmor = !state.armor.isEmpty();
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_saddle.png")), true, itemStack.hasGlint());

            model.setAngles(state);
            model.renderSaddle(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}