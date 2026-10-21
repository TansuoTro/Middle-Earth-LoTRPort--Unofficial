package net.jukoz.me.block.special.pots;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.layers.RenderLayers;
import net.minecraft.util.Mth;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity> {
    private static final float POSITIVE_WOBBLE_SCALE = 0.015625F;
    private final DecoratedPotBlockEntityRenderer vanillaRenderer;
    private final BlockRenderManager blockRenderManager;

    public LootablePotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.vanillaRenderer = new DecoratedPotBlockEntityRenderer(context);
        this.blockRenderManager = context.getRenderManager();
    }

    @Override
    public void render(DecoratedPotBlockEntity entity, float tickProgress, PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        if (!(state.getBlock() instanceof LootablePotBlock)) {
            vanillaRenderer.render(entity, tickProgress, matrices, vertexConsumers, light, overlay);
            return;
        }

        matrices.push();
        ((net.jukoz.me.compat.MERenderManagerAccess) blockRenderManager).getModelRenderer().render(
                matrices.peek(),
                vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state, true)),
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
