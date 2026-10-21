package net.jukoz.me.api.dtos;

import net.jukoz.me.compat.NbtCompat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class WeightedIdentifier extends WeightedItem<ResourceLocation> {
    public WeightedIdentifier(Tag element){
        super(element);

        // asString() stringifies any element in 1.20.1, so a compound would asString() to its whole JSON form.
        if(NbtCompat.isCompound(element)) { this.item = new ResourceLocation(NbtCompat.compound(element).getString("id")); } else { this.item = new ResourceLocation(element.asString()); }
    }
    public WeightedIdentifier(ResourceLocation value) {
        super(value);
    }
    public WeightedIdentifier(ResourceLocation value, int i) {
        super(value, i);
    }
    public static WeightedIdentifier fromIdentifier(ResourceLocation id){
        return new WeightedIdentifier(id, 1);
    }
    public static WeightedIdentifier fromKey(ResourceKey key){
        return new WeightedIdentifier(key.location(), 1);
    }

    @Override
    public WeightedIdentifier withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    @Override
    public Tag getNbt(){
        // Always a compound: a weighted pool serialises into a single ListTag, and ListTag
        // refuses to mix tag types, so the bare-string form used for weight==1 corrupted every
        // pool that held both weighted and unweighted entries.
        Tag newNbt = super.getNbt();
        if(newNbt == null)
            newNbt = new CompoundTag();

        NbtCompat.compound(newNbt).putString("id", this.item.toString());
        return newNbt;
    }
}
