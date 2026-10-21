//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.jukoz.me.entity.goose;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.math.RotationAxis;

@OnlyIn(Dist.CLIENT)
public class GooseHeldItemFeatureRenderer extends FeatureRenderer<GooseEntity, GooseModel<GooseEntity>> {
    private final HeldItemRenderer heldItemRenderer;

    public GooseHeldItemFeatureRenderer(FeatureRendererContext<GooseEntity, GooseModel<GooseEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(PoseStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, GooseEntity gooseEntity, float f, float g, float h, float j, float k, float l) {
        matrixStack.push();

        matrixStack.translate((this.getContextModel()).head.pivotX / 16.0F + (this.getContextModel()).neck.pivotX / 16.0F, (this.getContextModel()).neck.pivotY / 16.0F, (this.getContextModel()).head.pivotZ / 16.0F + (this.getContextModel()).neck.pivotZ / 16.0F);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(k));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(l));

        ItemStack itemStack = gooseEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        if(itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof PickaxeItem || itemStack.getItem() instanceof AxeItem || itemStack.getItem() instanceof ShovelItem || itemStack.getItem() instanceof HoeItem) {
            matrixStack.translate(-0.1F, -0.4F, -0.2F);

            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(135.0F));
        } else {
            matrixStack.translate(-0.0F, -0.4F, -0.5F);

            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        }
        if(gooseEntity.isAttacking()){

        }

        this.heldItemRenderer.renderItem(gooseEntity, itemStack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
