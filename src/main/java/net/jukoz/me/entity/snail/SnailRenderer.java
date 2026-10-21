package net.jukoz.me.entity.snail;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.crab.CrabModel;
import net.jukoz.me.entity.crab.CrabVariant;
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

public class SnailRenderer extends MobRenderer<SnailEntity, SnailModel<SnailEntity>> {

    private static final String PATH = "textures/entities/snails/";

    public SnailRenderer(EntityRendererProvider.Context context) {
        this(context, 0.2F, ModEntityModelLayers.SNAIL);
    }

    protected SnailRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new SnailModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    public static final Map<SnailVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SnailVariant.class), (resourceLocation) -> {
                resourceLocation.put(SnailVariant.WHITE,
                        PATH + "snail1.png");
                resourceLocation.put(SnailVariant.GREEN,
                        PATH + "snail2.png");
            });

    @Override
    public ResourceLocation getTexture(SnailEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(SnailEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
