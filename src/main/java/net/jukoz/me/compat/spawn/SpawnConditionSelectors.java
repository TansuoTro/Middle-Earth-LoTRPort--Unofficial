package net.jukoz.me.compat.spawn;

import com.mojang.serialization.Codec;

/** 1.21.5 spawn-selector shim. The port resolves variants directly, so only shape matters. */
public final class SpawnConditionSelectors {
    public static final Codec<SpawnConditionSelectors> CODEC = Codec.INT.xmap(SpawnConditionSelectors::new, s -> s.priority);

    private final int priority;

    private SpawnConditionSelectors(int priority) {
        this.priority = priority;
    }

    public static SpawnConditionSelectors createFallback(int priority) {
        return new SpawnConditionSelectors(priority);
    }

    public static SpawnConditionSelectors createSingle(BiomeSpawnCondition condition, int priority) {
        return new SpawnConditionSelectors(priority);
    }

    public int priority() {
        return priority;
    }
}
