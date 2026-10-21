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

public class LarchCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BASE = "bottom";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart bottom;

    public LarchCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.LARCH_COFFER);
        this.bottom = modelPart.getChild(BASE);
        this.lid = modelPart.getChild(LID);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addChild(LID, CubeListBuilder.create().uv(0, 21).cuboid(-8.0F, -2.0F, -10.0F, 16.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(49, 57).cuboid(-1.0F, -2.0F, -11.0F, 2.0F, 3.0F, 1.0F,
                        new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 14.0F, 5.0F));

        PartDefinition bottom = modelPartData.addChild(BASE, CubeListBuilder.create().uv(0, 58).cuboid(-7.0F, -2.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(12, 58).cuboid(-7.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(24, 58).cuboid(4.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(36, 58).cuboid(4.0F, -2.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-7.0F, -10.0F, -5.0F, 14.0F, 8.0F, 10.0F,
                        new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 24.0F, 0.0F));

        PartDefinition handle_r1 = bottom.addChild("handle_r1", CubeListBuilder.create().uv(0, 0)
                .mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .mirrored(false), PartPose.of(7.0F, -6.1F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition handle_r2 = bottom.addChild("handle_r2", CubeListBuilder.create().uv(0, 0)
                .cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.of(-7.0F, -6.1F, 0.0F, 0.0F, 0.0F, 0.3927F));
        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "larch_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        LarchCofferBlock chest = (LarchCofferBlock)block;

        DoubleBlockCombiner.PropertySource<? extends ChestBlockEntity> properties;
        properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(Axis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(rotation - 180));

        lid.pitch = g * -1.5707964f;
        lid.render(matrices, vertexConsumer, light, overlay);
        bottom.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
