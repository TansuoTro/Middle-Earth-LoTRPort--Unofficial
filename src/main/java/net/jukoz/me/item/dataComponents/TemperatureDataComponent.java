package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;

/** Heat level of a forge workpiece. */
public record TemperatureDataComponent(int temperature) implements net.jukoz.me.compat.TooltipAppender {
    private static final Codec<TemperatureDataComponent> BASE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("temperature").forGetter(TemperatureDataComponent::temperature))
                    .apply(instance, TemperatureDataComponent::new));

    public static final Codec<TemperatureDataComponent> CODEC =
            ComponentCodecs.alternative(BASE_CODEC, Codec.INT, TemperatureDataComponent::new);

    @Override
    public void appendTooltip(net.minecraft.world.item.ItemStack stack, net.minecraft.world.item.TooltipFlag context,
                              java.util.List<net.minecraft.network.chat.Component> tooltip) {
        if (temperature < 0) return;
        int tier = Math.min(4, temperature / 20);
        net.jukoz.me.utils.ModColors[] colors = {net.jukoz.me.utils.ModColors.TEMP_1,
                net.jukoz.me.utils.ModColors.TEMP_2, net.jukoz.me.utils.ModColors.TEMP_3,
                net.jukoz.me.utils.ModColors.TEMP_4, net.jukoz.me.utils.ModColors.TEMP_5};
        tooltip.add(net.minecraft.network.chat.Component.translatable("tooltip.me.temp_" + (tier + 1))
                .styled(style -> style.withColor(colors[tier].color)));
    }
}
