package net.jukoz.me.entity.swan;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModelLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SwanRenderer extends MobRenderer<SwanEntity, SwanModel<SwanEntity>> {
    private static final String PATH = "textures/entities/swan/";
    private static final float SIZE = 1f;

    public SwanRenderer(EntityRendererProvider.Context context) {
        this(context, 0.35F, ModEntityModelLayers.SWAN);
    }

    protected SwanRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new SwanModel<>(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(SwanEntity swanEntity) {
        return 180.0F;
    }

    public static final Map<SwanVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SwanVariant.class), (resourceLocation) -> {
                resourceLocation.put(SwanVariant.WHITE,
                        PATH + "swan1.png");
            });

    public ResourceLocation getTexture(SwanEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(SwanEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
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
