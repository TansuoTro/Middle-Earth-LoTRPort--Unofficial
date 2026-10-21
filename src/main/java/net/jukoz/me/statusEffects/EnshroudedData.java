package net.jukoz.me.statusEffects;

import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.utils.IEntityDataSaver;

public class EnshroudedData {
    public static final String KEY = "enshrouded";
    public static final int STOPPING_TICK = 30;

    public static void addEffect(IEntityDataSaver player, int amount) {
        CompoundTag nbt = player.getPersistentData();
        int effect = readEffect(player);

        effect = Math.min(100, effect + amount);
        effect = Math.max(0, effect);

        nbt.putInt(KEY, effect);
    }

    public static int readEffect(IEntityDataSaver player) {
        CompoundTag nbt = player.getPersistentData();
        return nbt.contains(KEY) ? nbt.getInt(KEY) : 0;
    }
    public static void resetEffect(IEntityDataSaver player){
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt(KEY, 0);
    }

    public static void stopEffect(IEntityDataSaver player){
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt(KEY, STOPPING_TICK);
    }
}
