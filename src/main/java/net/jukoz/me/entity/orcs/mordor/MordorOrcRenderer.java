package net.jukoz.me.entity.orcs.mordor;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.model.geom.ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MordorOrcRenderer extends HumanoidMobRenderer<MordorOrcEntity, MordorOrcModel<MordorOrcEntity>> {
    private static final String PATH = "textures/entities/orcs/mordor/";

    public MordorOrcRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MordorOrcModel<>(ctx.bakeLayer(ModEntityModelLayers.ORC)), 0.5f);
        this.addFeature(new HumanoidArmorLayer<>(this, new MordorOrcModel(ctx.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new MordorOrcModel(ctx.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public ResourceLocation getTexture(MordorOrcEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MordorOrcVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MordorOrcVariant.class), (resourceLocation) -> {
                resourceLocation.put(MordorOrcVariant.LIGHT_BROWN_RED,
                        PATH + "orc1.png");
                resourceLocation.put(MordorOrcVariant.PALE_BLUE_YELLOW,
                        PATH + "orc2.png");
                resourceLocation.put(MordorOrcVariant.PALE_GREY_ORANGE,
                        PATH + "orc3.png");
            });

    @Override
    public void render(MordorOrcEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.8f, 0.8f, 0.8f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
