package net.jukoz.me.entity.npcs.renderer.features.feet;

import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.model.EntityModel;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
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
public class FeetFeatureRenderer extends MEFeatureRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final FeetModel feetModel;
    private final TextureAtlas characterTexturesAtlas;

    public FeetFeatureRenderer(RenderLayerParent<NpcEntity, NpcEntityModel> context, LoadedEntityModelsCompat loader) {
        super(context);
        this.feetModel = new FeetModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_FEET));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        ResourceLocation feetId =  (isSimplified) ? state.simplifiedFeetId : MiddleEarth.ofPrefix(state.feetId, AtlasesME.SKIN_PREFIX);

        feetModel.setAngles(state);

        if(!state.canShowFeet)
            return;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        if(feetId != null){
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(feetId);
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
        }
    }

    public static int getOverlay(net.jukoz.me.compat.client.render.state.BipedEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }

    private void renderModel(TextureAtlasSprite sprite, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            feetModel.render(matrices, newLayerVertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
