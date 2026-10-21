package net.jukoz.me.entity.spider.spawn;

import net.jukoz.me.compat.client.render.MEAnimationStates;
import net.jukoz.me.compat.client.render.MEMobEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpawnOfShelobRenderer extends MEMobEntityRenderer<SpawnOfShelobEntity, SpawnOfShelobRenderState, SpawnOfShelobModel> {
    private static final String PATH = "textures/entities/spiders/";

    public SpawnOfShelobRenderer(EntityRendererProvider.Context context) {
        this(context, 0.75F, EntityModelLayersME.SPAWN_OF_SHELOB);
    }

    @Override
    public SpawnOfShelobRenderState createRenderState() {
        return new SpawnOfShelobRenderState();
    }

    protected SpawnOfShelobRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new SpawnOfShelobModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public ResourceLocation getTexture(SpawnOfShelobRenderState state) {
        return state.spiderVariant.assetInfo().spawnOfShelob().texturePath();
    }

    public void updateRenderState(SpawnOfShelobEntity spawnofShelobEntity, SpawnOfShelobRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(spawnofShelobEntity, shelobiteScuttlerEntityRenderState, f);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.idleAnimationState, spawnofShelobEntity.idleAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.walkAnimationState, spawnofShelobEntity.walkingAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.biteAnimationState, spawnofShelobEntity.biteAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.blockAnimationState, spawnofShelobEntity.blockAnimation);
        MEAnimationStates.copyFrom(shelobiteScuttlerEntityRenderState.pounceAnimationState, spawnofShelobEntity.pounceAnimation);
        shelobiteScuttlerEntityRenderState.timelineTicks = spawnofShelobEntity.getTimelineTicks();
        shelobiteScuttlerEntityRenderState.climbingTicks = spawnofShelobEntity.getClimbingTicks();
        shelobiteScuttlerEntityRenderState.leapingTicks = spawnofShelobEntity.getLeapingTicks();
        shelobiteScuttlerEntityRenderState.spiderVariant = spawnofShelobEntity.getVariant();
    }
}
