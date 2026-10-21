package net.jukoz.me.entity.beasts.cave_troll.feature;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
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
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollSaddleFeatureRenderer extends MEFeatureRenderer<CaveTrollEntity, CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private final CaveTrollSaddleModel model;

    public CaveTrollSaddleFeatureRenderer(FeatureRendererContext<CaveTrollEntity, CaveTrollEntityModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);

        model = new CaveTrollSaddleModel(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_SADDLE));
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_platform.png")), true, itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
