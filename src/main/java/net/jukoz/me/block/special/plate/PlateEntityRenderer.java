package net.jukoz.me.block.special.plate;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import net.jukoz.me.item.MEComponents;
import net.jukoz.me.block.special.forge.ForgeBlock;
import com.mojang.math.Axis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class PlateEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {

    private final ItemRenderer itemRenderer;

    public PlateEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PlateBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStack();

        if(stack.isEmpty()) return;

        ResourceLocation oldId = MEComponents.get(stack, MEComponents.ITEM_MODEL);
        ResourceLocation modelId = PlateFoodModels.getPlateIdentifier(oldId);
        ItemStack copyStack = stack.copy();
        MEComponents.set(copyStack, MEComponents.ITEM_MODEL, modelId);

        boolean is3D = oldId != modelId;

        matrices.push();
        Direction direction = entity.getCachedState().get(ForgeBlock.FACING);

        long seed = Mth.hashCode(entity.getPos().getX(), 0, entity.getPos().getZ());
        double xOffset = ((seed & 15L) / 15.0 - 0.5) * 0.15;
        double zOffset = (((seed >> 4 & 15L) / 15.0) - 0.5) * 0.15;
        double rotOffset = (((seed >> 5 & 15L) / 15.0) - 0.5) * 35;

        if(!is3D) {
            matrices.translate(0.5f + xOffset, 0.085f, 0.5f + zOffset);
            matrices.scale(0.65f, 0.65f, 0.65f);
            matrices.multiply(Axis.POSITIVE_X.rotation((float) Math.toRadians(90)));
            switch (direction) {
                case NORTH -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(270)));
            }
            matrices.multiply(Axis.POSITIVE_Z.rotation((float) Math.toRadians(rotOffset)));
        } else {
            matrices.translate(0.5f + xOffset, 0.56f, 0.5f + zOffset);
            matrices.scale(1f, 1f, 1f);
            switch (direction) {
                case NORTH -> matrices.multiply(Axis.POSITIVE_Y.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.multiply(Axis.POSITIVE_Y.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.multiply(Axis.POSITIVE_Y.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.multiply(Axis.POSITIVE_Y.rotation((float) Math.toRadians(270)));
            }
            matrices.multiply(Axis.POSITIVE_Y.rotation((float) Math.toRadians(rotOffset)));
        }

        int currentLight = getLightLevel(entity.getWorld(), entity.getPos(), direction);

        this.itemRenderer.renderItem(copyStack, ItemDisplayContext.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }

    private int getLightLevel(Level world, BlockPos pos, Direction direction) {
        int bLight = world.getLightLevel(LightLayer.BLOCK, pos.offset(direction).up());
        int sLight = world.getLightLevel(LightLayer.SKY, pos.offset(direction).up());
        return LightTexture.pack(bLight, sLight);
    }
}
