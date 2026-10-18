package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.text.Text;

import java.util.List;

public record ArmorVariantDataComponent(Integer id) implements TooltipAppender {
    private static final Codec<ArmorVariantDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.intRange(0, 256).fieldOf("id").forGetter(ArmorVariantDataComponent::id))
                .apply(instance, ArmorVariantDataComponent::new);
    });
    public static final Codec<ArmorVariantDataComponent> CODEC = ComponentCodecs.alternative(BASE_CODEC, Codec.INT, ArmorVariantDataComponent::new);

    public static final PacketCodec<PacketByteBuf, ArmorVariantDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.INTEGER, ArmorVariantDataComponent::id,
            ArmorVariantDataComponent::new);

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Text> tooltip) {
    }

    @Override
    public Integer id() {
        return id;
    }
}
