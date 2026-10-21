package net.jukoz.me.entity.beasts.great_horn.features;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.jukoz.me.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleFeatureRenderer extends MEFeatureRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornSaddleModel model;
    private final static String PATH = "textures/entities/great_horn/feature/great_horn_saddle";

    public GreatHornSaddleFeatureRenderer(FeatureRendererContext<GreatHornEntity, GreatHornModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornSaddleModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN_SADDLE));
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean blueSaddle = state.blueSaddle;
        String suffix = "";
        if(blueSaddle) suffix = "_blue";
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new ResourceLocation(MiddleEarth.MOD_ID, PATH + suffix + ".png")), true, itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
