package net.jukoz.me.registries.content.biomevents.pools;

import net.jukoz.me.registries.content.npctypes.NpcRegistry;
import net.jukoz.me.resources.datas.biome_events.BiomeEventData;
import net.jukoz.me.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class DaleBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DALE_MILITIA).withWeight(7).withSameNpcType(5, 256),
            new WildSpawnEventData(NpcRegistry.DALE_SOLDIER).withWeight(3).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.DALE_ELITE_ARCHER).withWeight(3).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.DALE_KNIGHT).withWeight(1).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.DALE_SERGEANT).withWeight(0).withSameNpcType(0, 512)
        ));
    }
}
