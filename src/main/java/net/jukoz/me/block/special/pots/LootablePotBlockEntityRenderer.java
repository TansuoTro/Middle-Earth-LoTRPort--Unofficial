package net.jukoz.me.block.special.pots;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity> {
    private static final float POSITIVE_WOBBLE_SCALE = 0.015625F;
    private final DecoratedPotBlockEntityRenderer vanillaRenderer;
    private final BlockRenderManager blockRenderManager;

    public LootablePotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.vanillaRenderer = new DecoratedPotBlockEntityRenderer(context);
        this.blockRenderManager = context.getRenderManager();
    }

    @Override
    public void render(DecoratedPotBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        if (!(state.getBlock() instanceof LootablePotBlock)) {
            vanillaRenderer.render(entity, tickProgress, matrices, vertexConsumers, light, overlay);
            return;
        }

        matrices.push();
        ((net.jukoz.me.compat.MERenderManagerAccess) blockRenderManager).getModelRenderer().render(
                matrices.peek(),
                vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state)),
                state,
                blockRenderManager.getModel(state),
                1.0F,
                1.0F,
                1.0F,
                light,
                overlay
        );
        matrices.pop();
    }

}
