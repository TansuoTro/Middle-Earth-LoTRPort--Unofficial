package net.jukoz.me.entity.beasts.trolls.petrified;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollEntity;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PetrifiedTrollRenderer extends MobRenderer<PetrifiedTrollEntity, PetrifiedTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public PetrifiedTrollRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PetrifiedTrollModel(ctx.bakeLayer(ModEntityModelLayers.PETRIFIED_TROLL)), 0.0f);
    }

    @Override
    public ResourceLocation getTexture(PetrifiedTrollEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, PATH + "petrified_stone_troll.png");
    }
}
