package net.jukoz.me.entity.dwarves.longbeards;

import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class LongbeardDwarfRenderer extends HumanoidMobRenderer<LongbeardDwarfEntity, LongbeardDwarfModel<LongbeardDwarfEntity>> {
    private static final String PATH = "textures/entities/dwarves/durin/";
    private static final float SIZE = 0.78f;

    public LongbeardDwarfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new LongbeardDwarfModel(ctx.getPart(ModEntityModelLayers.DWARF)), 0.5f);
        this.addFeature(new HumanoidArmorLayer<>(this, new LongbeardDwarfModel(ctx.getPart(ModelLayers.PLAYER_INNER_ARMOR)), new LongbeardDwarfModel(ctx.getPart(ModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    public static final Map<LongbeardDwarfVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(LongbeardDwarfVariant.class), (resourceLocation) -> {
                resourceLocation.put(LongbeardDwarfVariant.GINGER,
                        PATH + "dwarf1.png");
                resourceLocation.put(LongbeardDwarfVariant.BLACK,
                        PATH + "dwarf2.png");
                resourceLocation.put(LongbeardDwarfVariant.OLD,
                        PATH + "dwarf3.png");
                resourceLocation.put(LongbeardDwarfVariant.DARK_BLONDE,
                        PATH + "dwarf4.png");
                resourceLocation.put(LongbeardDwarfVariant.BLACK_BALD,
                        PATH + "dwarf5.png");
                resourceLocation.put(LongbeardDwarfVariant.GINGER_NORI,
                        PATH + "dwarf6.png");
                resourceLocation.put(LongbeardDwarfVariant.BLACK_NORI,
                        PATH + "dwarf7.png");
                resourceLocation.put(LongbeardDwarfVariant.DARK_BLONDE_NORI,
                        PATH + "dwarf8.png");
            });

    @Override
    public ResourceLocation getTexture(LongbeardDwarfEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(LongbeardDwarfEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(SIZE, SIZE, SIZE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
