package net.jukoz.me.client.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.item.ItemDisplayContext;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.Sheets;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeColor;
import net.minecraft.resources.ResourceLocation;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ModBuiltInModelItemRenderer {

    private static final Material BANNER_BASE = new Material(Sheets.BANNER_PATTERNS_ATLAS_TEXTURE, new ResourceLocation("entity/banner_base"));

    private HeaterShieldEntityModel heaterShieldEntityModel;
    private KiteShieldEntityModel kiteShieldEntityModel;
    private RoundShieldEntityModel roundShieldEntityModel;

    private HeldBannerEntityModel heldBannerEntityModel;

    public ModBuiltInModelItemRenderer() {
    }

    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        this.heaterShieldEntityModel = new HeaterShieldEntityModel(Minecraft.getInstance().getEntityModels().getModelPart(MiddleEarthClient.HEATER_SHIELD_LAYER));
        this.kiteShieldEntityModel = new KiteShieldEntityModel(Minecraft.getInstance().getEntityModels().getModelPart(MiddleEarthClient.KITE_SHIELD_LAYER));
        this.roundShieldEntityModel = new RoundShieldEntityModel(Minecraft.getInstance().getEntityModels().getModelPart(MiddleEarthClient.ROUND_SHIELD_LAYER));
        this.heldBannerEntityModel = new HeldBannerEntityModel(Minecraft.getInstance().getEntityModels().getModelPart(MiddleEarthClient.HELD_BANNER_LAYER));

        if (stack.getItem() instanceof CustomBannerShieldItem) {
            MEBannerPatterns bannerPatternsComponent = MEBannerPatterns.get(stack);
            DyeColor dyeColor2 = MEBannerPatterns.getBaseColor(stack);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.is(WeaponItemsME.HEATER_SHIELD)){
                Material spriteIdentifier = bl ? MEModelLoader.HEATER_SHIELD_BASE : MEModelLoader.HEATER_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.heaterShieldEntityModel.getLayer(spriteIdentifier.atlasLocation()), true, stack.hasGlint()));
                this.heaterShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heaterShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.heaterShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.is(WeaponItemsME.KITE_SHIELD)){
                Material spriteIdentifier = bl ? MEModelLoader.KITE_SHIELD_BASE : MEModelLoader.KITE_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.kiteShieldEntityModel.getLayer(spriteIdentifier.atlasLocation()), true, stack.hasGlint()));
                this.kiteShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.kiteShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.kiteShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.is(WeaponItemsME.ROUND_SHIELD)){
                Material spriteIdentifier = bl ? MEModelLoader.ROUND_SHIELD_BASE : MEModelLoader.ROUND_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.roundShieldEntityModel.getLayer(spriteIdentifier.atlasLocation()), true, stack.hasGlint()));
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

            if (stack.is(WeaponItemsME.HELD_BANNER)){
                Material spriteIdentifier = BANNER_BASE;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.heldBannerEntityModel.getLayer(spriteIdentifier.atlasLocation()), true, stack.hasGlint()));
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

    public static void renderCanvas(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ModelPart canvas, Material baseSprite, boolean isBanner, DyeColor color, MEBannerPatterns patterns, boolean glint, ItemStack stack, boolean solid) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
        if (stack.is(WeaponItemsME.HEATER_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? BANNER_BASE : ModTexturedRenderLayers.HEATER_SHIELD_BASE, color);
        } else if (stack.is(WeaponItemsME.KITE_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? BANNER_BASE : ModTexturedRenderLayers.KITE_SHIELD_BASE, color);
        }else if (stack.is(WeaponItemsME.ROUND_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? BANNER_BASE : ModTexturedRenderLayers.ROUND_SHIELD_BASE, color);
        }else if (stack.is(WeaponItemsME.HELD_BANNER)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, BANNER_BASE, color);
        }
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            MEBannerPatterns.Layer layer = (MEBannerPatterns.Layer)patterns.layers().get(i);
            Material spriteIdentifier = isBanner ? Sheets.getBannerPatternTextureId(layer.pattern().getKey().orElseThrow()) : ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            if (stack.is(WeaponItemsME.HEATER_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getHeaterShieldPatternTextureId(layer.pattern());
            } else if (stack.is(WeaponItemsME.KITE_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getKiteShieldPatternTextureId(layer.pattern());
            }else if (stack.is(WeaponItemsME.ROUND_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            } else if (stack.is(WeaponItemsME.HELD_BANNER)){
                spriteIdentifier = Sheets.getBannerPatternTextureId(layer.pattern().getKey().orElseThrow());
            }
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }
    }

    private static void renderLayer(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ModelPart canvas, Material textureId, DyeColor color) {
        float[] components = color.getTextureDiffuseColors();
        canvas.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay,
                components[0], components[1], components[2], 1.0F);
    }
}
