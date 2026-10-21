package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/** Race restriction carried by equipment. */
public record RaceDataComponent(ResourceLocation raceId) implements net.jukoz.me.compat.TooltipAppender {
    private static final Codec<RaceDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(ResourceLocation.CODEC.fieldOf("race").forGetter(RaceDataComponent::raceId))
                    .apply(instance, RaceDataComponent::new));

    public static final Codec<RaceDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, ResourceLocation.CODEC, RaceDataComponent::new);

    @Override
    public void appendTooltip(net.minecraft.world.item.ItemStack stack, net.minecraft.world.item.TooltipFlag context,
                              java.util.List<net.minecraft.network.chat.Component> tooltip) {
        tooltip.add(net.minecraft.network.chat.Component.translatable("tooltip.me.race")
                .formatted(net.minecraft.util.ChatFormatting.DARK_RED)
                .append(net.minecraft.network.chat.Component.translatable(raceId.toLanguageKey("race"))
                        .formatted(net.minecraft.util.ChatFormatting.WHITE)));
    }
}
