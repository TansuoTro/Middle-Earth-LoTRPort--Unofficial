package net.jukoz.me.compat;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Accessor for the Fabric extensions on {@code BlockRenderManager}. The patched class
 * exposes covariant versions of these methods whose Fabric return type is not present on
 * the compile classpath, so call through this interface instead.
 */
public interface MERenderManagerAccess {
    void renderBlockAsEntity(BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    BlockModelRenderer getModelRenderer();
}
