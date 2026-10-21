package net.jukoz.me.block.special.fire_of_orthanc;

import net.jukoz.me.compat.client.render.MEEntityRenderer;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import com.mojang.math.Axis;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.RenderShape;

public class FireOfOrthancEntityRenderer extends MEEntityRenderer<FireOfOrthancEntity, FireOfOrthancEntityRenderState> {
    private final BlockRenderDispatcher blockRenderManager;

    public FireOfOrthancEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
        this.blockRenderManager = net.minecraft.client.Minecraft.getInstance().getBlockRenderManager();
    }

    @Override
    public FireOfOrthancEntityRenderState createRenderState() {
        return new FireOfOrthancEntityRenderState();
    }

    public void render(FireOfOrthancEntity fireOfOrthancEntity, float yaw, float tickDelta, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light) {
        BlockState blockState = ModDecorativeBlocks.FIRE_OF_ORTHANC.getDefaultState();
        if (blockState.getRenderType() != RenderShape.MODEL) {
            return;
        }
        Level world = fireOfOrthancEntity.getWorld();
        if (blockState == world.getBlockState(fireOfOrthancEntity.getBlockPos()) || blockState.getRenderType() == RenderShape.INVISIBLE) {
            return;
        }
        matrixStack.push();
        matrixStack.multiply(Axis.POSITIVE_Y.rotationDegrees(-90.0F));

        float scale = ((float) fireOfOrthancEntity.age / 36) + 1.0f;
        scale = Math.min(1.2f, scale);
        matrixStack.scale(scale, scale, scale);

        BlockPos blockPos = BlockPos.ofFloored(fireOfOrthancEntity.getX(), fireOfOrthancEntity.getBoundingBox().maxY, fireOfOrthancEntity.getZ());
        matrixStack.translate(-0.5, 0.0, -0.5);

        //TODO to test if this worky
        ((net.jukoz.me.compat.MERenderManagerAccess) this.blockRenderManager).renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV);

        /*this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState,
                blockPos, matrixStack, vertexConsumerProvider.getBuffer(ItemBlockRenderTypes.getMovingBlockLayer(blockState)), false,
                RandomSource.create(), 0, OverlayTexture.DEFAULT_UV);*/
        matrixStack.pop();
        super.render(new FireOfOrthancEntityRenderState(), matrixStack, vertexConsumerProvider, light);
    }
}
