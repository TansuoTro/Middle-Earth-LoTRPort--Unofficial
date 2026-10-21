package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.chest.CloakCapeModel;
import net.jukoz.me.client.model.equipment.chest.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.client.model.equipment.head.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.InnerArmorModel;
import net.jukoz.me.utils.IntToRGB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModelLayers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import java.awt.*;
import net.minecraft.client.model.geom.ModelLayers;

public class ModArmorRenderer implements ArmorRenderer {
    private  ResourceLocation ARMOR_LAYER_0;
    private  ResourceLocation ARMOR_LAYER_1;
    private  ResourceLocation ARMOR_LAYER_2;
    private  ResourceLocation ARMOR_LAYER_CAPE;
    private  ResourceLocation ARMOR_LAYER_HOOD;

    private  CustomHelmetModel<LivingEntity> helmetModel;
    private  CustomChestplateModel<LivingEntity> chestplateModel;
    private static InnerArmorModel<LivingEntity> innerArmorModel;
    private static HumanoidArmorModel<LivingEntity> armorModel;
    private static HumanoidArmorModel<LivingEntity> legArmorModel;
    private static CloakCapeModel<LivingEntity> capeModel;
    private static CloakHoodModel<LivingEntity> hoodModel;

    private final boolean hasInnerLayer;
    private final boolean hasVanillaArmorModel;
    private final boolean hasCape;
    private final boolean hasHood;
    private final boolean dyeable;

    public ModArmorRenderer(CustomHelmetModel<LivingEntity> customHelmetModel, CustomChestplateModel<LivingEntity> customChestplateModel, String armorName,
                            boolean hasInnerLayer, boolean hasVanillaArmorModel, boolean hasCape, boolean hasHood, boolean dyeable) {
        helmetModel = customHelmetModel;
        chestplateModel = customChestplateModel;
        ARMOR_LAYER_0 = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_0.png");
        ARMOR_LAYER_1 = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_1.png");
        ARMOR_LAYER_2 = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_2.png");
        ARMOR_LAYER_CAPE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_cape.png");
        ARMOR_LAYER_HOOD = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_hood.png");

        this.hasInnerLayer = hasInnerLayer;
        this.hasVanillaArmorModel = hasVanillaArmorModel;
        this.hasCape = hasCape;
        this.hasHood = hasHood;
        this.dyeable = dyeable;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (innerArmorModel == null) {
            innerArmorModel = new InnerArmorModel<>(Minecraft.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.INNER_ARMOR_MODEL_LAYER));
            armorModel = new HumanoidArmorModel<>(Minecraft.getInstance().getEntityModelLoader().getModelPart(ModelLayers.PLAYER_OUTER_ARMOR));
            legArmorModel = new HumanoidArmorModel<>(Minecraft.getInstance().getEntityModelLoader().getModelPart(ModelLayers.PLAYER_INNER_ARMOR));
            capeModel = new CloakCapeModel<>(Minecraft.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));
            hoodModel = new CloakHoodModel<>(Minecraft.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));
        }

        if (slot == EquipmentSlot.HEAD) {
            if(hasVanillaArmorModel){
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.head.visible = true;
                armorModel.hat.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1, this.dyeable);
            }
            if (helmetModel != null) {
                contextModel.copyBipedStateTo(helmetModel);
                helmetModel.setVisible(false);
                helmetModel.head.visible = true;
                helmetModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(),(float)entity.age + Minecraft.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.pitch);
                renderArmor(matrices, vertexConsumers, light, stack, helmetModel, helmetModel.HELMET_ADDON_TEXTURE, this.dyeable);
            }
            if(hasHood){
                contextModel.copyBipedStateTo(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, hoodModel, ARMOR_LAYER_HOOD, this.dyeable);
            }
        } else if (slot == EquipmentSlot.CHEST) {
            if(hasInnerLayer) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                innerArmorModel.body.visible = true;
                innerArmorModel.rightArm.visible = true;
                innerArmorModel.leftArm.visible = true;
                innerArmorModel.rightLeg.visible = true;
                innerArmorModel.leftLeg.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0, this.dyeable);
            }

            if(hasVanillaArmorModel) {
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.body.visible = true;
                armorModel.rightArm.visible = true;
                armorModel.leftArm.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1, this.dyeable);
            }

            if(this.chestplateModel != null) {
                contextModel.copyBipedStateTo(chestplateModel);
                chestplateModel.setVisible(false);
                chestplateModel.body.visible = true;
                chestplateModel.rightArm.visible = true;
                chestplateModel.leftArm.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, chestplateModel, chestplateModel.CHESTPLATE_ADDON_TEXTURE, this.dyeable);
            }

            if (this.hasCape) {
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.setAngles(entity, entity.limbAnimator.getPos(),entity.limbAnimator.getSpeed(),(float)entity.age + Minecraft.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.roll);
                renderArmor(matrices, vertexConsumers, light, stack, capeModel, ARMOR_LAYER_CAPE, this.dyeable);
            }
        } else if (slot == EquipmentSlot.LEGS) {
            if(hasInnerLayer) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                renderArmor(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0, this.dyeable);
            }
            contextModel.copyBipedStateTo(legArmorModel);
            legArmorModel.setVisible(false);
            legArmorModel.body.visible = true;
            legArmorModel.rightLeg.visible = true;
            legArmorModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, legArmorModel, ARMOR_LAYER_2, this.dyeable);
        } else if (slot == EquipmentSlot.FEET) {
            contextModel.copyBipedStateTo(armorModel);
            armorModel.setVisible(false);
            armorModel.rightLeg.visible = true;
            armorModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1, this.dyeable);
        }
    }

    static void renderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack stack, Model model, ResourceLocation texture, boolean dyeable){
        if(dyeable){
            renderDyeable(matrices, vertexConsumers, light, stack, model, texture);
            if(!(model == capeModel)){
                renderDyeable(matrices, vertexConsumers, light, stack, model, new ResourceLocation(MiddleEarth.MOD_ID,
                        texture.getPath().replaceAll(".png", "_overlay.png")));
            }
        } else {
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }
    }

    static void renderDyeable(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack stack, Model model, ResourceLocation texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
        Color rgb = IntToRGB.ex(((DyeableLeatherItem)stack.getItem()).getColor(stack));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, rgb.getRed()/255f, rgb.getGreen()/255f, rgb.getBlue()/255f, 1.0F);
    }
}
