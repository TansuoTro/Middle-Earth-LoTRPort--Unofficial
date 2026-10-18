package net.jukoz.me.item.dataComponents;
import net.jukoz.me.compat.METext;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.compat.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.ColorHelper;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.armor.ArmorMaterialsME;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;

import java.util.List;

public record ArmorTierDataComponent(ArmorMaterialsME.Tiers tier) implements TooltipAppender {

    private static final Codec<ArmorTierDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ArmorMaterialsME.Tiers.CODEC.fieldOf("tier").forGetter(ArmorTierDataComponent::tier))
                .apply(instance, ArmorTierDataComponent::new);
    });
    public static final Codec<ArmorTierDataComponent> CODEC  = ComponentCodecs.alternative(BASE_CODEC, ArmorMaterialsME.Tiers.CODEC, (enabled) -> {
        return new ArmorTierDataComponent(ArmorMaterialsME.Tiers.BASIC);
    });
    public static final PacketCodec<PacketByteBuf, ArmorTierDataComponent> PACKET_CODEC  = PacketCodec.tuple(ArmorMaterialsME.Tiers.PACKET_CODEC, ArmorTierDataComponent::tier, ArmorTierDataComponent::new);
    ;

    public ArmorTierDataComponent(ArmorMaterialsME.Tiers tier){
        this.tier = tier;
    }

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Text> tooltip) {
        tooltip.add(METext.withColor( Text.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, tier.getName())), tier.getColor()));
    }

    @Override
    public ArmorMaterialsME.Tiers tier() {
        return tier;
    }
}
