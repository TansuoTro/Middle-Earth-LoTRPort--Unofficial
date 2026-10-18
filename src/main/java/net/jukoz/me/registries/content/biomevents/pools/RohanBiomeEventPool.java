package net.jukoz.me.registries.content.biomevents.pools;

import net.jukoz.me.registries.content.npctypes.NpcRegistry;
import net.jukoz.me.resources.datas.biome_events.BiomeEventData;
import net.jukoz.me.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class RohanBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ROHAN_MILITIA).withWeight(7).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.ROHAN_SOLDIER).withWeight(3).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.ROHAN_KNIGHT).withWeight(2).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.ROHAN_EORLING_MARSHAL).withWeight(1).withSameNpcType(0, 512)
        ));
    }
}
