package net.jukoz.me.resources.datas.texture_presets;
import net.jukoz.me.compat.NbtCompat;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;

public class SimplifiedTexturePreset {
    public Identifier base;
    public Identifier hair;
    public Identifier feet;
    public Identifier nose;
    public Identifier ear;

    public SimplifiedTexturePreset() {
    }

    public SimplifiedTexturePreset(NbtCompound source) {
        source.getString("base").ifPresent(value -> this.base = MiddleEarth.fetchId(value));
        source.getString("hair").ifPresent(value -> this.hair = MiddleEarth.fetchId(value));
        source.getString("feet").ifPresent(value -> this.feet = MiddleEarth.fetchId(value));
        source.getString("nose").ifPresent(value -> this.nose = MiddleEarth.fetchId(value));
        source.getString("ear").ifPresent(value -> this.ear = MiddleEarth.fetchId(value));
    }


    public static SimplifiedTexturePreset create(Identifier base) {
        SimplifiedTexturePreset texturePreset = new SimplifiedTexturePreset();
        texturePreset.base = base;
        return texturePreset;
    }

    public SimplifiedTexturePreset withHair(Identifier hair){
        this.hair = hair;
        return this;
    }

    public SimplifiedTexturePreset withFeet(Identifier feet){
        this.feet = feet;
        return this;
    }

    public SimplifiedTexturePreset withNose(Identifier nose){
        this.nose = nose;
        return this;
    }

    public SimplifiedTexturePreset withEar(Identifier ear){
        this.ear = ear;
        return this;
    }


    public NbtElement getNbt(NbtElement newNbt) {
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
