package net.jukoz.me.resources.datas.texture_presets;

import net.jukoz.me.compat.NbtCompat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public class SimplifiedTexturePreset {
    public ResourceLocation base;
    public ResourceLocation hair;
    public ResourceLocation feet;
    public ResourceLocation nose;
    public ResourceLocation ear;

    public SimplifiedTexturePreset() {
    }

    public SimplifiedTexturePreset(CompoundTag source) {
        if (source.contains("base")) this.base = MiddleEarth.fetchId(source.getString("base"));
        if (source.contains("hair")) this.hair = MiddleEarth.fetchId(source.getString("hair"));
        if (source.contains("feet")) this.feet = MiddleEarth.fetchId(source.getString("feet"));
        if (source.contains("nose")) this.nose = MiddleEarth.fetchId(source.getString("nose"));
        if (source.contains("ear")) this.ear = MiddleEarth.fetchId(source.getString("ear"));
    }

    public static SimplifiedTexturePreset create(ResourceLocation base) {
        SimplifiedTexturePreset texturePreset = new SimplifiedTexturePreset();
        texturePreset.base = base;
        return texturePreset;
    }

    public SimplifiedTexturePreset withHair(ResourceLocation hair){
        this.hair = hair;
        return this;
    }

    public SimplifiedTexturePreset withFeet(ResourceLocation feet){
        this.feet = feet;
        return this;
    }

    public SimplifiedTexturePreset withNose(ResourceLocation nose){
        this.nose = nose;
        return this;
    }

    public SimplifiedTexturePreset withEar(ResourceLocation ear){
        this.ear = ear;
        return this;
    }

    public Tag getNbt(Tag newNbt) {
        if(base != null){
            NbtCompat.compound(newNbt).putString("base", this.base.toString());
        }
        if(hair != null){
            NbtCompat.compound(newNbt).putString("hair", this.hair.toString());
        }
        if(feet != null){
            NbtCompat.compound(newNbt).putString("feet", this.feet.toString());
        }
        if(nose != null){
            NbtCompat.compound(newNbt).putString("nose", this.nose.toString());
        }
        if(ear != null){
            NbtCompat.compound(newNbt).putString("ear", this.ear.toString());
        }
        return newNbt;
    }
}
