package net.jukoz.me.resources.datas.npc_types.data;
import net.jukoz.me.compat.NbtCompat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.jukoz.me.api.dtos.WeightedPool;
import net.jukoz.me.MiddleEarth;

public class GearSlotPool {
    private WeightedItemData weightedItemData;
    private WeightedPool<WeightedItemData> npcGearItemPool;

    public GearSlotPool() {
        this.npcGearItemPool = new WeightedPool<>();
    }

    public static GearSlotPool create() {
        return new GearSlotPool();
    }

    public static GearSlotPool create(WeightedItemData weightedItemData) {
        return new GearSlotPool(weightedItemData);
    }

    public GearSlotPool(WeightedItemData weightedItemData) {
        this.weightedItemData = weightedItemData;
    }

    public GearSlotPool add(WeightedItemData newGearData){
        if(this.npcGearItemPool == null)
            this.npcGearItemPool = new WeightedPool<>();
        if(this.weightedItemData != null)
            this.weightedItemData = null;
        this.npcGearItemPool.add(newGearData);
        return this;
    }

    public boolean isPool(){
        return npcGearItemPool != null && !npcGearItemPool.isEmpty();
    }

    public static NbtElement createNbt(GearSlotPool slotData){
        if(slotData.isPool()){
            NbtCompound nbt = new NbtCompound();
            NbtList nbtList = new NbtList();
            for(WeightedItemData gearItemData : slotData.npcGearItemPool.elements){
                nbtList.add(gearItemData.getNbt());
            }
            nbt.put("pool",nbtList);
            return nbt;
        }
        else if(slotData.weightedItemData == null){
            return null;
        }
        var nbt = slotData.weightedItemData.getNbt();
        if(!nbt.asString().isEmpty()){
            return nbt;
        } else if(NbtCompat.isCompound(nbt))
            return NbtCompat.compound(nbt);
        return null;
    }

    public static GearSlotPool readNbt(NbtElement nbt){
        if(NbtCompat.isCompound(nbt)){
            NbtCompound nbtCompound = NbtCompat.compound(nbt);
            if(nbtCompound.get("pool") == null){
                return GearSlotPool.create(new WeightedItemData(nbtCompound));
            }
            NbtList list = nbtCompound.getList("pool", NbtElement.COMPOUND_TYPE);
            GearSlotPool gearSlotPool = GearSlotPool.create();
            for(int i = 0; i < list.size(); i++){
                if(!list.getString(i).isEmpty()){
                    gearSlotPool.add(new WeightedItemData(MiddleEarth.fetchId(list.getString(i))));
                } else if(!list.getCompound(i).isEmpty()){
                    gearSlotPool.add(new WeightedItemData(list.getCompound(i)));
                }
            }
            return gearSlotPool;
        } else if(!nbt.asString().isEmpty()){
            return new GearSlotPool(new WeightedItemData(MiddleEarth.fetchId(nbt.asString())));
        }

        return null;
    }

    public ItemStack getItemStack() {
        if(isPool()){
            return npcGearItemPool.getRandom().getItem().getItemStack();
        }
        if(weightedItemData != null)
            return weightedItemData.getItem().getItemStack();
        return ItemStack.EMPTY;
    }
}
