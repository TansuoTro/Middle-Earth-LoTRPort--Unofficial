package net.jukoz.me.entity.projectile.smoke;

import net.jukoz.me.compat.client.render.MEEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.jukoz.me.MiddleEarth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

@OnlyIn(Dist.CLIENT)
public class SmokeRingProjectileRenderer extends MEEntityRenderer<SmokeRingProjectileEntity, SmokeRingProjectileRenderState> {
    private final TextureAtlasSprite[] frames;

    private static final ResourceLocation SPRITES_ATLAS_ID = new ResourceLocation(MiddleEarth.MOD_ID, "sprites");
    private static final String SPRITE_PATH_PREFIX = "sprites/smoke_ring/big_smoke_ring_";
    private static final int FRAME_COUNT = 12;
    private static final int FAILED_FIRST_FRAME = 7;
    private static final int FAILED_FRAME_COUNT = 5;
    private static final float SMOKE_RING_SIZE = 1.0f;

    public SmokeRingProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        frames = loadFrames();
    }

    @Override
    public void render(
            SmokeRingProjectileRenderState state,
            PoseStack matrices,
            MultiBufferSource vertexConsumers,
            int light) {
        matrices.push();
        matrices.translate(0, 0.2, 0);
        matrices.multiply(state.orientationQuat);

        int firstFrame = state.failed ? FAILED_FIRST_FRAME : 0;
        int frameCount = state.failed ? FAILED_FRAME_COUNT : frames.length;
        int frame = firstFrame + Math.min((int) (state.age / state.maxLifespan * frameCount), frameCount - 1);
        TextureAtlasSprite sprite = frames[frame];

        Matrix4f matrix = matrices.peek().pose();
        VertexConsumer vc = vertexConsumers.getBuffer(RenderType.entityTranslucent(sprite.atlasLocation()));

        float minU = sprite.getU0();
        float maxU = sprite.getMaxU();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        int overlay = net.minecraft.client.renderer.texture.OverlayTexture.DEFAULT_UV;

        drawQuad(vc, matrix, SMOKE_RING_SIZE, minU, maxU, minV, maxV, light, overlay);

        matrices.pop();
    }

    @Override
    public SmokeRingProjectileRenderState createRenderState() {
        SmokeRingProjectileRenderState state = new SmokeRingProjectileRenderState();
        state.maxLifespan = SmokeRingProjectileEntity.MAX_LIFESPAN_TICKS;
        state.failed = false;

        return state;
    }

    @Override
    public void updateRenderState(
            SmokeRingProjectileEntity entity,
            SmokeRingProjectileRenderState state,
            float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);

        state.maxLifespan = entity.getMaxLifespanTicks();
        state.failed = entity.isFailed();
        this.updateOrientationQuaternion(entity, state, tickDelta);
    }

    private TextureAtlasSprite[] loadFrames() {
        TextureAtlas atlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(
                SPRITES_ATLAS_ID);

        TextureAtlasSprite[] sprites = new TextureAtlasSprite[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            ResourceLocation spriteId = new ResourceLocation(MiddleEarth.MOD_ID, SPRITE_PATH_PREFIX + i);
            sprites[i] = atlas.getSprite(spriteId);
        }
        return sprites;
    }

    private void drawQuad(
            VertexConsumer vc,
            Matrix4f matrix,
            float size,
            float minU,
            float maxU,
            float minV,
            float maxV,
            int light,
            int overlay) {
        float half = size / 2f;
        vc.vertex(matrix, -half, -half, 0).color(255, 255, 255, 255).texture(minU, minV).overlay(
                overlay).light(light).normal(0, 0, 1);
        vc.vertex(matrix, -half, +half, 0).color(255, 255, 255, 255).texture(minU, maxV).overlay(
                overlay).light(light).normal(0, 0, 1);
        vc.vertex(matrix, +half, +half, 0).color(255, 255, 255, 255).texture(maxU, maxV).overlay(
                overlay).light(light).normal(0, 0, 1);
        vc.vertex(matrix, +half, -half, 0).color(255, 255, 255, 255).texture(maxU, minV).overlay(
                overlay).light(light).normal(0, 0, 1);
    }

    private void updateOrientationQuaternion(
            SmokeRingProjectileEntity entity,
            SmokeRingProjectileRenderState state,
            float tickDelta) {
        float yawRad = (float) Math.toRadians(-Mth.lerp(tickDelta,
                entity.prevYaw,
                entity.getYaw()));
        float pitchRad = (float) Math.toRadians(Mth.lerp(tickDelta,
                entity.prevPitch,
                entity.getPitch()));
        state.orientationQuat = new Quaternionf().rotateYXZ(yawRad, pitchRad, 0f);
    }
}
