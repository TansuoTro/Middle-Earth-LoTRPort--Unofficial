package net.jukoz.me.compat.client.render.equipment;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * 1.20.1 bridge for 1.21.x {@code net.minecraft.client.render.entity.equipment.EquipmentRenderer}.
 * There is no data-driven equipment-asset registry on 1.20.1, so render() draws the model
 * directly with an armor cutout layer at the 1.21.8 vanilla texture path
 * {@code textures/entity/equipment/<layer_type>/<asset_id>.png}.
 */
public class EquipmentRenderer {

    public static final EquipmentRenderer INSTANCE = new EquipmentRenderer();

    public void render(EquipmentModel.LayerType layerType, ResourceLocation assetId, Model model,
                       ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        ResourceLocation texture = new ResourceLocation(assetId.getNamespace(),
                "textures/entity/equipment/" + layerType.getName() + "/" + assetId.getPath() + ".png");
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers,
                RenderLayer.getArmorCutoutNoCull(texture), true, stack.hasGlint());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    /** Donor 1.21.8 call sites (thrown spear) delegate plain item rendering through the equipment renderer. */
    public void renderItem(ItemStack stack, ItemDisplayContext mode, int light, int overlay,
                           PoseStack matrices, MultiBufferSource vertexConsumers, Level world, int seed) {
        Minecraft.getInstance().getItemRenderer()
                .renderItem(stack, mode, light, overlay, matrices, vertexConsumers, world, seed);
    }
}
