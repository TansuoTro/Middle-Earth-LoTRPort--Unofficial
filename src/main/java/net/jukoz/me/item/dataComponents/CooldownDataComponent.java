package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/** Per-stack cooldown counter. */
public record CooldownDataComponent(int cooldown) {
    private static final Codec<CooldownDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("cooldown").forGetter(CooldownDataComponent::cooldown))
                    .apply(instance, CooldownDataComponent::new));

    public static final Codec<CooldownDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Codec.INT, CooldownDataComponent::new);
}
