package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.RenderUtil;
import net.jukoz.me.entity.beasts.warg.WargEyeVariant;
import net.jukoz.me.entity.beasts.warg.WargEntityRenderState;
import net.jukoz.me.entity.beasts.warg.WargModel;
import java.util.Map;

public class WargEyesFeatureRenderer extends MEFeatureRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/eyes/";
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_eyes_emissive.png");
    private static final Map<WargEyeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargEyeVariant.class), (map) -> {
                map.put(WargEyeVariant.BLUE,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_eyes_blue.png"));
                map.put(WargEyeVariant.ORANGE,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_eyes_orange.png"));
            });

    public WargEyesFeatureRenderer(RenderLayerParent<WargEntity, WargModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        RenderUtil.renderCutoutTexture(this.getParentModel(), matrices, vertexConsumers,
                LOCATION_BY_VARIANT.get(state.eyeVariant), light, OverlayTexture.DEFAULT_UV);

        if(state.haveEmissiveEyes) {
            RenderUtil.renderEmissiveTexture(this.getParentModel(), matrices, vertexConsumers,
                    EMISSIVE_TEXTURE, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
