package net.jukoz.me.entity.npcs.renderer.features.ear;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.model.EntityModel;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.ModTexturedRenderLayers;
import net.jukoz.me.config.ModClientConfigs;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.npcs.renderer.NpcEntityModel;
import net.jukoz.me.entity.npcs.renderer.NpcEntityRenderState;
import net.jukoz.me.registries.AtlasesME;

@OnlyIn(Dist.CLIENT)
public class EarFeatureRenderer extends MEFeatureRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final EarModel earModel;
    private final SpriteAtlasTexture characterTexturesAtlas;

    public EarFeatureRenderer(FeatureRendererContext<NpcEntity, NpcEntityModel> context, LoadedEntityModelsCompat loader) {
        super(context);
        this.earModel = new EarModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_EAR));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        ResourceLocation earId =  (isSimplified) ? state.simplifiedEarId : MiddleEarth.ofPrefix(state.earId, AtlasesME.SKIN_PREFIX);

        earModel.setAngles(state);

        if(!state.canShowEars)
            return;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;
        if(earId != null){
            Sprite sprite = characterTexturesAtlas.getSprite(earId);
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
        }
    }

    public static int getOverlay(net.jukoz.me.compat.client.render.state.BipedEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }

    private void renderModel(Sprite sprite, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            earModel.render(matrices, newLayerVertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
