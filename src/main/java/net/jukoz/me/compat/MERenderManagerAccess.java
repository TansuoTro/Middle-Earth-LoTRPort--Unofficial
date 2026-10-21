package net.jukoz.me.compat;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Accessor for the Fabric extensions on {@code BlockRenderManager}. The patched class
 * exposes covariant versions of these methods whose Fabric return type is not present on
 * the compile classpath, so call through this interface instead.
 */
public interface MERenderManagerAccess {
    void renderBlockAsEntity(BlockState state, PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    BlockModelRenderer getModelRenderer();
}
