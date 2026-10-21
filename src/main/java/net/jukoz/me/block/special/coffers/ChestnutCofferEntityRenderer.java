package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartDefinition;
import net.minecraft.client.model.geom.ModelPartData;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.LayerDefinition;
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
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ChestnutCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart body;
    private final ModelPart lid;

    public ChestnutCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.CHESTNUT_COFFER);
        this.body = modelPart.getChild(BODY);
        this.lid = modelPart.getChild(LID);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addChild(BODY, PartDefinition.create()
                        .uv(2, 34).cuboid(-16.0F, 6.1F, -5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .uv(19, 34).cuboid(-4.0F, 6.1F, -5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .uv(35, 34).cuboid(-4.0F, 6.1F, 5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .uv(2, 39).cuboid(-16.0F, 6.1F, 5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .uv(0, 13).cuboid(-16.0F, -1.9F, -5.0F, 16.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(8.0F, 15.9F, 0.0F));

        body.addChild("cube_r1", PartDefinition.create()
                        .uv(1, -1).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.of(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        body.addChild("cube_r2", PartDefinition.create()
                        .uv(1, -1).mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        modelPartData.addChild(LID, PartDefinition.create()
                        .uv(0, 49).cuboid(-8.0F, -2.0F, -12.0F, 16.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 14.0F, 6.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "chestnut_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        ChestnutCofferBlock chest = (ChestnutCofferBlock)block;

        DoubleBlockCombiner.PropertySource<? extends ChestBlockEntity> properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
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
