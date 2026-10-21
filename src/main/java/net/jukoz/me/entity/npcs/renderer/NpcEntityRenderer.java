package net.jukoz.me.entity.npcs.renderer;

import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.MEBipedEntityRenderer;
import net.jukoz.me.item.MEComponents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.ModTexturedRenderLayers;
import net.jukoz.me.client.RenderUtil;
import net.jukoz.me.config.ModClientConfigs;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.npcs.data.NpcTextureData;
import net.jukoz.me.entity.npcs.renderer.features.ear.EarFeatureRenderer;
import net.jukoz.me.entity.npcs.renderer.features.feet.FeetFeatureRenderer;
import net.jukoz.me.entity.npcs.renderer.features.hair.HairFeatureRenderer;
import net.jukoz.me.entity.npcs.renderer.features.nose.NoseFeatureRenderer;
import net.jukoz.me.registries.AtlasesME;
import net.jukoz.me.registries.CharacterClothesRegistryME;
import net.jukoz.me.utils.ItemTagsME;
import org.jetbrains.annotations.Nullable;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Pose;
import net.minecraft.client.renderer.RenderType;

public class NpcEntityRenderer extends MEBipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final TextureAtlas characterTextureAtlas;

    public final static int HURT_COLOR = 0xff7e75;

    public final static int LIGHT_LEVEL_EMISSIVE_EYES = 8;
    public final static int BLINKING_INTERVAL = 80;
    public final static int BLINKING_DURATION = 3;

    public NpcEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new NpcEntityModel(context.getPart(EntityModelLayersME.NPC)), 0.7f);

        this.features.removeIf(x -> x.getClass() == ElytraLayer.class);
        this.features.removeIf(x -> x.getClass() == CustomHeadLayer.class);

        this.addFeature(new HumanoidArmorLayer<NpcEntity, NpcEntityModel, HumanoidArmorModel<NpcEntity>>(this, new HumanoidArmorModel<>(context.getPart(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel<>(context.getPart(ModelLayers.PLAYER_OUTER_ARMOR)), net.minecraft.client.Minecraft.getInstance().getModelManager()));
        this.addFeature(new HairFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet())));
        this.addFeature(new EarFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet())));
        this.addFeature(new NoseFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet())));
        this.addFeature(new FeetFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet())));
        this.addFeature(new net.jukoz.me.entity.spider.EnwebbedFeatureRenderer<NpcEntity, NpcEntityModel>(this, new LoadedEntityModelsCompat(context.getModelSet()), net.jukoz.me.compat.client.render.equipment.EquipmentRenderer.INSTANCE));

        characterTextureAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);

        this.shadowRadius = 0.5f;
    }

    // region [RenderState]
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    public static float getLOD(Vec3 entityPos) {
        Minecraft client = Minecraft.getInstance();
        Camera camera = client.gameRenderer.getCamera();
        return (float)camera.getPos().distanceTo(entityPos);
        // Keep in case it's needed
        /*int currentFov = client.options.getFov().getValue();
        double fovRatio = currentFov / 90.0;
        return (float) (distance * fovRatio);*/
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void updateRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float tickDelta) {
        if(!npcEntity.getWorld().isClientSide)
            return;

        npcEntityRenderState.aimingState = npcEntity.aimingState;

        super.updateRenderState(npcEntity, npcEntityRenderState, tickDelta);
        NpcTextureData npcTextureData = npcEntity.retrieveNpcTextureData();
        float currentLightLevel = npcEntity.getWorld().getLightLevel(npcEntity.getBlockPos());

        npcEntityRenderState.pose = npcEntity.getPose();

        npcEntityRenderState.leftArmPose = getArmPose(npcEntity, npcEntity.getItemInHand(InteractionHand.OFF_HAND), InteractionHand.OFF_HAND);
        npcEntityRenderState.rightArmPose = getArmPose(npcEntity, npcEntity.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);

        npcEntityRenderState.widthScale = npcEntity.getWidthScale();

        npcEntityRenderState.simplifiedSkinId = npcTextureData.getSimplifiedSkin();
        npcEntityRenderState.simplifiedEarId = npcTextureData.getSimplifiedEar();
        npcEntityRenderState.simplifiedFeetId = npcTextureData.getSimplifiedFeet();
        npcEntityRenderState.simplifiedHairAddonId = npcTextureData.getSimplifiedHair();
        npcEntityRenderState.simplifiedNoseId = npcTextureData.getSimplifiedNose();

        if(!ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING || npcEntityRenderState.simplifiedSkinId == null){
            npcEntityRenderState.skinId = npcTextureData.get(NpcRenderedPart.BODY);
            npcEntityRenderState.feetId = npcTextureData.get(NpcRenderedPart.FEET);
            npcEntityRenderState.headId = npcTextureData.get(NpcRenderedPart.HEAD);
            npcEntityRenderState.earId = npcTextureData.get(NpcRenderedPart.EAR);
            npcEntityRenderState.noseId = npcTextureData.get(NpcRenderedPart.NOSE);
            npcEntityRenderState.eyesId = npcTextureData.get(NpcRenderedPart.EYE);
            npcEntityRenderState.eyesEmissiveId = npcTextureData.get(NpcRenderedPart.EYE_EMISSIVE);
            npcEntityRenderState.haveEmissiveEyes = npcTextureData.isEyeEmissive() && currentLightLevel <= LIGHT_LEVEL_EMISSIVE_EYES;
            npcEntityRenderState.eyebrowId = npcTextureData.get(NpcRenderedPart.EYEBROW);
            npcEntityRenderState.scarId = npcTextureData.get(NpcRenderedPart.SCAR);
            npcEntityRenderState.beardId = npcTextureData.get(NpcRenderedPart.BEARD);
            npcEntityRenderState.beardAddonId = npcTextureData.get(NpcRenderedPart.BEARD_ADDON);
            npcEntityRenderState.hairId = npcTextureData.get(NpcRenderedPart.HAIR);
            npcEntityRenderState.hairAddonId = npcTextureData.get(NpcRenderedPart.HAIR_ADDON);

            npcEntityRenderState.clothingBase = npcTextureData.get(NpcRenderedPart.CLOTHING_BASE);
            npcEntityRenderState.clothingOver = npcTextureData.get(NpcRenderedPart.CLOTHING_OVER);
            npcEntityRenderState.clothingExtra = npcTextureData.get(NpcRenderedPart.CLOTHING_EXTRA);

            long age = npcEntity.age;
            npcEntityRenderState.blinking = (0 + age) % BLINKING_INTERVAL >= BLINKING_INTERVAL - BLINKING_DURATION;
        }
        ItemStack currentHelmet = npcEntity.getEquippedStack(EquipmentSlot.HEAD);
        if(currentHelmet == null || currentHelmet.isEmpty()){
            npcEntityRenderState.canShowBeard = true;
            npcEntityRenderState.canShowHair = true;
            npcEntityRenderState.canShowEars = true;
        } else {
            var hasAttachment = MEComponents.get(currentHelmet, MEComponents.HELMET_ATTACHMENT_DATA);
            boolean hasHoodDown = hasAttachment == null || hasAttachment.below();

            npcEntityRenderState.canShowEars = currentHelmet.is(ItemTagsME.CHARACTER_HELMET_SHOW_EARS) && hasHoodDown;
            npcEntityRenderState.canShowBeard = !currentHelmet.is(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
            npcEntityRenderState.canShowHair = !currentHelmet.is(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR) && hasHoodDown;
        }
        ItemStack currentShoes = npcEntity.getEquippedStack(EquipmentSlot.FEET);
        npcEntityRenderState.canShowFeet = currentShoes == null || currentShoes.isEmpty();
        npcEntityRenderState.LOD = getLOD(npcEntity.getPos());
    }
    // endregion

    // region [Layered Texture Renderer]
    @Nullable
    protected RenderType getRenderLayer(NpcEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        ResourceLocation identifier = this.getTexture(state);
        if (translucent) {
            return RenderType.getEntityTranslucent(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderType.getOutline(identifier) : null;
        }
    }

    @Override
    public void render(NpcEntityRenderState state, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;

        if(!simplified && (state.skinId == null || state.headId == null || state.eyesId == null))
            return;

        matrices.push();
        if (state.isInPose(Pose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            if (direction != null) {
                float f = state.standingEyeHeight - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * f, 0.0F, (float)(-direction.getOffsetZ()) * f);
            }
        }
        else if (state.hasVehicle) {
            matrices.translate(0, -0.5F, 0);
        }

        float g = state.baseScale;
        float widthScale = state.widthScale;

        matrices.scale(g * widthScale, g, g * widthScale);
        this.setupTransforms(state, matrices, state.bodyYaw, g);
        matrices.scale(-1.0f, -1.0f, 1.0f);
        this.scale(state, matrices);
        matrices.translate(0.0f, -1.501f, 0.0f);

        this.model.setAngles(state);
        int overlay = state.hurt ? getOverlay(state, this.getAnimationCounter(state)) : OverlayTexture.DEFAULT_UV;

        if(simplified){
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());
            renderTexture(matrices, vertexConsumer, state.simplifiedSkinId, light, overlay, false);
        } else {
            renderComplexVersion(matrices, vertexConsumers, light, overlay, state);
        }

        if (!state.invisible && state.LOD < ModClientConfigs.LOD_NPC_ARMOR_DISTANCE) {
            NpcEntity featureEntity = (NpcEntity) state.entity;
            for (RenderType<NpcEntity, NpcEntityModel> feature : this.features) {
                if (feature instanceof EarFeatureRenderer)
                    if ((state.simplifiedEarId == null && state.earId == null) || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if (feature instanceof NoseFeatureRenderer)
                    if ((state.simplifiedNoseId == null && state.noseId == null) || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if (feature instanceof HairFeatureRenderer)
                    if ((state.simplifiedHairAddonId == null && state.hairAddonId == null && state.beardAddonId == null)
                            || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if(feature instanceof FeetFeatureRenderer)
                    if((state.simplifiedFeetId == null && state.feetId == null) || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                feature.render(matrices, vertexConsumers, light, featureEntity, state.limbSwingAnimationProgress,
                        state.limbSwingAmplitude, state.tickDelta, state.age, state.relativeHeadYaw, state.relativeHeadPitch);
            }
        }

        matrices.pop();

        if (state.customName != null) {
            this.renderLabelIfPresent(state, state.customName, matrices, vertexConsumers, light);
        }
    }

    private void renderComplexVersion(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, NpcEntityRenderState state) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());

        // Will always be shown
        renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.skinId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.headId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        if(!state.blinking){
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.eyesId, AtlasesME.EYE_PREFIX), light, overlay, false);
        }
        // Optionally shown, only if the value is present
        if(state.eyebrowId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.eyebrowId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(state.scarId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.scarId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        if(state.beardId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.beardId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(state.clothingBase == null && state.clothingOver == null && state.clothingExtra == null){
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(CharacterClothesRegistryME.Base.THONG_BROWN, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay, false);
        }
        else {
            if(state.clothingBase != null)
                renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.clothingBase, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay, false);

            if(state.clothingOver != null)
                renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.clothingOver, AtlasesME.CLOTHES_OVER_PREFIX), light, overlay, false);

            if(state.clothingExtra != null)
                renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.clothingExtra, AtlasesME.CLOTHES_EXTRA_PREFIX), light, overlay, false);
        }

        if(state.hairId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.hairId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(!state.blinking && state.haveEmissiveEyes){
            VertexConsumer vertexConsumerEmissive = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesEmissiveRenderLayer());
            renderTexture(matrices, vertexConsumerEmissive, MiddleEarth.ofPrefix(state.eyesEmissiveId, AtlasesME.EYE_PREFIX), light, overlay, true);
        }
    }

    private void renderTexture(PoseStack matrices, VertexConsumer vertexConsumer, ResourceLocation textureId, int light, int overlay, boolean isEmissive){
        RenderUtil.renderAtlasTexture(characterTextureAtlas, model, matrices, vertexConsumer, textureId, light, overlay);
    }

    @Override
    public ResourceLocation getTexture(NpcEntityRenderState state) {
        // Made custom in the render method
        return null;
    }

    private HumanoidModel.ArmPose getArmPose(NpcEntity npc, ItemStack stack, InteractionHand hand) {
        if(npc.isAiming()){
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }

        if (stack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        }
        if (!npc.handSwinging && (stack.is(Items.CROSSBOW) || stack.is(ItemTagsME.CROSSBOW))) {
            if(CrossbowItem.isCharged(stack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
            else if(npc.isCharging()) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
        }
        if (npc.getUsedItemHand() == hand && npc.getUseItemRemainingTicks() > 0) {
            UseAnim useAction = stack.getUseAction();
            if (useAction == UseAnim.BLOCK || npc.isBlocking()) {
                return HumanoidModel.ArmPose.BLOCK;
            }
            if (useAction == UseAnim.BOW) {
                return HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
            if (useAction == UseAnim.SPEAR) {
                return HumanoidModel.ArmPose.THROW_SPEAR;
            }
            if (useAction == UseAnim.CROSSBOW) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
            if (useAction == UseAnim.SPYGLASS) {
                return HumanoidModel.ArmPose.SPYGLASS;
            }
            if (useAction == UseAnim.TOOT_HORN) {
                return HumanoidModel.ArmPose.TOOT_HORN;
            }
            if (useAction == UseAnim.BRUSH) {
                return HumanoidModel.ArmPose.BRUSH;
            }
        }
        return HumanoidModel.ArmPose.ITEM;
    }
    // endregion
}
