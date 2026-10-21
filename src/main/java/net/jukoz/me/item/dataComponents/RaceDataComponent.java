package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

/** Race restriction carried by equipment. */
public record RaceDataComponent(Identifier raceId) implements net.jukoz.me.compat.TooltipAppender {
    private static final Codec<RaceDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Identifier.CODEC.fieldOf("race").forGetter(RaceDataComponent::raceId))
                    .apply(instance, RaceDataComponent::new));

    public static final Codec<RaceDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Identifier.CODEC, RaceDataComponent::new);

    @Override
    public void appendTooltip(net.minecraft.item.ItemStack stack, net.minecraft.client.item.TooltipContext context,
                              java.util.List<net.minecraft.text.Text> tooltip) {
        tooltip.add(net.minecraft.text.Text.translatable("tooltip.me.race")
                .formatted(net.minecraft.util.Formatting.DARK_RED)
                .append(net.minecraft.text.Text.translatable(raceId.toTranslationKey("race"))
                        .formatted(net.minecraft.util.Formatting.WHITE)));
    }
}
