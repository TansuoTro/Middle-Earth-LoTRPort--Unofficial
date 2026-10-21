package net.jukoz.me.entity.duck;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DuckRenderer extends MobRenderer<DuckEntity, DuckModel<DuckEntity>> {
    private static final String PATH = "textures/entities/duck/";
    private static final float SIZE = 1f;

    public DuckRenderer(EntityRendererProvider.Context context) {
        this(context, 0.35F, ModEntityModelLayers.DUCK);
    }

    protected DuckRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new DuckModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    protected float getLyingAngle(DuckEntity duckEntity) {
        return 180.0F;
    }

    public static final Map<DuckVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DuckVariant.class), (resourceLocation) -> {
                resourceLocation.put(DuckVariant.GRAY,
                        PATH + "duck1.png");
                resourceLocation.put(DuckVariant.BROWN,
                        PATH + "duck2.png");
            });

    public ResourceLocation getTexture(DuckEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(DuckEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            float initialBabySize = (SIZE / 2);
            float size = initialBabySize + ((SIZE - initialBabySize) / 24000) * entity.age;
            poseStack.scale(size, size, size);
        } else {
            poseStack.scale(SIZE, SIZE, SIZE);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    protected float getAnimationProgress(Chicken chickenEntity, float f) {
        float g = Mth.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
        float h = Mth.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.maxWingDeviation);
        return (Mth.sin(g) + 1.0F) * h;
    }
}
