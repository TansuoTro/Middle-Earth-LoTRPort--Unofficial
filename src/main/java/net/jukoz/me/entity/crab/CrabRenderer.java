package net.jukoz.me.entity.crab;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModelLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel> {
    private static final String PATH = "textures/entities/crabs/";
    private static final float SIZE = 1f;

    public CrabRenderer(EntityRendererProvider.Context context) {
        this(context, 0.35F, ModEntityModelLayers.CRAB);
    }

    protected CrabRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new CrabModel(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(CrabEntity crabEntity) {
        return 180.0F;
    }

    public static final Map<CrabVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CrabVariant.class), (resourceLocation) -> {
                resourceLocation.put(CrabVariant.ORANGE,
                        PATH + "crab1.png");
                resourceLocation.put(CrabVariant.DARK_BROWN,
                        PATH + "crab2.png");
            });

    public ResourceLocation getTexture(CrabEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            float initialBabySize = (SIZE / 4);
            float size = initialBabySize + ((SIZE - initialBabySize) / 24000) * entity.age;
            poseStack.scale(size, size, size);
        } else {
            poseStack.scale(SIZE, SIZE, SIZE);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
