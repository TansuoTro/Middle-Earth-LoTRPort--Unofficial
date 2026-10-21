package net.jukoz.me.compat;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Accessor for the Fabric extensions on {@code BlockRenderDispatcher}. The patched class
 * exposes covariant versions of these methods whose Fabric return type is not present on
 * the compile classpath, so call through this interface instead.
 */
public interface MERenderManagerAccess {
    void renderBlockAsEntity(BlockState state, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay);

    ModelBlockRenderer getModelRenderer();
}
