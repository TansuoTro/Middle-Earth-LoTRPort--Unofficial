package net.jukoz.me.block.special.fire_of_orthanc;
import net.jukoz.me.compat.client.render.MEEntityRenderer;

import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModDecorativeBlocks;

public class FireOfOrthancEntityRenderer extends MEEntityRenderer<FireOfOrthancEntity, FireOfOrthancEntityRenderState> {
    private final BlockRenderManager blockRenderManager;

    public FireOfOrthancEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
        this.blockRenderManager = net.minecraft.client.MinecraftClient.getInstance().getBlockRenderManager();
    }

    @Override
    public FireOfOrthancEntityRenderState createRenderState() {
        return new FireOfOrthancEntityRenderState();
    }


    public void render(FireOfOrthancEntity fireOfOrthancEntity, float yaw, float tickDelta, PoseStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        BlockState blockState = ModDecorativeBlocks.FIRE_OF_ORTHANC.getDefaultState();
        if (blockState.getRenderType() != BlockRenderType.MODEL) {
            return;
        }
        Level world = fireOfOrthancEntity.getWorld();
        if (blockState == world.getBlockState(fireOfOrthancEntity.getBlockPos()) || blockState.getRenderType() == BlockRenderType.INVISIBLE) {
            return;
        }
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));

        float scale = ((float) fireOfOrthancEntity.age / 36) + 1.0f;
        scale = Math.min(1.2f, scale);
        matrixStack.scale(scale, scale, scale);

        BlockPos blockPos = BlockPos.ofFloored(fireOfOrthancEntity.getX(), fireOfOrthancEntity.getBoundingBox().maxY, fireOfOrthancEntity.getZ());
        matrixStack.translate(-0.5, 0.0, -0.5);

        //TODO to test if this worky
        ((net.jukoz.me.compat.MERenderManagerAccess) this.blockRenderManager).renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV);

        /*this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState,
                blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false,
                Random.create(), 0, OverlayTexture.DEFAULT_UV);*/
        matrixStack.pop();
        super.render(new FireOfOrthancEntityRenderState(), matrixStack, vertexConsumerProvider, light);
    }
}
