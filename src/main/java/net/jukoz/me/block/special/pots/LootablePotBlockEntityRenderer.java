package net.jukoz.me.block.special.pots;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity> {
    private static final float POSITIVE_WOBBLE_SCALE = 0.015625F;
    private final DecoratedPotRenderer vanillaRenderer;
    private final BlockRenderDispatcher blockRenderManager;

    public LootablePotBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.vanillaRenderer = new DecoratedPotRenderer(context);
        this.blockRenderManager = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(DecoratedPotBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState state = entity.getBlockState();
        if (!(state.getBlock() instanceof LootablePotBlock)) {
            vanillaRenderer.render(entity, tickProgress, matrices, vertexConsumers, light, overlay);
            return;
        }

        matrices.push();
        ((net.jukoz.me.compat.MERenderManagerAccess) blockRenderManager).getModelRenderer().render(
                matrices.peek(),
                vertexConsumers.getBuffer(ItemBlockRenderTypes.getEntityBlockLayer(state, true)),
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
