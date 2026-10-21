package net.jukoz.me.entity.deer;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DeerRenderer extends MobRenderer<DeerEntity, DeerModel> {
    private static final String PATH = "textures/entities/deer/";

    public DeerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerModel(context.getPart(ModEntityModelLayers.DEER)), 0.7f);
    }

    @Override
    public ResourceLocation getTexture(DeerEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, PATH + "deer1.png");
    }

}
