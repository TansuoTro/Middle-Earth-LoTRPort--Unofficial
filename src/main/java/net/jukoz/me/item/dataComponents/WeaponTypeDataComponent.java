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
import net.minecraft.util.Formatting;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.armor.ArmorMaterialsME;

import java.util.List;

public record WeaponTypeDataComponent(String type) implements TooltipAppender {

    private static final Codec<WeaponTypeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.STRING.fieldOf("type").forGetter(WeaponTypeDataComponent::type))
                .apply(instance, WeaponTypeDataComponent::new);
    });
    public static final Codec<WeaponTypeDataComponent> CODEC  = ComponentCodecs.alternative(BASE_CODEC, Codec.STRING, WeaponTypeDataComponent::new);
    public static final PacketCodec<PacketByteBuf, WeaponTypeDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.STRING, WeaponTypeDataComponent::type, WeaponTypeDataComponent::new);
    ;

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Text> tooltip) {
        tooltip.add(Text.translatable("tooltip.%s.type".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GOLD)
                .append(Text.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, this.type)).formatted(Formatting.WHITE)));
    }

    @Override
    public String type() {
        return type;
    }
}
