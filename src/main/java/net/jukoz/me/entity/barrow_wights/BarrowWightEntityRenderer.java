package net.jukoz.me.entity.barrow_wights;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BarrowWightEntityRenderer extends MobRenderer<BarrowWightEntity, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/";

    public BarrowWightEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BarrowWightModel(ctx.getPart(ModEntityModelLayers.BARROW_WIGHT)), 0.5f);
    }

    @Override
    public ResourceLocation getTexture(BarrowWightEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<BarrowWightVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BarrowWightVariant.class), (resourceLocation) -> {
                resourceLocation.put(BarrowWightVariant.BASIC, PATH + "barrow_wight0.png");
            });
}
