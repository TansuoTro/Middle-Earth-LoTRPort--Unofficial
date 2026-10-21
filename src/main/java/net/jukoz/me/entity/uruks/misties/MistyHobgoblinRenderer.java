package net.jukoz.me.entity.uruks.misties;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.model.EntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MistyHobgoblinRenderer extends HumanoidMobRenderer<MistyHobgoblinEntity, MistyHobgoblinModel<MistyHobgoblinEntity>> {
    private static final String PATH = "textures/entities/uruks/misties/";

    public MistyHobgoblinRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MistyHobgoblinModel<>(ctx.getPart(ModEntityModelLayers.URUK)), 0.5f);
        this.addFeature(new HumanoidArmorLayer<>(this, new MistyHobgoblinModel(ctx.getPart(ModelLayers.PLAYER_INNER_ARMOR)),
                new MistyHobgoblinModel(ctx.getPart(ModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    @Override
    public ResourceLocation getTexture(MistyHobgoblinEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MistyHobgoblinVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MistyHobgoblinVariant.class), (resourceLocation) -> {
                resourceLocation.put(MistyHobgoblinVariant.LIGHT_BROWN_RED,
                        PATH + "uruk1.png");
                resourceLocation.put(MistyHobgoblinVariant.PALE_BLUE_YELLOW,
                        PATH + "uruk2.png");
                resourceLocation.put(MistyHobgoblinVariant.PALE_GREY_ORANGE,
                        PATH + "uruk3.png");
            });

    @Override
    public void render(MistyHobgoblinEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.95f, 0.95f, 0.95f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
