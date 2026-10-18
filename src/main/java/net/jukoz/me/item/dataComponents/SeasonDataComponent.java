package net.jukoz.me.item.dataComponents;
import net.jukoz.me.compat.METext;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.utils.ModColors;

import java.util.List;

public record SeasonDataComponent(Season season) implements TooltipAppender {
    private static final Codec<SeasonDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Season.CODEC.fieldOf("season").forGetter(SeasonDataComponent::season))
                .apply(instance, SeasonDataComponent::new);
    });
    public static final Codec<SeasonDataComponent> CODEC = ComponentCodecs.alternative(BASE_CODEC, Season.CODEC, SeasonDataComponent::new);

    public static final PacketCodec<PacketByteBuf, SeasonDataComponent> PACKET_CODEC =
            PacketCodec.tuple(Season.PACKET_CODEC, SeasonDataComponent::season, SeasonDataComponent::new);

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Text> tooltip) {
        // 1.21 read the biome registry out of the tooltip context just to fail when no world
        // was attached; 1.20.1's TooltipContext has no registry lookup and the season is a
        // plain enum, so the line is always available.
        tooltip.add(Text.translatable("tooltip.%s.season".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GOLD)
                .append(Text.translatable("season.%s.".formatted(MiddleEarth.MOD_ID) + season.toString()).formatted(Formatting.WHITE)));
    }

    public Season season() {
        return season;
    }

    public enum Season implements StringIdentifiable {
        SPRING("spring"),
        SUMMER("summer"),
        AUTUMN("autumn"),
        WINTER("winter"),
        DEAD("dead");

        public static final Codec<Season> CODEC = StringIdentifiable.createCodec(Season::values);
        public static final PacketCodec<PacketByteBuf, Season> PACKET_CODEC =
                PacketCodec.ofStatic(Season::writeToBuf, Season::readFromBuf);
        private final String name;

        Season(String name) {
            this.name = name;
        }

        private static void writeToBuf(PacketByteBuf buf, Season value) {
            buf.writeEnumConstant(value);
        }

        private static Season readFromBuf(PacketByteBuf buf) {
            return buf.readEnumConstant(Season.class);
        }

        @Override
        public String asString() {
            return name;
        }
    }
}