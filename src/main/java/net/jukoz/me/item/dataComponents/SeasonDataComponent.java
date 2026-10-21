package net.jukoz.me.item.dataComponents;

import net.jukoz.me.compat.METext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
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

    public static final PacketCodec<FriendlyByteBuf, SeasonDataComponent> PACKET_CODEC =
            PacketCodec.tuple(Season.PACKET_CODEC, SeasonDataComponent::season, SeasonDataComponent::new);

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.world.item.TooltipFlag context, List<Component> tooltip) {
        // 1.21 read the biome registry out of the tooltip context just to fail when no world
        // was attached; 1.20.1's TooltipFlag has no registry lookup and the season is a
        // plain enum, so the line is always available.
        tooltip.add(Component.translatable("tooltip.%s.season".formatted(MiddleEarth.MOD_ID)).formatted(ChatFormatting.GOLD)
                .append(Component.translatable("season.%s.".formatted(MiddleEarth.MOD_ID) + season.toString()).formatted(ChatFormatting.WHITE)));
    }

    public Season season() {
        return season;
    }

    public enum Season implements StringRepresentable {
        SPRING("spring"),
        SUMMER("summer"),
        AUTUMN("autumn"),
        WINTER("winter"),
        DEAD("dead");

        public static final Codec<Season> CODEC = StringRepresentable.fromEnum(Season::values);
        public static final PacketCodec<FriendlyByteBuf, Season> PACKET_CODEC =
                PacketCodec.ofStatic(Season::writeToBuf, Season::readFromBuf);
        private final String name;

        Season(String name) {
            this.name = name;
        }

        private static void writeToBuf(FriendlyByteBuf buf, Season value) {
            buf.writeEnumConstant(value);
        }

        private static Season readFromBuf(FriendlyByteBuf buf) {
            return buf.readEnumConstant(Season.class);
        }

        @Override
        public String asString() {
            return name;
        }
    }
}
