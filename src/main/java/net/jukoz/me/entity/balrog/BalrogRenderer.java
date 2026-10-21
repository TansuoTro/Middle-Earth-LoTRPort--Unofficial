package net.jukoz.me.entity.balrog;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BalrogRenderer extends MobRenderer<BalrogEntity, BalrogModel<BalrogEntity>> {
    private static final String PATH = "textures/entities/balrog/";
    private static final float SIZE = 2f;

    public BalrogRenderer(EntityRendererProvider.Context context) {
        this(context, 0.35F, ModEntityModelLayers.BALROG);
    }

    protected BalrogRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new BalrogModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    protected float getLyingAngle(BalrogEntity balrogEntity) {
        return 180.0F;
    }

    public static final Map<BalrogVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BalrogVariant.class), (resourceLocation) -> {
                resourceLocation.put(BalrogVariant.BASE,
                        PATH + "balrog1.png");
            });

    public ResourceLocation getTexture(BalrogEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(BalrogEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(SIZE, SIZE, SIZE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
