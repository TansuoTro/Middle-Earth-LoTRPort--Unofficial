package net.jukoz.me.entity.npcs.renderer.features.hair;

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
public class HairFeatureRenderer extends MEFeatureRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final HairModel hairModel;
    private final TextureAtlas characterTexturesAtlas;

    public HairFeatureRenderer(RenderLayerParent<NpcEntity, NpcEntityModel> context, LoadedEntityModelsCompat loader) {
        super(context);
        this.hairModel = new HairModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_HAIR));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        HairModel entityModel = hairModel;

        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        ResourceLocation hairAddonTextureId = (isSimplified) ? state.simplifiedHairAddonId : MiddleEarth.ofPrefix(state.hairAddonId, AtlasesME.HAIR_PREFIX);
        ResourceLocation beardAddonTextureId =  (isSimplified) ? null : MiddleEarth.ofPrefix(state.beardAddonId, AtlasesME.HAIR_PREFIX);

        // TODO : Disable the beard based on the helmet
        /*
        if (entity.getEquippedStack(EquipmentSlot.HEAD).is(TagK°.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "helmet_hides_dwarf_beard")))){
         */

        if(hairAddonTextureId == null && beardAddonTextureId == null){
            entityModel.getPart().visible = false;
            return;
        } else if (!entityModel.getPart().visible){
            entityModel.getPart().visible = true;
        }
        entityModel.setAngles(state);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        if(hairAddonTextureId != null && state.canShowHair){
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(hairAddonTextureId);
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
        }
        if(beardAddonTextureId != null && state.canShowBeard){
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(beardAddonTextureId);
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
        }
    }

    private void renderModel(TextureAtlasSprite sprite, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            hairModel.render(matrices, newLayerVertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public static int getOverlay(net.jukoz.me.compat.client.render.state.BipedEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }
}
