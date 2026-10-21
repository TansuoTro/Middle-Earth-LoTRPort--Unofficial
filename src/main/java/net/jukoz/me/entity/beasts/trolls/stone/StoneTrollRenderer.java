package net.jukoz.me.entity.beasts.trolls.stone;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StoneTrollRenderer extends MobRenderer<StoneTrollEntity, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public StoneTrollRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneTrollModel(context.getPart(ModEntityModelLayers.STONE_TROLL)), 1.1f);
    }

    @Override
    public ResourceLocation getTexture(StoneTrollEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, PATH + "stone_troll1.png");
    }

    public void render(StoneTrollEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
