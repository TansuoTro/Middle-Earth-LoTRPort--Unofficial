package net.jukoz.me.block.special.reinforcedChest;

import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
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

public class ReinforcedChestEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private final ModelPart chestLid;
    private final ModelPart chestBase;
    private final ModelPart chestLatch;

    public ReinforcedChestEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.REINFORCED_CHEST);
        this.chestBase = modelPart.getChild(BASE);
        this.chestLid = modelPart.getChild(LID);
        this.chestLatch = modelPart.getChild(LATCH);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(BASE, CubeListBuilder.create().uv(0, 18).cuboid(-8.0F, 0.0F, -6.0F, 16.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(LID, CubeListBuilder.create().uv(0, 1).cuboid(-2.0F, -3.0F, -13.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 10.0F, 6.0F));
        modelPartData.addChild(LATCH, CubeListBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -12.0F, 16.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 10.0F, 6.0F));
        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE,
                new ResourceLocation(MiddleEarth.MOD_ID, "model/reinforced_chest"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        ReinforcedChestBlock chest = (ReinforcedChestBlock)block;

        DoubleBlockCombiner.PropertySource<? extends ChestBlockEntity> properties;
        properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 0.0D, 0.5D);
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(-rotation));

        chestLatch.pitch = chestLid.pitch = +(g * 1.5707964f);
        chestLid.render(matrices, vertexConsumer, light, overlay);
        chestLatch.render(matrices, vertexConsumer, light, overlay);
        chestBase.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
