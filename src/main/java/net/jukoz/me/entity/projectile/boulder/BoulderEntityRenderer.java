package net.jukoz.me.entity.projectile.boulder;

import net.jukoz.me.MiddleEarth;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.RenderShape;

public class BoulderEntityRenderer extends EntityRenderer<BoulderEntity> {
    private final BlockRenderDispatcher blockRenderManager;
    public BoulderEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.blockRenderManager = ctx.getBlockRenderManager();
    }

    @Override
    public ResourceLocation getTexture(BoulderEntity entity) {
        return new ResourceLocation("minecraft", "textures/block/stone.png");
    }

    @Override
    public void render(BoulderEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if(entity.getOwner() == null) {
            return;
        }
        BlockState blockState = Blocks.STONE.defaultBlockState();
        if (blockState.getRenderType() != RenderShape.MODEL) {
            return;
        }
        Level world = entity.level();
        if (blockState == world.getBlockState(entity.blockPosition()) || blockState.getRenderType() == RenderShape.INVISIBLE) {
            return;
        }
        matrices.push();
        matrices.multiply(Axis.POSITIVE_Z.rotationDegrees(Mth.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        BlockPos blockPos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
        matrices.translate(-0.5, 0.0, -0.5);

        this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState, blockPos, matrices, vertexConsumers.getBuffer(ItemBlockRenderTypes.getMovingBlockLayer(blockState)), false, RandomSource.create(), blockState.getRenderingSeed(entity.getOwner().getBlockPos()), OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
