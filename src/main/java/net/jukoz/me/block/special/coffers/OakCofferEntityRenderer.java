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
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.LayerDefinition;
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
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class OakCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart body;

    public OakCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.OAK_COFFER);
        this.lid = modelPart.getChild(LID);
        this.body = modelPart.getChild(BODY);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addOrReplaceChild(LID, PartDefinition.create(),
                PartPose.offset(0.0F, 13.0F, 6.0F));

        lid.addOrReplaceChild("lid_r1", PartDefinition.create()
                        .texOffs(0, 9).addBox(-12.0F, -3.0F, -16.0F, 12.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild(BODY, PartDefinition.create()
                        .texOffs(0, 56).addBox(-11.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 56).addBox(-11.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(39, 56).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(13, 56).addBox(-2.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32).addBox(-11.0F, -7.0F, -14.0F, 12.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.0F, 20.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        body.addOrReplaceChild("cube_r1", PartDefinition.create()
                        .texOffs(0, -2).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -5.0F, -14.0F, -1.5708F, 1.2654F, -1.5708F));

        body.addOrReplaceChild("cube_r2", PartDefinition.create()
                        .texOffs(0, -2).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-5.0F, -5.0F, 0.0F, 1.5708F, 1.2654F, 1.5708F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Material spriteIdentifier = new Material(Sheets.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "oak_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.level();
        BlockState blockState = world != null ? entity.getBlockState() : Blocks.CHEST.defaultBlockState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        OakCofferBlock chest = (OakCofferBlock)block;

        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> properties = chest.getBlockEntitySource(blockState, world, entity.position(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).toYRot();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(Axis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(rotation - 180));

        lid.xRot = g * -1.5707964f;
        lid.render(matrices, vertexConsumer, light, overlay);
        body.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
