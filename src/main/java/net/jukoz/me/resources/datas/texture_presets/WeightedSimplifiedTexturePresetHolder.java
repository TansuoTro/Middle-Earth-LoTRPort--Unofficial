package net.jukoz.me.resources.datas.texture_presets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtElement;
import net.jukoz.me.api.dtos.WeightedItem;


public class WeightedSimplifiedTexturePresetHolder extends WeightedItem<SimplifiedTexturePreset> {

    public WeightedSimplifiedTexturePresetHolder(CompoundTag source){
        super(source);
        this.item = new SimplifiedTexturePreset(source);
    }
    public WeightedSimplifiedTexturePresetHolder(SimplifiedTexturePreset texturePresets){
        this(texturePresets, 1);
    }

    public WeightedSimplifiedTexturePresetHolder(SimplifiedTexturePreset texturePreset, int weight){
        this.item = texturePreset;
        this.weight = weight;
    }


    @Override
    public WeightedItem<SimplifiedTexturePreset> withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }


    @Override
    public NbtElement getNbt() {
        NbtElement newNbt = super.getNbt();
        if(newNbt == null)
            newNbt = new CompoundTag();
        return this.item.getNbt(newNbt);
    }
}
