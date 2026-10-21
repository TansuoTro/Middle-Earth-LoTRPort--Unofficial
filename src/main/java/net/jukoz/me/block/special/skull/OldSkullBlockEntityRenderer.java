package net.jukoz.me.block.special.skull;

import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import org.jetbrains.annotations.Nullable;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class OldSkullBlockEntityRenderer implements BlockEntityRenderer<OldSkullBlockEntity> {
    private final ModelPart skull;

    public OldSkullBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.OLD_SKULL);
        this.skull = modelPart.getChild("skull");
    }

    @Nullable
    public static SkullModelBase getModels(LoadedEntityModelsCompat models) {
        return new SkullModel(models.getModelPart(ModelLayers.SKELETON_SKULL));
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition skull = modelPartData.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-12.0F, -8.0F, 4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));
        return LayerDefinition.of(modelData, 64, 64);
    }

    public void render(OldSkullBlockEntity skullBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j) {
        BlockState blockState = skullBlockEntity.getBlockState();
        boolean bl = blockState.getBlock() instanceof WallSkullBlock;
        Direction direction = bl ? blockState.get(WallSkullBlock.FACING) : null;
        int k = bl ? RotationSegment.fromDirection(direction.getOpposite()) : (Integer)blockState.get(SkullBlock.ROTATION);
        float h = RotationSegment.toDegrees(k);

        VertexConsumer vertexConsumer = getSpriteIdentifier().getVertexConsumer(vertexConsumerProvider, RenderType::getEntityCutout);

        RenderType renderLayer = getRenderLayer();
        renderSkull(direction, h, 0, matrixStack, vertexConsumer, i, skull, renderLayer);
    }

    public static void renderSkull(@Nullable Direction direction, float yaw, float animationProgress, PoseStack matrices,
                                   VertexConsumer vertexConsumers, int light, ModelPart model, RenderType renderLayer) {
        matrices.push();
        if (direction == null) {
            matrices.translate(0.5F, 0.0F, 0.5F);
        } else {
            float f = 0.25F;
            matrices.translate(0.5F - direction.getOffsetX() * 0.25F, 0.25F, 0.5F - direction.getOffsetZ() * 0.25F);
        }

        matrices.translate(0, 1.5f, 0);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(yaw));
        model.render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    public static RenderType getRenderLayer() {
        return RenderType.getEntityCutoutNoCullZOffset(new ResourceLocation(MiddleEarth.MOD_ID, "model/old_skull"));
    }

    private Material getSpriteIdentifier(){
        return new Material(TextureAtlas.BLOCK_ATLAS_TEXTURE,
                new ResourceLocation(MiddleEarth.MOD_ID, "model/old_skull"));
    }
}
