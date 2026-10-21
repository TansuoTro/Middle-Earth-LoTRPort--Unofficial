package net.jukoz.me.block.special.alloyfurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import com.mojang.math.Axis;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class AlloyFurnaceEntityRenderer implements BlockEntityRenderer<AlloyFurnaceEntity> {
    public AlloyFurnaceEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(AlloyFurnaceEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        //ModelBlockRenderer blockRenderer = Minecraft.getInstance().getBlockRenderManager().getModelRenderer();
        ItemStack output = entity.getRenderStack();

        matrices.push();
        matrices.translate(0.5f, 0.76f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);

        matrices.multiply(Axis.POSITIVE_X.rotation((float) Math.toRadians(-90)));
        switch (entity.getCachedState().get(AlloyFurnace.FACING)) {
            case NORTH -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(180)));
            case EAST -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(90)));
            case SOUTH -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(0)));
            case WEST -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(270)));
        }

        int currentLight = getLightLevel(entity.getWorld(), entity.getPos());

        //if(entity.isCooking()) { // Keep for later
        //    BlockState lavaState = Blocks.LAVA.getDefaultState().with(LiquidBlock.LEVEL, 0);
        //    BakedModel bakedModel = Minecraft.getInstance().getBlockRenderManager().getModel(lavaState);
        //    blockRenderer.render(matrices.peek(), vertexConsumers.getBuffer(Sheets.getEntitySolid()), null, bakedModel,
        //            1.0f, 1.0f, 1.0f, currentLight, OverlayTexture.DEFAULT_UV);
        //}
        if(!output.isEmpty()) {
            itemRenderer.renderItem(output, ItemDisplayContext.GUI, currentLight, OverlayTexture.DEFAULT_UV,
                    matrices, vertexConsumers, entity.getWorld(), 1);
        }

        matrices.pop();
    }

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getLightLevel(LightLayer.BLOCK, pos);
        int sLight = world.getLightLevel(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
