package net.jukoz.me.entity.beasts.warg.features;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.WargEntityRenderState;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;

public class WargSaddleFeatureRenderer extends MEFeatureRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private final WargSaddleModel model;

    public WargSaddleFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargSaddleModel(loader.getModelPart(EntityModelLayersME.WARG_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean hasArmor = !state.armor.isEmpty();
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new Identifier(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_saddle.png")), true, itemStack.hasGlint());

            model.setAngles(state);
            model.renderSaddle(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}