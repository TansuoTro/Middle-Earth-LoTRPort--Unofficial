package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/** Sneak-attack charge timer. */
public record SneakAttackDataComponent(int timer) {
    private static final Codec<SneakAttackDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("timer").forGetter(SneakAttackDataComponent::timer))
                    .apply(instance, SneakAttackDataComponent::new));

    public static final Codec<SneakAttackDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Codec.INT, timer -> new SneakAttackDataComponent((int) timer));
}
