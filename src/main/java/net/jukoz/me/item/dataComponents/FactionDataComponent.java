package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

/**
 * Faction tag carried by items and NPC equipment.
 *
 * <p>Ported from the 1.21.8 donor. The tooltip body is not ported yet: it needs the
 * faction registry ({@code DynamicRegistriesME.FACTION}), which does not exist in the
 * 1.20.1 tree until the faction system is ported.
 */
public class FactionDataComponent {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Identifier.CODEC.fieldOf("faction").forGetter(FactionDataComponent::getFactionId))
                    .apply(instance, FactionDataComponent::new));

    public static final Codec<FactionDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Identifier.CODEC, FactionDataComponent::new);

    private final Identifier factionId;

    public FactionDataComponent(Identifier identifier) {
        this.factionId = identifier;
    }

    public Identifier getFactionId() {
        return factionId;
    }

    @Override
    public String toString() {
        return "FactionDataComponent[" + factionId + "]";
    }
}
