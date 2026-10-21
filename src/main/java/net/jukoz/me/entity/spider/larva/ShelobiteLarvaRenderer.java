package net.jukoz.me.entity.spider.larva;

import net.jukoz.me.compat.client.render.MEAnimationStates;
import net.jukoz.me.compat.client.render.MEMobEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ShelobiteLarvaRenderer extends MEMobEntityRenderer<ShelobiteLarvaEntity, ShelobiteScuttlerRenderState, ShelobiteLarvaModel> {
    private static final String PATH = "textures/entities/spiders/";

    public ShelobiteLarvaRenderer(EntityRendererProvider.Context context) {
        this(context, 0.2F, EntityModelLayersME.SHELOBITE_LARVA);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteLarvaRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new ShelobiteLarvaModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public ResourceLocation getTexture(ShelobiteScuttlerRenderState state) {
        return state.spiderVariant.assetInfo().larva().texturePath();
    }

    public void updateRenderState(ShelobiteLarvaEntity larvaEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(larvaEntity, shelobiteScuttlerEntityRenderState, f);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.walkAnimationState, larvaEntity.walkingAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.biteAnimationState, larvaEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.spiderVariant = larvaEntity.getVariant();
    }

    @Override
    public void render(ShelobiteScuttlerRenderState livingEntityRenderState, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
