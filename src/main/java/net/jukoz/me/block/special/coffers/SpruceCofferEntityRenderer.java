package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.Sheets;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
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
        PartDefinition lid = modelPartData.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 45)
                .addBox(0.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F,
                new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 16.0F, 0.0F));

        PartDefinition lock_r1 = lid.addOrReplaceChild("lock_r1", CubeListBuilder.create().texOffs(0, 1)
                .addBox(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 38)
                        .addBox(5.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 38).addBox(-8.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 38).addBox(-8.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 38).addBox(5.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-5.0F, -3.0F, -8.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(20, 36).addBox(-5.0F, -3.0F, 8.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(20, 24).addBox(-8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "spruce_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.level();
        BlockState blockState = world != null ? entity.getBlockState() : Blocks.CHEST.defaultBlockState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        SpruceCofferBlock chest = (SpruceCofferBlock)block;

        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> properties;
        properties = chest.getBlockEntitySource(blockState, world, entity.position(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        Direction facing = blockState.get(ChestBlock.FACING);
        float rotation = facing.toYRot();
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
