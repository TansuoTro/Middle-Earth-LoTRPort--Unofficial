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

public class BeechCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart body;

    public BeechCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.BEECH_COFFER);
        this.body = modelPart.getChild(BODY);
        this.lid = modelPart.getChild(LID);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addChild(BODY, CubeListBuilder.create()
                        .uv(8, 50).cuboid(-12.0F, -1.0F, -3.0F, 12.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(6.0F, 19.0F, 1.0F));

        body.addChild("cube_r1", CubeListBuilder.create()
                        .uv(43, 48).cuboid(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.of(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        body.addChild("cube_r2", CubeListBuilder.create()
                        .uv(43, 48).mirrored().cuboid(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        modelPartData.addChild(LID, CubeListBuilder.create()
                        .uv(12, 35).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 18.0F, 5.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "beech_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        BeechCofferBlock chest = (BeechCofferBlock)block;

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
        body.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
