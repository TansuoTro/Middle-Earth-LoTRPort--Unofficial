package net.jukoz.me.entity.beasts.cave_troll.feature;

import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollDroolFeatureRenderer extends MEFeatureRenderer<CaveTrollEntity, CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_green_drooling.png");
    public CaveTrollDroolFeatureRenderer(RenderLayerParent<CaveTrollEntity, CaveTrollEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(state.tameness < 50 && state.isTame) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
