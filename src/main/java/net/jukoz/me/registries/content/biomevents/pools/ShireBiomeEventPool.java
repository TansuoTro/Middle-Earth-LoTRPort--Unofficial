package net.jukoz.me.registries.content.biomevents.pools;

import net.jukoz.me.registries.content.npctypes.NpcRegistry;
import net.jukoz.me.resources.datas.biome_events.BiomeEventData;
import net.jukoz.me.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class ShireBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(12).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_MILITIA).withWeight(3).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_SHIRRIFF).withSameNpcType(0, 256)
        ));
    }
}
