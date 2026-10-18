package net.jukoz.me.gui.render.states;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.special.SpecialGuiElementRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.util.DyeColor;
import net.jukoz.me.gui.render.BannerResultWithScaleGuiElementRenderer;
import net.jukoz.me.gui.render.InstancedGuiElementRenderer;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record BannerResultWithScaleGuiElementRenderState(ModelPart flag, DyeColor baseColor, MEBannerPatterns resultBannerPatterns, int x1, int y1, int x2, int y2, float scale, @Nullable ScreenRect scissorArea, @Nullable ScreenRect bounds) implements InstancedGuiElementRenderState {
    public BannerResultWithScaleGuiElementRenderState(ModelPart flag, DyeColor color, MEBannerPatterns bannerPatterns, int x1, int y1, int x2, int y2, float scale, @Nullable ScreenRect scissorArea) {
        this(flag, color, bannerPatterns, x1, y1, x2, y2, scale, scissorArea, SpecialGuiElementRenderState.createBounds(x1, y1, x2, y2, scissorArea));
    }

    public float scale() {
        return scale;
    }

    @Override
    public InstancedGuiElementRenderer<? extends InstancedGuiElementRenderState> newRenderer(VertexConsumerProvider.Immediate vertexConsumers) {
        return new BannerResultWithScaleGuiElementRenderer(vertexConsumers);
    }
}
