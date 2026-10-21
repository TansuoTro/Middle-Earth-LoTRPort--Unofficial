package net.jukoz.me.entity.beasts.great_horn.features;

import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.item.MEComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.jukoz.me.entity.beasts.great_horn.GreatHornModel;
import net.jukoz.me.item.utils.armor.DyeablePiecesME;

public class GreatHornArmorFeatureRenderer extends MEFeatureRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornArmorModel model;
    private final EquipmentRenderer equipmentRenderer;

    public GreatHornArmorFeatureRenderer(RenderLayerParent<GreatHornEntity, GreatHornModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornArmorModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN_ARMOR));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        ResourceLocation equippableComponent = MEComponents.get(itemStack, MEComponents.EQUIPPABLE_ASSET);
        if (equippableComponent != null && !equippableComponent.getPath().isEmpty()) {
            model.setAngles(state);
            String path = "textures/entities/great_horn/feature/" + equippableComponent.getPath() + ".png";
            boolean dyeable = false;
            if (itemStack.getItem() instanceof net.minecraft.world.item.DyeableLeatherItem) {
                dyeable = true;
            }
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new ResourceLocation(MiddleEarth.MOD_ID, path)), true, itemStack.hasGlint());

            if(dyeable){
                int color = itemStack.getItem() instanceof net.minecraft.world.item.DyeableLeatherItem dyeableItem
                    ? dyeableItem.getColor(itemStack) : DyedColorComponent.DEFAULT_COLOR;
                float red = ((color >> 16) & 255) / 255.0F;
                float green = ((color >> 8) & 255) / 255.0F;
                float blue = (color & 255) / 255.0F;
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
                if(DyeablePiecesME.dyeablePieces.get(itemStack.getItem())) {
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, model,
                            new ResourceLocation(MiddleEarth.MOD_ID, path.replaceAll(".png", "_overlay.png")));
                }
            } else {
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
