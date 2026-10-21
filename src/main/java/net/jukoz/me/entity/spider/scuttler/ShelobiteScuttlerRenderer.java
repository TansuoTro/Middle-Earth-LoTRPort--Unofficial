package net.jukoz.me.entity.spider.scuttler;
import net.jukoz.me.compat.client.render.MEAnimationStates;
import net.jukoz.me.compat.client.render.MEMobEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.model.EntityModelLayer;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.entity.EntityModelLayersME;

public class ShelobiteScuttlerRenderer extends MEMobEntityRenderer<ShelobiteScuttlerEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {

    public ShelobiteScuttlerRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, EntityModelLayersME.SHELOBITE_SCUTTLER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteScuttlerRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.getPart(layer)), shadowRadius);
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
