package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.jukoz.me.resources.datas.factions.Faction;

/**
 * Faction tag carried by items and NPC equipment.
 *
 * Resolves parent factions against the current world's synced dynamic registry.
 */
public class FactionDataComponent implements net.jukoz.me.compat.TooltipAppender {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Identifier.CODEC.fieldOf("faction").forGetter(FactionDataComponent::getFactionId))
                    .apply(instance, FactionDataComponent::new));

    public static final Codec<FactionDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Identifier.CODEC, FactionDataComponent::new);

    private final Identifier factionId;

    public FactionDataComponent(Identifier identifier) {
        this.factionId = identifier;
    }

    public FactionDataComponent(RegistryKey<Faction> faction) {
        this.factionId = faction.getValue();
    }

    public Identifier getFactionId() {
        return factionId;
    }

    @Override
    public void appendTooltip(net.minecraft.item.ItemStack stack, net.minecraft.client.item.TooltipContext context,
                              java.util.List<net.minecraft.text.Text> tooltip) {
        appendFaction(tooltip, factionId, false);
    }

    @Override
    public void appendTooltip(net.minecraft.item.ItemStack stack, net.minecraft.client.item.TooltipContext context,
                              java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.world.World world) {
        Faction faction = world == null ? null : world.getRegistryManager()
                .getOptional(net.jukoz.me.registries.DynamicRegistriesME.FACTION)
                .map(registry -> registry.get(factionId)).orElse(null);
        Faction parent = faction == null ? null : faction.getParentFaction(world);
        if (parent != null) {
            appendFaction(tooltip, parent.getId(), false);
            appendFaction(tooltip, factionId, true);
        } else {
            appendFaction(tooltip, factionId, false);
        }
    }

    private static void appendFaction(java.util.List<net.minecraft.text.Text> tooltip, Identifier id, boolean sub) {
        tooltip.add(net.minecraft.text.Text.translatable(sub ? "tooltip.me.sub_faction" : "tooltip.me.faction")
                .formatted(sub ? net.minecraft.util.Formatting.GRAY : net.minecraft.util.Formatting.GOLD)
                .append(net.minecraft.text.Text.translatable(id.toTranslationKey("faction"))
                        .formatted(net.minecraft.util.Formatting.WHITE)));
    }

    @Override
    public String toString() {
        return "FactionDataComponent[" + factionId + "]";
    }
}
