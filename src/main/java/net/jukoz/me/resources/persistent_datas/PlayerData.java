package net.jukoz.me.resources.persistent_datas;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PlayerData {
    private Identifier faction;
    private Identifier spawn;
    private Identifier race;
    private BlockPos posOrigin;
    private Identifier dimensionOrigin;

    private int delversFearCountInSeconds = 0;

    public NbtCompound createNbt() {
        NbtCompound nbtCompound = new NbtCompound();
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

    public PlayerData(NbtCompound nbtCompound) {
        if(nbtCompound.contains("faction", net.minecraft.nbt.NbtElement.STRING_TYPE))
            faction = new Identifier(nbtCompound.getString("faction"));
        if(nbtCompound.contains("spawn", net.minecraft.nbt.NbtElement.STRING_TYPE))
            spawn = new Identifier(nbtCompound.getString("spawn"));
        if(nbtCompound.contains("race", net.minecraft.nbt.NbtElement.STRING_TYPE))
            race = new Identifier(nbtCompound.getString("race"));
        int[] intArray = nbtCompound.getIntArray("origin_pos");
        if(intArray.length >= 3) {
            posOrigin = new BlockPos(intArray[0], intArray[1], intArray[2]);
        }
        if(nbtCompound.contains("dimensionOrigin", net.minecraft.nbt.NbtElement.STRING_TYPE))
            dimensionOrigin = new Identifier(nbtCompound.getString("dimensionOrigin"));

        delversFearCountInSeconds = nbtCompound.getInt("delversFearCountInSeconds");
    }
    public boolean assignNewFactionInformation(Identifier factionId, Identifier spawnId){
        this.faction = factionId;
        this.spawn = spawnId;
        return true;
    }

    public boolean assignNewRace(Identifier raceId){
        this.race = raceId;
        return true;
    }

    public boolean assignNewOrigin(Identifier dimensionOrigin, BlockPos newBlockPos){
        this.dimensionOrigin = dimensionOrigin;
        this.posOrigin = newBlockPos;
        return true;
    }
    public Identifier getFaction(){
        return this.faction;
    }
    public Identifier getRace(){
        return this.race;
    }
    public Identifier getSpawn(){
        return this.spawn;
    }

    public Identifier getDimensionOrigin(){
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
