package net.jukoz.me.entity.orcs.misties;

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

public class MistyGoblinRenderer extends HumanoidMobRenderer<MistyGoblinEntity, MistyGoblinModel<MistyGoblinEntity>> {
    private static final String PATH = "textures/entities/orcs/misties/";

    public MistyGoblinRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MistyGoblinModel<>(ctx.getPart(ModEntityModelLayers.ORC)), 0.5f);
        this.addFeature(new HumanoidArmorLayer<>(this, new MistyGoblinModel(ctx.getPart(ModelLayers.PLAYER_INNER_ARMOR)),
                new MistyGoblinModel(ctx.getPart(ModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

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
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
