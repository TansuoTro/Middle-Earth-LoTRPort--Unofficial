package net.jukoz.me.entity.beasts.broadhoof.features;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatBeads;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatPattern;

import java.util.Map;

public class BroadhoofGoatBeadsFeatureRenderer extends MEFeatureRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/beads/";
    private static final Identifier INVISIBLE_ID = new Identifier("minecraft", "invisible");

    private static final Map<BroadhoofGoatBeads, Identifier> TEXTURES = Maps.newEnumMap(
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

    public BroadhoofGoatBeadsFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntity, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BroadhoofGoatEntityRenderState state, float f, float g
    ) {
        Identifier identifier = (Identifier)TEXTURES.get(state.beads);
        if (identifier != INVISIBLE_ID && !state.invisible) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(identifier));
            this.getContextModel().render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
