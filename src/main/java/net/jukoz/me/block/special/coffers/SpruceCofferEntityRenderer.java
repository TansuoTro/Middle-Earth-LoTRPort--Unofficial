package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.Sheets;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestBlock;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SpruceCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BASE = "bottom";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart bottom;

    public SpruceCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.SPRUCE_COFFER);
        this.bottom = modelPart.getChild(BASE);
        this.lid = modelPart.getChild(LID);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addChild("lid", CubeListBuilder.create().uv(0, 45)
                .cuboid(0.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F,
                new CubeDeformation(0.0F)), PartPose.pivot(-8.0F, 16.0F, 0.0F));

        PartDefinition lock_r1 = lid.addChild("lock_r1", CubeListBuilder.create().uv(0, 1)
                .cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.of(16.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition body = modelPartData.addChild("bottom", CubeListBuilder.create().uv(0, 38)
                        .cuboid(5.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(12, 38).cuboid(-8.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(24, 38).cuboid(-8.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(36, 38).cuboid(5.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(0, 36).cuboid(-5.0F, -3.0F, -8.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .uv(20, 36).cuboid(-5.0F, -3.0F, 8.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .uv(0, 24).cuboid(8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(20, 24).cuboid(-8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(0, 3).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "spruce_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        SpruceCofferBlock chest = (SpruceCofferBlock)block;

        DoubleBlockCombiner.PropertySource<? extends ChestBlockEntity> properties;
        properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        Direction facing = blockState.get(ChestBlock.FACING);
        float rotation = facing.asRotation();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(Axis.POSITIVE_X.rotationDegrees(180));
        if(facing == Direction.NORTH || facing == Direction.SOUTH) {
            matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(-rotation - 90));
        } else {
            matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(-rotation + 90));
        }

        lid.roll = g * -1.5707964f;
        lid.render(matrices, vertexConsumer, light, overlay);
        bottom.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
