package net.jukoz.me.entity.beasts.great_horn.features;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;

import net.jukoz.me.compat.DyedColorComponent;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.jukoz.me.entity.beasts.great_horn.GreatHornModel;
import net.jukoz.me.item.utils.armor.DyeablePiecesME;

public class GreatHornArmorFeatureRenderer extends MEFeatureRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornArmorModel model;
    private final EquipmentRenderer equipmentRenderer;

    public GreatHornArmorFeatureRenderer(FeatureRendererContext<GreatHornEntity, GreatHornModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornArmorModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN_ARMOR));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        Identifier equippableComponent = MEComponents.get(itemStack, MEComponents.EQUIPPABLE_ASSET);
        if (equippableComponent != null && !equippableComponent.getPath().isEmpty()) {
            model.setAngles(state);
            String path = "textures/entities/great_horn/feature/" + equippableComponent.getPath() + ".png";
            boolean dyeable = false;
            if (itemStack.isIn(ItemTags.DYEABLE)) {
                dyeable = true;
            }
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new Identifier(MiddleEarth.MOD_ID, path)), true, itemStack.hasGlint());

            if(dyeable){
                int color = DyedColorComponent.getColor(itemStack, DyedColorComponent.DEFAULT_COLOR);
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
                if(DyeablePiecesME.dyeablePieces.get(itemStack.getItem())) {
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, model,
                            new Identifier(MiddleEarth.MOD_ID, path.replaceAll(".png", "_overlay.png")));
                }
            } else {
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
            }
        }
    }
}
