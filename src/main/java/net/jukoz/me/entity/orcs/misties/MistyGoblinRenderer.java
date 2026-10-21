package net.jukoz.me.entity.orcs.misties;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.model.EntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;

import java.util.Map;

public class MistyGoblinRenderer extends BipedEntityRenderer<MistyGoblinEntity, MistyGoblinModel<MistyGoblinEntity>> {
    private static final String PATH = "textures/entities/orcs/misties/";

    public MistyGoblinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MistyGoblinModel<>(ctx.getPart(ModEntityModelLayers.ORC)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new MistyGoblinModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new MistyGoblinModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public ResourceLocation getTexture(MistyGoblinEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MistyGoblinVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MistyGoblinVariant.class), (resourceLocation) -> {
                resourceLocation.put(MistyGoblinVariant.LIGHT_BROWN_RED,
                        PATH + "orc1.png");
                resourceLocation.put(MistyGoblinVariant.PALE_BLUE_YELLOW,
                        PATH + "orc2.png");
                resourceLocation.put(MistyGoblinVariant.PALE_GREY_ORANGE,
                        PATH + "orc3.png");
            });

    @Override
    public void render(MistyGoblinEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
