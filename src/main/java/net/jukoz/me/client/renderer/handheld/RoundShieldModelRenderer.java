package net.jukoz.me.client.renderer.handheld;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;

import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.MEModelLoader;
import net.jukoz.me.client.ModTexturedRenderLayers;
import net.jukoz.me.client.model.hand.shields.RoundShieldEntityModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public class RoundShieldModelRenderer implements SpecialModelRenderer<ComponentMap> {

    private final RoundShieldEntityModel model;

    public RoundShieldModelRenderer(RoundShieldEntityModel model) {
        this.model = model;
    }

    @Override
    public void render(@Nullable ComponentMap data, ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        MEBannerPatterns bannerPatternsComponent = data != null ? MEBannerPatterns.get(data) : MEBannerPatterns.DEFAULT;
        DyeColor dyeColor = data != null ? data.get(DataComponentTypes.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl2 ? MEModelLoader.ROUND_SHIELD_BASE : MEModelLoader.ROUND_SHIELD_BASE_NO_PATTERN;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(spriteIdentifier.getAtlasId()), displayContext == ItemDisplayContext.GUI, glint));
        this.model.getHandle().render(matrices, vertexConsumer, light, overlay);
        if (bl2) {
            renderCanvas(matrices, vertexConsumers, light, overlay, this.model.getPlate(), spriteIdentifier, false, (DyeColor)Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, glint, false);
        } else {
            this.model.getPlate().render(matrices, vertexConsumer, light, overlay);
        }

        matrices.pop();
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.getRootPart().collectVertices(matrixStack, vertices);
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, DyeColor color, MEBannerPatterns patterns, boolean glint, boolean solid) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, solid, glint), light, overlay);
        renderLayer(matrices, vertexConsumers, light, overlay, canvas, ModTexturedRenderLayers.ROUND_SHIELD_BASE, color);
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            MEBannerPatterns.Layer layer = patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());

            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }}

    private static void renderLayer(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        canvas.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, i);
    }

    @Nullable
    @Override
    public ComponentMap getData(ItemStack stack) {
        return stack.getImmutableComponents();
    }

    @Environment(EnvType.CLIENT)
    public static record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final RoundShieldModelRenderer.Unbaked INSTANCE = new RoundShieldModelRenderer.Unbaked();
        public static final MapCodec<RoundShieldModelRenderer.Unbaked> CODEC;

        public Unbaked() {
        }



        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new RoundShieldModelRenderer(new RoundShieldEntityModel(entityModels.getModelPart(MiddleEarthClient.ROUND_SHIELD_LAYER)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}
