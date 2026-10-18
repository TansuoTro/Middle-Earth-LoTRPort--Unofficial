package net.jukoz.me.resources.datas.combatarchetypes.utils;

import net.minecraft.nbt.NbtCompound;
import net.jukoz.me.resources.datas.combatarchetypes.CombatArchetypeData;
import net.jukoz.me.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.jukoz.me.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.jukoz.me.resources.datas.combatarchetypes.data.CombatArchetype;

import java.util.Optional;

public class CombatArchetypeDataUtil {
    public static CombatArchetypeData create(NbtCompound combatArchetypeData) {
        Optional<String> type = combatArchetypeData.getString("type");
        if(type.isEmpty())
            return null;
        CombatArchetype combatArchetype = CombatArchetype.valueOf(type.get());

        NbtCompound data = combatArchetypeData.getCompoundOrEmpty("data");
        return switch (combatArchetype) {
            case MELEE -> new MeleeCombatArchetypeData(data);
            case RANGED -> new RangedCombatArchetypeData(data);
            default -> null;
        };
    }
}
