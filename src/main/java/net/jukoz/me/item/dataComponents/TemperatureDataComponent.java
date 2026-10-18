package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/** Body temperature rating of an armour piece. Tooltip not ported yet. */
public record TemperatureDataComponent(int temperature) {
    private static final Codec<TemperatureDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("temperature").forGetter(TemperatureDataComponent::temperature))
                    .apply(instance, TemperatureDataComponent::new));

    public static final Codec<TemperatureDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Codec.INT, TemperatureDataComponent::new);
}
