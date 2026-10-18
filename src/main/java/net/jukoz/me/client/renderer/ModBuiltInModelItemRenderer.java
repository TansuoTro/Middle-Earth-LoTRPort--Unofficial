package net.jukoz.me.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.MEModelLoader;
import net.jukoz.me.client.ModTexturedRenderLayers;
import net.jukoz.me.client.model.hand.HeldBannerEntityModel;
import net.jukoz.me.client.model.hand.shields.HeaterShieldEntityModel;
import net.jukoz.me.client.model.hand.shields.KiteShieldEntityModel;
import net.jukoz.me.client.model.hand.shields.RoundShieldEntityModel;
import net.jukoz.me.item.WeaponItemsME;
import net.jukoz.me.item.items.HeldBannerItem;
import net.jukoz.me.item.items.shields.CustomBannerShieldItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;

import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ModBuiltInModelItemRenderer {

    private HeaterShieldEntityModel heaterShieldEntityModel;
    private KiteShieldEntityModel kiteShieldEntityModel;
    private RoundShieldEntityModel roundShieldEntityModel;

    private HeldBannerEntityModel heldBannerEntityModel;

    public ModBuiltInModelItemRenderer() {
    }

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.heaterShieldEntityModel = new HeaterShieldEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.HEATER_SHIELD_LAYER));
        this.kiteShieldEntityModel = new KiteShieldEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.KITE_SHIELD_LAYER));
        this.roundShieldEntityModel = new RoundShieldEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.ROUND_SHIELD_LAYER));
        this.heldBannerEntityModel = new HeldBannerEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.HELD_BANNER_LAYER));

        if (stack.getItem() instanceof CustomBannerShieldItem) {
            MEBannerPatterns bannerPatternsComponent = MEBannerPatterns.get(stack);
            DyeColor dyeColor2 = MEBannerPatterns.getBaseColor(stack);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.isOf(WeaponItemsME.HEATER_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.HEATER_SHIELD_BASE : MEModelLoader.HEATER_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.heaterShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.heaterShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heaterShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.heaterShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.isOf(WeaponItemsME.KITE_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.KITE_SHIELD_BASE : MEModelLoader.KITE_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.kiteShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.kiteShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.kiteShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.kiteShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.isOf(WeaponItemsME.ROUND_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.ROUND_SHIELD_BASE : MEModelLoader.ROUND_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.roundShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.roundShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.roundShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.roundShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            }
        }

        if (stack.getItem() instanceof HeldBannerItem){
            MEBannerPatterns bannerPatternsComponent = MEBannerPatterns.get(stack);
            DyeColor dyeColor2 = MEBannerPatterns.getBaseColor(stack);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.isOf(WeaponItemsME.HELD_BANNER)){
                SpriteIdentifier spriteIdentifier = ModelBaker.BANNER_BASE;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.heldBannerEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.heldBannerEntityModel.getPole().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heldBannerEntityModel.getBanner(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.heldBannerEntityModel.getBanner().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            }
        }
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, DyeColor color, MEBannerPatterns patterns, boolean glint, ItemStack stack, boolean solid) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, solid, glint), light, overlay);
        if (stack.isOf(WeaponItemsME.HEATER_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.HEATER_SHIELD_BASE, color);
        } else if (stack.isOf(WeaponItemsME.KITE_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.KITE_SHIELD_BASE, color);
        }else if (stack.isOf(WeaponItemsME.ROUND_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.ROUND_SHIELD_BASE, color);
        }else if (stack.isOf(WeaponItemsME.HELD_BANNER)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, TexturedRenderLayers.BANNER_BASE, color);
        }
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            MEBannerPatterns.Layer layer = (MEBannerPatterns.Layer)patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            if (stack.isOf(WeaponItemsME.HEATER_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getHeaterShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(WeaponItemsME.KITE_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getKiteShieldPatternTextureId(layer.pattern());
            }else if (stack.isOf(WeaponItemsME.ROUND_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(WeaponItemsME.HELD_BANNER)){
                spriteIdentifier = TexturedRenderLayers.getBannerPatternTextureId(layer.pattern());
            }
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }
    }



    private static void renderLayer(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        canvas.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, i);
    }
}
