package net.jukoz.me.entity.projectile.spear;
import net.jukoz.me.compat.client.render.MEEntityRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.math.RotationAxis;

@OnlyIn(Dist.CLIENT)
public class SpearEntityRenderer extends MEEntityRenderer<SpearEntity, SpearEntityRenderState> {
    private static final float MIN_DISTANCE = 12.25F;
    private static final float SCALE = 1.0F;
    private final EquipmentRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public SpearEntityRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = EquipmentRenderer.INSTANCE;
        this.scale = SCALE * scale;
        this.lit = lit;
    }

    public SpearEntityRenderer(EntityRendererFactory.Context context) {
        this(context, 1.0F, false);
    }

    @Override
    public SpearEntityRenderState createRenderState() {
        return new SpearEntityRenderState();
    }

    @Override
    protected int getBlockLight(SpearEntity entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);

    }

    //TODO fix all this
    @Override
    public void render(SpearEntityRenderState state, PoseStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        /*
        matrices.push();
        float cosYaw = (float) Math.cos(Math.toRadians(state.getYaw()));
        float sinYaw = (float) Math.sin(Math.toRadians(state.getYaw()));
        matrices.translate(sinYaw * -1.3D, 1.55D * (state.getPitch() / -90), cosYaw * -1.3D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(Mth.lerp(tickDelta, state.prevYaw, state.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(Mth.lerp(tickDelta, state.prevPitch, state.getPitch())));

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));


        matrices.scale(scale, scale, scale);

        ItemStack itemStack = state.getTrackedItemStackData();
        if(itemStack == null) state.getDefaultItemStack();
        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();

        */
        super.render(state, matrices, vertexConsumers, light);
    }
}
