package net.jukoz.me.entity.barrel;

import net.jukoz.me.compat.client.render.MEEntityRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.jukoz.me.compat.client.render.state.BoatEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import org.joml.Quaternionf;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

public class BarrelEntityRenderer extends MEEntityRenderer<BarrelEntity, BoatEntityRenderState> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/reinforced_barrel/reinforced_barrel.png");
    private ModelPart modelPart;

    public BarrelEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        modelPart = context.getPart(EntityModelLayersME.REINFORCED_BARREL);
        this.shadowRadius = 0.6F;
    }

    @Override
    public BoatEntityRenderState createRenderState() {
        return new BoatEntityRenderState();
    }

    @Override
    public void render(BoatEntityRenderState state, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.push();
        matrices.scale(1.35f, 1.35f, 1.35f);
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(180.0F - state.yRot));
        matrices.multiply(Axis.POSITIVE_Z.rotationDegrees(180.0F));

        float f = state.damageWobbleTicks;
        if (f > 0.0F) {
            matrices.multiply(Axis.POSITIVE_X.rotationDegrees(Mth.sin(f) * f * state.damageWobbleStrength / 10.0F * (float)state.damageWobbleSide));
        }

        if (!state.submergedInWater && !Mth.approximatelyEquals(state.bubbleWobble, 0.0F)) {
            matrices.multiply((new Quaternionf()).setAngleAxis(state.bubbleWobble * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        matrices.translate(0f, -1.4f, 0f);
        modelPart.render(matrices, vertexConsumers.getBuffer(RenderType.cutoutBlockSheet(TEXTURE)), light, OverlayTexture.DEFAULT_UV);
        this.renderWaterMask(state, matrices, vertexConsumers, light);
        matrices.pop();
    }

    public void updateRenderState(BarrelEntity barrelEntity, BoatEntityRenderState boatEntityRenderState, float f) {
        super.updateRenderState(barrelEntity, boatEntityRenderState, f);
        boatEntityRenderState.yRot = Mth.lerpAngleDegrees(f, barrelEntity.prevYaw, barrelEntity.getYaw());
        boatEntityRenderState.damageWobbleTicks = (float)barrelEntity.getHurtTime() - f;
        boatEntityRenderState.damageWobbleSide = barrelEntity.getHurtDir();
        boatEntityRenderState.damageWobbleStrength = Math.max(barrelEntity.getDamage() - f, 0.0F);
        boatEntityRenderState.bubbleWobble = barrelEntity.interpolateBubbleWobble(f);
        boatEntityRenderState.submergedInWater = barrelEntity.isUnderWater();
        boatEntityRenderState.leftPaddleAngle = barrelEntity.interpolatePaddlePhase(0, f);
        boatEntityRenderState.rightPaddleAngle = barrelEntity.interpolatePaddlePhase(1, f);
    }

    protected void renderWaterMask(BoatEntityRenderState state, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (!state.submergedInWater) {
            this.modelPart.render(matrices, vertexConsumers.getBuffer(RenderType.waterMask()), light, OverlayTexture.DEFAULT_UV);
        }

    }
}
