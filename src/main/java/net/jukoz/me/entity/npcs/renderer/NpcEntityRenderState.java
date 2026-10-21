package net.jukoz.me.entity.npcs.renderer;

import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class NpcEntityRenderState extends BipedEntityRenderState {
    public float widthScale;

    public AnimationState aimingState = new AnimationState();

    public ResourceLocation simplifiedSkinId;
    public ResourceLocation simplifiedEarId;
    public ResourceLocation simplifiedNoseId;
    public ResourceLocation simplifiedHairAddonId;
    public ResourceLocation simplifiedFeetId;

    public ResourceLocation skinId;
    public ResourceLocation feetId;
    public ResourceLocation headId;
    public ResourceLocation earId;
    public ResourceLocation noseId;
    public ResourceLocation eyesId;
    public ResourceLocation eyesEmissiveId;
    public ResourceLocation eyebrowId;
    public ResourceLocation scarId;
    public ResourceLocation beardId;
    public ResourceLocation beardAddonId;
    public ResourceLocation hairId;
    public ResourceLocation hairAddonId;
    public ResourceLocation clothingBase;
    public ResourceLocation clothingOver;
    public ResourceLocation clothingExtra;

    public boolean haveEmissiveEyes;
    public boolean blinking;

    public boolean canShowFeet;
    public boolean canShowEars;
    public boolean canShowBeard;
    public boolean canShowHair;
    public float LOD = 0;

    public NpcEntityRenderState() {
        this.simplifiedSkinId = null;
        this.simplifiedNoseId = null;
        this.simplifiedEarId = null;
        this.simplifiedHairAddonId = null;
        this.simplifiedFeetId = null;

        this.widthScale = 1.0f;
        this.skinId = null;
        this.headId = null;
        this.earId = null;
        this.noseId = null;
        this.eyesId = null;
        this.eyesEmissiveId = null;
        this.eyebrowId = null;
        this.scarId = null;
        this.beardId = null;
        this.beardAddonId = null;
        this.hairId = null;
        this.hairAddonId = null;

        this.clothingBase = null;
        this.clothingOver = null;
        this.clothingExtra = null;

        this.haveEmissiveEyes = false;
        this.blinking = false;

        this.canShowFeet = false;
        this.canShowEars = true;
        this.canShowBeard = true;
        this.canShowHair = true;
    }
}
