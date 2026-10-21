package net.jukoz.me.entity.spider.scuttler;

import net.jukoz.me.compat.client.render.MEAnimationStates;
import net.jukoz.me.compat.client.render.MEMobEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ShelobiteScuttlerRenderer extends MEMobEntityRenderer<ShelobiteScuttlerEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {

    public ShelobiteScuttlerRenderer(EntityRendererProvider.Context context) {
        this(context, 0.45F, EntityModelLayersME.SHELOBITE_SCUTTLER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteScuttlerRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public ResourceLocation getTexture(ShelobiteScuttlerRenderState state) {
        return state.spiderVariant.assetInfo().scuttler().texturePath();
    }

    public void updateRenderState(ShelobiteScuttlerEntity shelobiteScuttlerEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(shelobiteScuttlerEntity, shelobiteScuttlerEntityRenderState, f);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.idleAnimationState, shelobiteScuttlerEntity.idleAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.walkAnimationState, shelobiteScuttlerEntity.walkingAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.biteAnimationState, shelobiteScuttlerEntity.biteAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.pounceAnimationState, shelobiteScuttlerEntity.pounceAnimation);
        shelobiteScuttlerEntityRenderState.climbingTicks = shelobiteScuttlerEntity.getClimbingTicks();
        shelobiteScuttlerEntityRenderState.leapingTicks = shelobiteScuttlerEntity.getLeapingTicks();
        shelobiteScuttlerEntityRenderState.spiderVariant = shelobiteScuttlerEntity.getVariant();
    }
}
