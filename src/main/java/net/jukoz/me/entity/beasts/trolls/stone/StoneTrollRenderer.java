package net.jukoz.me.entity.beasts.trolls.stone;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

public class StoneTrollRenderer extends MobEntityRenderer<StoneTrollEntity, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public StoneTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneTrollModel(context.getPart(ModEntityModelLayers.STONE_TROLL)), 1.1f);
    }

    @Override
    public ResourceLocation getTexture(StoneTrollEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, PATH + "stone_troll1.png");
    }

    public void render(StoneTrollEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
