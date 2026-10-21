package net.jukoz.me.entity.beasts.broadhoof.features;

import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatBeads;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatPattern;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;

public class BroadhoofGoatBeadsFeatureRenderer extends MEFeatureRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/beads/";
    private static final ResourceLocation INVISIBLE_ID = new ResourceLocation("minecraft", "invisible");

    private static final Map<BroadhoofGoatBeads, ResourceLocation> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatBeads.NONE,
                    INVISIBLE_ID,
                    BroadhoofGoatBeads.LEATHER,
                    MiddleEarth.of(PATH + "broadhoof_goat_leather_beads.png"),
                    BroadhoofGoatBeads.COAL,
                    MiddleEarth.of(PATH + "broadhoof_goat_coal_beads.png"),
                    BroadhoofGoatBeads.COPPER,
                    MiddleEarth.of(PATH + "broadhoof_goat_copper_beads.png"),
                    BroadhoofGoatBeads.GOLD,
                    MiddleEarth.of(PATH + "broadhoof_goat_gold_beads.png"),
                    BroadhoofGoatBeads.ALMANDINE,
                    MiddleEarth.of(PATH + "broadhoof_goat_almandine_beads.png")
            )
    );

    public BroadhoofGoatBeadsFeatureRenderer(RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(
            PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, BroadhoofGoatEntityRenderState state, float f, float g
    ) {
        ResourceLocation identifier = (ResourceLocation)TEXTURES.get(state.beads);
        if (identifier != INVISIBLE_ID && !state.invisible) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityTranslucent(identifier));
            this.getParentModel().render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
