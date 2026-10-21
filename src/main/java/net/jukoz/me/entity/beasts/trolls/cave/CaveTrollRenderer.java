package net.jukoz.me.entity.beasts.trolls.cave;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CaveTrollRenderer extends MobRenderer<CaveTrollEntity, CaveTrollModel> {
    private static final String PATH = "textures/entities/trolls/cave/";

    public CaveTrollRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CaveTrollModel(ctx.bakeLayer(ModEntityModelLayers.CAVE_TROLL)), 1.1f);
    }

    @Override
    public ResourceLocation getTexture(CaveTrollEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<CaveTrollVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CaveTrollVariant.class), (resourceLocation) -> {
                resourceLocation.put(CaveTrollVariant.GREY_YELLOW,
                        PATH + "cave_troll1.png");
                resourceLocation.put(CaveTrollVariant.GREY_BLUE,
                        PATH + "cave_troll2.png");
                resourceLocation.put(CaveTrollVariant.GREY_STONE,
                        PATH + "cave_troll3.png");
            });

    public void render(CaveTrollEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
