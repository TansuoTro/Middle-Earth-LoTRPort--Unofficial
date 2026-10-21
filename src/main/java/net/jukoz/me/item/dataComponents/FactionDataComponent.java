package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.resources.datas.factions.Faction;

/**
 * Faction tag carried by items and NPC equipment.
 *
 * Resolves parent factions against the current world's synced dynamic registry.
 */
public class FactionDataComponent implements net.jukoz.me.compat.TooltipAppender {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(ResourceLocation.CODEC.fieldOf("faction").forGetter(FactionDataComponent::getFactionId))
                    .apply(instance, FactionDataComponent::new));

    public static final Codec<FactionDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, ResourceLocation.CODEC, FactionDataComponent::new);

    private final ResourceLocation factionId;

    public FactionDataComponent(ResourceLocation identifier) {
        this.factionId = identifier;
    }

    public FactionDataComponent(ResourceKey<Faction> faction) {
        this.factionId = faction.getValue();
    }

    public ResourceLocation getFactionId() {
        return factionId;
    }

    @Override
    public void appendTooltip(net.minecraft.world.item.ItemStack stack, net.minecraft.client.item.TooltipContext context,
                              java.util.List<net.minecraft.network.chat.Component> tooltip) {
        appendFaction(tooltip, factionId, false);
    }

    @Override
    public void appendTooltip(net.minecraft.world.item.ItemStack stack, net.minecraft.client.item.TooltipContext context,
                              java.util.List<net.minecraft.network.chat.Component> tooltip, net.minecraft.world.level.Level world) {
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

    private static void appendFaction(java.util.List<net.minecraft.network.chat.Component> tooltip, ResourceLocation id, boolean sub) {
        tooltip.add(net.minecraft.network.chat.Component.translatable(sub ? "tooltip.me.sub_faction" : "tooltip.me.faction")
                .formatted(sub ? net.minecraft.util.Formatting.GRAY : net.minecraft.util.Formatting.GOLD)
                .append(net.minecraft.network.chat.Component.translatable(id.toTranslationKey("faction"))
                        .formatted(net.minecraft.util.Formatting.WHITE)));
    }

    @Override
    public String toString() {
        return "FactionDataComponent[" + factionId + "]";
    }
}
