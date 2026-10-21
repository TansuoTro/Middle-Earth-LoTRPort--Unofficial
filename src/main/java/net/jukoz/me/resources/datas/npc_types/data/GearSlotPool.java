package net.jukoz.me.resources.datas.npc_types.data;

import net.jukoz.me.compat.NbtCompat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
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

    public static Tag createNbt(GearSlotPool slotData){
        if(slotData.isPool()){
            CompoundTag nbt = new CompoundTag();
            ListTag nbtList = new ListTag();
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

    public static GearSlotPool readNbt(Tag nbt){
        if(NbtCompat.isCompound(nbt)){
            CompoundTag nbtCompound = NbtCompat.compound(nbt);
            if(nbtCompound.get("pool") == null){
                return GearSlotPool.create(new WeightedItemData(nbtCompound));
            }
            ListTag list = nbtCompound.getList("pool", Tag.COMPOUND_TYPE);
            GearSlotPool gearSlotPool = GearSlotPool.create();
            for(int i = 0; i < list.size(); i++){
                // getString() stringifies compound elements in 1.20.1, so the compound form
                // has to be tested first.
                if(list.get(i) instanceof CompoundTag compound){
                    gearSlotPool.add(new WeightedItemData(compound));
                } else if(!list.getString(i).isEmpty()){
                    gearSlotPool.add(new WeightedItemData(MiddleEarth.fetchId(list.getString(i))));
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
