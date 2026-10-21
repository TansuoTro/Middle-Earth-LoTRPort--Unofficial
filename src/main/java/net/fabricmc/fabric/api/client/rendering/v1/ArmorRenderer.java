package net.fabricmc.fabric.api.client.rendering.v1;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;

/**
 * Forge compat shim for Fabric's {@code ArmorRenderer}. The render-part helper delegates to
 * Forge's own {@code net.minecraftforge.client.extensions.common.IClientItemExtensions} armor
 * pipeline; the port only uses the static part renderer, which Forge exposes through the
 * same vanilla model machinery.
 */
public final class ArmorRenderer {
    private ArmorRenderer() {}

    public static void renderPart(PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                                  ItemStack stack, Model model, ResourceLocation texture) {
        renderPart(matrices, vertexConsumers, light, model, 1.0F, 1.0F, 1.0F, 1.0F, texture);
    }

    public static void renderPart(PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                                  Model model, float red, float green, float blue, float alpha,
                                  ResourceLocation texture) {
        renderVanillaPart(matrices, vertexConsumers, light, model, red, green, blue, alpha, texture);
    }

    private static void renderVanillaPart(PoseStack matrices, MultiBufferSource vertexConsumers,
                                          int light, Model model, float red, float green, float blue,
                                          float alpha, ResourceLocation texture) {
        model.renderToBuffer(matrices, vertexConsumers.getBuffer(
                        net.minecraft.client.renderer.RenderType.armorCutoutNoCull(texture)),
                light, net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                red, green, blue, alpha);
    }
}
