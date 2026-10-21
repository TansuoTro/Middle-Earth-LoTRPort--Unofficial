package net.jukoz.me.gui.render;

import com.mojang.datafixers.util.Pair;
import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelPart;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.core.Holder;
import net.minecraft.util.Tuple;

/** The donor's full-size banner preview, rendered through 1.20.1's immediate GUI buffers. */
public final class BannerResultWithScaleGuiElementRenderer {
    private BannerResultWithScaleGuiElementRenderer() { }

    public static void render(GuiGraphics context, ModelPart flag, DyeColor baseColor,
                              MEBannerPatterns patterns, int x, int y, float scale) {
        var layers = new java.util.ArrayList<Tuple<net.minecraft.core.Holder<net.minecraft.world.level.block.entity.BannerPattern>, DyeColor>>();
        Registries.BANNER_PATTERN.getEntry(BannerPatterns.BASE)
                .ifPresent(base -> layers.add(Tuple.of(base, baseColor)));
        for (var layer : patterns.layers()) layers.add(Tuple.of(layer.pattern(), layer.color()));
        Lighting.disableGuiDepthLighting();
        var matrices = context.getMatrices();
        matrices.push();
        try {
            matrices.translate(x, y, 0.0F);
            matrices.scale(scale, scale, 1.0F);
            matrices.translate(0.5F, -0.5F, 0.5F);
            matrices.scale(0.6666667F, 0.6666667F, -0.6666667F);
            flag.pitch = 0.0F;
            flag.pivotY = -32.0F;
            BannerRenderer.renderCanvas(matrices, context.getVertexConsumers(), 15728880,
                    OverlayTexture.DEFAULT_UV, flag, ModelBakery.BANNER_BASE, true, layers);
            context.draw();
        } finally {
            matrices.pop();
            Lighting.enableGuiDepthLighting();
        }
    }
}
