package net.jukoz.me.resources.persistent_datas;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class PlayerData {
    private ResourceLocation faction;
    private ResourceLocation spawn;
    private ResourceLocation race;
    private BlockPos posOrigin;
    private ResourceLocation dimensionOrigin;

    private int delversFearCountInSeconds = 0;

    public CompoundTag createNbt() {
        CompoundTag nbtCompound = new CompoundTag();
        if(faction != null)
            nbtCompound.putString("faction", faction.toString());
        if(spawn != null)
            nbtCompound.putString("spawn", spawn.toString());
        if(race != null)
            nbtCompound.putString("race", race.toString());
        if(posOrigin != null)
            nbtCompound.putIntArray("origin_pos", new int[]{posOrigin.getX(), posOrigin.getY(), posOrigin.getZ()});
        if(dimensionOrigin != null)
            nbtCompound.putString("dimensionOrigin", dimensionOrigin.toString());

        nbtCompound.putInt("delversFearCountInSeconds", delversFearCountInSeconds);

        return nbtCompound;
    }
    public PlayerData() {}

    public PlayerData(CompoundTag nbtCompound) {
        if(nbtCompound.contains("faction", net.minecraft.nbt.NbtElement.STRING_TYPE))
            faction = new ResourceLocation(nbtCompound.getString("faction"));
        if(nbtCompound.contains("spawn", net.minecraft.nbt.NbtElement.STRING_TYPE))
            spawn = new ResourceLocation(nbtCompound.getString("spawn"));
        if(nbtCompound.contains("race", net.minecraft.nbt.NbtElement.STRING_TYPE))
            race = new ResourceLocation(nbtCompound.getString("race"));
        int[] intArray = nbtCompound.getIntArray("origin_pos");
        if(intArray.length >= 3) {
            posOrigin = new BlockPos(intArray[0], intArray[1], intArray[2]);
        }
        if(nbtCompound.contains("dimensionOrigin", net.minecraft.nbt.NbtElement.STRING_TYPE))
            dimensionOrigin = new ResourceLocation(nbtCompound.getString("dimensionOrigin"));

        delversFearCountInSeconds = nbtCompound.getInt("delversFearCountInSeconds");
    }
    public boolean assignNewFactionInformation(ResourceLocation factionId, ResourceLocation spawnId){
        this.faction = factionId;
        this.spawn = spawnId;
        return true;
    }

    public boolean assignNewRace(ResourceLocation raceId){
        this.race = raceId;
        return true;
    }

    public boolean assignNewOrigin(ResourceLocation dimensionOrigin, BlockPos newBlockPos){
        this.dimensionOrigin = dimensionOrigin;
        this.posOrigin = newBlockPos;
        return true;
    }
    public ResourceLocation getFaction(){
        return this.faction;
    }
    public ResourceLocation getRace(){
        return this.race;
    }
    public ResourceLocation getSpawn(){
        return this.spawn;
    }

    public ResourceLocation getDimensionOrigin(){
        return this.dimensionOrigin;
    }
    public BlockPos getOriginPos(){
        return this.posOrigin;
    }


    public int getDelversFearCountInSeconds(){
        return this.delversFearCountInSeconds;
    }
    public void addToDelversFearCountInSeconds(){
         this.delversFearCountInSeconds += 1;
    }
    public void resetDelversFearCount() {
        this.delversFearCountInSeconds = 0;
    }
}
