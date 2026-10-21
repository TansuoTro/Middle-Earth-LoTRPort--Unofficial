package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartDefinition;
import net.minecraft.client.model.geom.ModelPartData;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestBlock;
import net.jukoz.me.entity.EntityModelLayersME;

public class WillowCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart body;
    private final ModelPart lid;

    public WillowCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.WILLOW_COFFER);
        this.body = modelPart.getChild(BODY);
        this.lid = modelPart.getChild(LID);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BODY, PartDefinition.create()
                        .uv(0, 36).cuboid(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .uv(16, 36).cuboid(-1.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .uv(32, 36).cuboid(11.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .uv(0, 44).cuboid(11.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .uv(1, 0).cuboid(-1.0F, -13.0F, -1.0F, 16.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(-7.0F, 24.0F, -5.0F));

        modelPartData.addChild(LID, PartDefinition.create()
                        .uv(1, 21).cuboid(-8.0F, -3.0F, -12.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 11.0F, 6.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "willow_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        Level world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        WillowCofferBlock chest = (WillowCofferBlock)block;

        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation - 180));

        lid.pitch = g * -1.5707964f;
        lid.render(matrices, vertexConsumer, light, overlay);
        body.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
