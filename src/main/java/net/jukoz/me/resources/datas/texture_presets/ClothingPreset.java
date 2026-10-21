package net.jukoz.me.resources.datas.texture_presets;

import net.jukoz.me.compat.NbtCompat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.jukoz.me.api.dtos.WeightedIdentifier;
import net.jukoz.me.api.dtos.WeightedPool;

public class ClothingPreset {
    public WeightedPool<WeightedIdentifier> bases;
    public WeightedPool<WeightedIdentifier> overs;
    public WeightedPool<WeightedIdentifier> extras;

    public ClothingPreset(WeightedPool<WeightedIdentifier> bases, WeightedPool<WeightedIdentifier> overs, WeightedPool<WeightedIdentifier> extras) {
        this.bases = bases;
        this.overs = overs;
        this.extras = extras;
    }

    public ClothingPreset(CompoundTag source) {
        bases = new WeightedPool<>();
        overs = new WeightedPool<>();
        extras = new WeightedPool<>();

        if(source.contains("bases")){
            ListTag baseList = source.getList("bases", Tag.COMPOUND_TYPE);
            baseList.forEach( x -> {
                bases.add(new WeightedIdentifier(x));
            });
        }

        if(source.contains("overs")){
            ListTag overList = source.getList("overs", Tag.COMPOUND_TYPE);
            overList.forEach( x -> {
                overs.add(new WeightedIdentifier(x));
            });
        }

        if(source.contains("extras")){
            ListTag extraList = source.getList("extras", Tag.COMPOUND_TYPE);
            extraList.forEach( x -> {
                extras.add(new WeightedIdentifier(x));
            });
        }
    }

    public Tag getNbt(Tag newNbt) {
        if(bases != null){
            var baseList = new ListTag();
            for(int i = 0; i < bases.size(); i++){
                Tag element = bases.get(i).getNbt();
                baseList.add(i, element);
            }
            NbtCompat.compound(newNbt).put("bases", baseList);
        }
        if(overs != null){
            var overList = new ListTag();
            for(int i = 0; i < overs.size(); i++){
                Tag element = overs.get(i).getNbt();
                overList.add(i, element);
            }
            NbtCompat.compound(newNbt).put("overs", overList);
        }
        if(extras != null){
            var extraList = new ListTag();
            for(int i = 0; i < extras.size(); i++){
                Tag element = extras.get(i).getNbt();
                extraList.add(i, element);
            }
            NbtCompat.compound(newNbt).put("extras", extraList);
        }
        return newNbt;
    }
}
