package net.jukoz.me.entity.npcs.data;
import net.jukoz.me.compat.NbtCompat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.npcs.renderer.NpcRenderedPart;
import net.jukoz.me.resources.datas.texture_presets.SimplifiedTexturePreset;

public class NpcTextureData {
    private ResourceLocation simplifiedSkin;
    private ResourceLocation simplifiedEar;
    private ResourceLocation simplifiedFeet;
    private ResourceLocation simplifiedHair;
    private ResourceLocation simplifiedNose;

    private ResourceLocation bodyTexture;
    private ResourceLocation feetTexture;
    private ResourceLocation headTexture;
    private ResourceLocation earTexture;
    private ResourceLocation noseTexture;
    private ResourceLocation eyeTexture;
    private ResourceLocation eyeEmissiveTexture;
    private ResourceLocation eyebrowTexture;
    private ResourceLocation scarTexture;
    private ResourceLocation beardTexture;
    private ResourceLocation beardAddonTexture;
    private ResourceLocation hairTexture;
    private ResourceLocation hairAddonTexture;
    private ResourceLocation clothingBaseTexture;
    private ResourceLocation clothingOverTexture;
    private ResourceLocation clothingExtraTexture;

    private Boolean eyeIsEmissive;

    public static final Codec<NpcTextureData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        CompoundTag.CODEC.fieldOf("dynamic").forGetter(NpcTextureData::writeDynamic),
        CompoundTag.CODEC.fieldOf("simplified").forGetter(NpcTextureData::writeSimplified)
    ).apply(instance, NpcTextureData::new));

    public static final PacketCodec<FriendlyByteBuf, NpcTextureData> PACKET_CODEC;

    public NpcTextureData(CompoundTag dynamic, CompoundTag simplified)
    {
        this.simplifiedSkin = MiddleEarth.fetchId(NbtCompat.getString(simplified, "skin", null));
        this.simplifiedEar = MiddleEarth.fetchId(NbtCompat.getString(simplified, "ear", null));
        this.simplifiedFeet = MiddleEarth.fetchId(NbtCompat.getString(simplified, "feet", null));
        this.simplifiedHair = MiddleEarth.fetchId(NbtCompat.getString(simplified, "hair", null));
        this.simplifiedNose = MiddleEarth.fetchId(NbtCompat.getString(simplified, "nose", null));

        this.bodyTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.BODY.getField(), null));
        this.headTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.HEAD.getField(), null));
        this.feetTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.FEET.getField(), null));

        this.earTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.EAR.getField(), null));
        this.noseTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.NOSE.getField(), null));

        this.scarTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.SCAR.getField(), null));
        this.eyeTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.EYE.getField(), null));
        this.eyeEmissiveTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.EYE_EMISSIVE.getField(), null));
        this.eyeIsEmissive = dynamic.getBoolean(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField());

        this.hairTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.HAIR.getField(), null));
        this.hairAddonTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.HAIR_ADDON.getField(), null));

        this.eyebrowTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.EYEBROW.getField(), null));

        this.beardTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.BEARD.getField(), null));
        this.beardAddonTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.BEARD_ADDON.getField(), null));

        this.clothingBaseTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.CLOTHING_BASE.getField(), null));
        this.clothingOverTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.CLOTHING_OVER.getField(), null));
        this.clothingExtraTexture = MiddleEarth.fetchId(NbtCompat.getString(dynamic, NpcRenderedPart.CLOTHING_EXTRA.getField(), null));
    }

    private CompoundTag writeSimplified() {
        CompoundTag nbt = new CompoundTag();
        if(simplifiedSkin != null)
            nbt.putString("skin", simplifiedSkin.toString());
        if(simplifiedEar != null)
            nbt.putString("ear", simplifiedEar.toString());
        if(simplifiedFeet != null)
            nbt.putString("feet", simplifiedFeet.toString());
        if(simplifiedHair != null)
            nbt.putString("hair", simplifiedHair.toString());
        if(simplifiedNose != null)
            nbt.putString("nose", simplifiedNose.toString());
        return nbt;
    }

    private CompoundTag writeDynamic() {
        CompoundTag nbt = new CompoundTag();
        if(bodyTexture != null)
            nbt.putString(NpcRenderedPart.BODY.getField(), bodyTexture.toString());
        if(headTexture != null)
            nbt.putString(NpcRenderedPart.HEAD.getField(), headTexture.toString());
        if(scarTexture != null)
            nbt.putString(NpcRenderedPart.SCAR.getField(), scarTexture.toString());
        if(earTexture != null)
            nbt.putString(NpcRenderedPart.EAR.getField(), earTexture.toString());
        if(noseTexture != null)
            nbt.putString(NpcRenderedPart.NOSE.getField(), noseTexture.toString());
        if(feetTexture != null)
            nbt.putString(NpcRenderedPart.FEET.getField(), feetTexture.toString());

        if(eyeTexture != null)
            nbt.putString(NpcRenderedPart.EYE.getField(), eyeTexture.toString());
        if(eyeEmissiveTexture != null)
            nbt.putString(NpcRenderedPart.EYE_EMISSIVE.getField(), eyeEmissiveTexture.toString());
        if(eyeIsEmissive != null)
            nbt.putBoolean(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField(), eyeIsEmissive);

        if(hairTexture != null){
            nbt.putString(NpcRenderedPart.HAIR.getField(), hairTexture.toString());
            if(hairAddonTexture != null)
                nbt.putString(NpcRenderedPart.HAIR_ADDON.getField(), hairAddonTexture.toString());
        }

        if(eyebrowTexture != null)
            nbt.putString(NpcRenderedPart.EYEBROW.getField(), eyebrowTexture.toString());

        if(beardTexture != null){
            nbt.putString(NpcRenderedPart.BEARD.getField(), beardTexture.toString());
            if(beardAddonTexture != null)
                nbt.putString(NpcRenderedPart.BEARD_ADDON.getField(), beardAddonTexture.toString());
        }

        if(clothingBaseTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_BASE.getField(), clothingBaseTexture.toString());
        if(clothingOverTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_OVER.getField(), clothingOverTexture.toString());
        if(clothingExtraTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_EXTRA.getField(), clothingExtraTexture.toString());

        return nbt;
    }

    public NpcTextureData() {
        this.eyeIsEmissive = false;
    }

    public void withSimplifiedPreset(SimplifiedTexturePreset preset) {
        if(preset == null)
            return;

        this.simplifiedSkin = preset.base;
        this.simplifiedEar = preset.ear;
        this.simplifiedFeet = preset.feet;
        this.simplifiedHair = preset.hair;
        this.simplifiedNose = preset.nose;
    }

    public NpcTextureData withSkinTexture(ResourceLocation texture){
        this.bodyTexture = texture;
        return this;
    }
    public NpcTextureData withHeadTexture(ResourceLocation texture){
        this.headTexture = texture;
        return this;
    }
    public NpcTextureData withFeetTexture(ResourceLocation texture){
        this.feetTexture = texture;
        return this;
    }
    public NpcTextureData withScarTexture(ResourceLocation texture){
        this.scarTexture = texture;
        return this;
    }
    public NpcTextureData withEarTexture(ResourceLocation texture){
        this.earTexture = texture;
        return this;
    }
    public NpcTextureData withNoseTexture(ResourceLocation texture){
        this.noseTexture = texture;
        return this;
    }

    public NpcTextureData withEyeTexture(ResourceLocation eyeTexture, ResourceLocation eyeEmissiveTexture, Boolean isEmissive){
        this.eyeTexture = eyeTexture;
        this.eyeEmissiveTexture = eyeEmissiveTexture;
        this.eyeIsEmissive = isEmissive;
        return this;
    }

    public NpcTextureData withHairTexture(ResourceLocation texture){
        this.hairTexture = texture;
        return this;
    }

    public NpcTextureData withHairAddonTexture(ResourceLocation texture){
        this.hairAddonTexture = texture;
        return this;
    }

    public NpcTextureData withEyebrowTexture(ResourceLocation texture){
        this.eyebrowTexture = texture;
        return this;
    }

    public NpcTextureData withBeardTexture(ResourceLocation texture){
        this.beardTexture = texture;
        return this;
    }

    public NpcTextureData withBeardAddonTexture(ResourceLocation texture){
        this.beardAddonTexture = texture;
        return this;
    }
    public NpcTextureData withClothingTexture(ResourceLocation textureBase, ResourceLocation textureOver, ResourceLocation textureExtra){
        this.clothingBaseTexture = textureBase;
        this.clothingOverTexture = textureOver;
        this.clothingExtraTexture = textureExtra;
        return this;
    }

    public ResourceLocation get(NpcRenderedPart part){
        return switch (part){
            case BODY -> bodyTexture;
            case HEAD -> headTexture;
            case FEET -> feetTexture;
            case EAR -> earTexture;
            case NOSE -> noseTexture;
            case SCAR -> scarTexture;
            case EYE -> eyeTexture;
            case EYE_EMISSIVE -> eyeEmissiveTexture;
            case HAIR -> hairTexture;
            case HAIR_ADDON -> hairAddonTexture;
            case EYEBROW -> eyebrowTexture;
            case BEARD -> beardTexture;
            case BEARD_ADDON -> beardAddonTexture;
            case CLOTHING_BASE -> clothingBaseTexture;
            case CLOTHING_OVER -> clothingOverTexture;
            case CLOTHING_EXTRA -> clothingExtraTexture;
            default -> null;
        };
    }

    public Boolean isEyeEmissive() {
        return this.eyeIsEmissive;
    }
    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.NBT_COMPOUND, NpcTextureData::writeDynamic,
                PacketCodecs.NBT_COMPOUND, NpcTextureData::writeSimplified,
                NpcTextureData::new);
    }

    public boolean needToBeRefreshed() {
        return get(NpcRenderedPart.BODY) == null;
    }

    public ResourceLocation getSimplifiedSkin() {
        return this.simplifiedSkin;
    }

    public ResourceLocation getSimplifiedEar() {
        return this.simplifiedEar;
    }

    public ResourceLocation getSimplifiedFeet() {
        return this.simplifiedFeet;
    }

    public ResourceLocation getSimplifiedHair() {
        return this.simplifiedHair;
    }

    public ResourceLocation getSimplifiedNose() {
        return this.simplifiedNose;
    }
}
