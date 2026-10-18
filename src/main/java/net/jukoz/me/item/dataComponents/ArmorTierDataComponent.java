package net.jukoz.me.item.dataComponents;
import net.jukoz.me.compat.METext;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.ColorHelper;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.utils.armor.ArmorMaterialsME;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;

import java.util.function.Consumer;

public record ArmorTierDataComponent(ArmorMaterialsME.Tiers tier) implements TooltipAppender {

    private static final Codec<ArmorTierDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ArmorMaterialsME.Tiers.CODEC.fieldOf("tier").forGetter(ArmorTierDataComponent::tier))
                .apply(instance, ArmorTierDataComponent::new);
    });
    public static final Codec<ArmorTierDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, ArmorMaterialsME.Tiers.CODEC, (enabled) -> {
        return new ArmorTierDataComponent(ArmorMaterialsME.Tiers.BASIC);
    });
    public static final PacketCodec<ByteBuf, ArmorTierDataComponent> PACKET_CODEC  = PacketCodec.tuple(ArmorMaterialsME.Tiers.PACKET_CODEC, ArmorTierDataComponent::tier, ArmorTierDataComponent::new);
    ;

    public ArmorTierDataComponent(ArmorMaterialsME.Tiers tier){
        this.tier = tier;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(METext.withColor(METext, Text.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, tier.getName())), tier.getColor()));
    }

    @Override
    public ArmorMaterialsME.Tiers tier() {
        return tier;
    }
}
