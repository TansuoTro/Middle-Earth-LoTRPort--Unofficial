package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

/** Race restriction carried by equipment. Tooltip not ported yet. */
public record RaceDataComponent(Identifier raceId) {
    private static final Codec<RaceDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Identifier.CODEC.fieldOf("race").forGetter(RaceDataComponent::raceId))
                    .apply(instance, RaceDataComponent::new));

    public static final Codec<RaceDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Identifier.CODEC, RaceDataComponent::new);
}
