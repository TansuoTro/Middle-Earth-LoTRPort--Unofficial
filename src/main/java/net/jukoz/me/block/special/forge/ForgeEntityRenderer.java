package net.jukoz.me.block.special.forge;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ForgeEntityRenderer implements BlockEntityRenderer<ForgeBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public ForgeEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(ForgeBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getRenderStack(entity);

        if(stack.isEmpty()) return;

        matrices.push();
        matrices.translate(0.5f, 1.025f, 0.5f);
        matrices.scale(0.65f, 0.65f, 0.65f);

        matrices.multiply(Axis.POSITIVE_X.rotation((float) Math.toRadians(90)));

        Direction direction = entity.getBlockState().get(ForgeBlock.FACING);

        switch (direction) {
            case NORTH -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(225)));
            case EAST -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(135)));
            case SOUTH -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(45)));
            case WEST -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(315)));
        }

        int currentLight = getLightLevel(entity.level(), entity.position(), direction);

        this.context.getItemRenderer().renderItem(stack, ItemDisplayContext.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.level(), 1);

        matrices.pop();
    }

    private int getLightLevel(Level world, BlockPos pos, Direction direction) {
        int bLight = world.getLightLevel(LightLayer.BLOCK, pos.offset(direction).above());
        int sLight = world.getLightLevel(LightLayer.SKY, pos.offset(direction).above());
        return LightTexture.pack(bLight, sLight);
    }
}
