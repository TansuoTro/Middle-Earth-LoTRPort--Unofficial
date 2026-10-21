package net.jukoz.me.entity.spider;

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

public class MirkwoodSpiderRenderer extends MobRenderer<MirkwoodSpiderEntity, MirkwoodSpiderModel> {
    private static final String PATH = "textures/entities/spiders/";

    public MirkwoodSpiderRenderer(EntityRendererProvider.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SPIDER);
    }

    protected MirkwoodSpiderRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new MirkwoodSpiderModel(ctx.getPart(layer)), shadowRadius);
    }

    @Override
    public ResourceLocation getTexture(MirkwoodSpiderEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MirkwoodSpiderVariants, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MirkwoodSpiderVariants.class), (resourceLocation) -> {
                resourceLocation.put(MirkwoodSpiderVariants.BLACK,
                        PATH + "spider1.png");
                resourceLocation.put(MirkwoodSpiderVariants.BROWN,
                        PATH + "spider2.png");
                resourceLocation.put(MirkwoodSpiderVariants.DARK_GREEN,
                        PATH + "spider3.png");
            });

    @Override
    public void render(MirkwoodSpiderEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.age > MirkwoodSpiderEntity.ADULT_AGE){
            poseStack.scale(1,1,1);
        } else {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
