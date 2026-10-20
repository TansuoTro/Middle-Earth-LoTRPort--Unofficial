package net.jukoz.me.api.dtos;
import net.jukoz.me.compat.NbtCompat;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class WeightedIdentifier extends WeightedItem<Identifier> {
    public WeightedIdentifier(NbtElement element){
        super(element);

        if(!element.asString().isEmpty()){
            this.item = new Identifier(element.asString());
        } else if(NbtCompat.isCompound(element)) {
            String potentialId = NbtCompat.compound(element).getString("id");
            if (!potentialId.isEmpty()) {
                this.item = new Identifier(potentialId);
            }
        }
    }
    public WeightedIdentifier(Identifier value) {
        super(value);
    }
    public WeightedIdentifier(Identifier value, int i) {
        super(value, i);
    }
    public static WeightedIdentifier fromIdentifier(Identifier id){
        return new WeightedIdentifier(id, 1);
    }
    public static WeightedIdentifier fromKey(RegistryKey key){
        return new WeightedIdentifier(key.getValue(), 1);
    }

    @Override
    public WeightedIdentifier withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    @Override
    public NbtElement getNbt(){
        // Always a compound: a weighted pool serialises into a single NbtList, and NbtList
        // refuses to mix tag types, so the bare-string form used for weight==1 corrupted every
        // pool that held both weighted and unweighted entries.
        NbtElement newNbt = super.getNbt();
        if(newNbt == null)
            newNbt = new NbtCompound();

        NbtCompat.compound(newNbt).putString("id", this.item.toString());
        return newNbt;
    }
}
