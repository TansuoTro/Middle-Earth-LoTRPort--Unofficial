package net.jukoz.me.item.dataComponents;

import net.jukoz.me.item.MEComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.compat.DyedColorComponent;
import net.minecraft.item.Item;
import net.jukoz.me.compat.TooltipAppender;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.armor.DyeablePiecesME;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.util.math.ColorHelper;

import java.util.List;

public record BackAttachmentDataComponent(BackAttachmentsME backAttachment, int backAttachmentColor) implements TooltipAppender {

    private static final Codec<BackAttachmentDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BackAttachmentsME.CODEC.fieldOf("back_attachment").forGetter(BackAttachmentDataComponent::getBackAttachment),
                Codec.INT.optionalFieldOf("back_attachment_color", DyedColorComponent.DEFAULT_COLOR).forGetter(BackAttachmentDataComponent::backAttachmentColor))
                .apply(instance, BackAttachmentDataComponent::new);
    });
    public static final Codec<BackAttachmentDataComponent> CODEC  = ComponentCodecs.alternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new BackAttachmentDataComponent(BackAttachmentsME.CAPE, DyedColorComponent.DEFAULT_COLOR);
    });
    public static final PacketCodec<PacketByteBuf, BackAttachmentDataComponent> PACKET_CODEC  = PacketCodec.tuple(BackAttachmentsME.PACKET_CODEC, BackAttachmentDataComponent::getBackAttachment, PacketCodecs.INTEGER, BackAttachmentDataComponent::backAttachmentColor, BackAttachmentDataComponent::new);
    ;

    public BackAttachmentDataComponent(BackAttachmentsME backAttachment, int backAttachmentColor){
        this.backAttachment = backAttachment;
        this.backAttachmentColor = backAttachmentColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        BackAttachmentDataComponent backAttachmentDataComponent = MEComponents.get(stack, MEComponents.BACK_ATTACHMENT_DATA);
        return backAttachmentDataComponent != null ? ComponentCodecs.fullAlpha(backAttachmentDataComponent.backAttachmentColor) : defaultColor;
    }

    public static BackAttachmentDataComponent newBackAttachment(BackAttachmentsME backAttachment) {
        return new BackAttachmentDataComponent(backAttachment, DyedColorComponent.DEFAULT_COLOR);
    }

    public static BackAttachmentDataComponent newBackAttachmentWithColor(BackAttachmentsME backAttachment, int backAttachmentColor) {
        return new BackAttachmentDataComponent(backAttachment, backAttachmentColor);
    }

    public static ItemStack setBackAttachment(ItemStack stack, BackAttachmentsME backAttachment){
        ItemStack itemStack = stack.copyWithCount(1);

        MEComponents.set(itemStack, MEComponents.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachment , DyedColorComponent.DEFAULT_COLOR));
        return itemStack;
    }

    public static ItemStack setBackAttachmentWithColor(ItemStack stack, BackAttachmentsME backAttachment, int backAttachmentColor){
        ItemStack itemStack = stack.copyWithCount(1);

        MEComponents.set(itemStack, MEComponents.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachment, backAttachmentColor));
        return itemStack;
    }


    public BackAttachmentsME getBackAttachment(){
        return backAttachment();
    }

    public int backAttachmentColor() {
        return backAttachmentColor;
    }

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Text> tooltip) {
        tooltip.add(Text.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, this.backAttachment().getName())).formatted(Formatting.GRAY));
    }
}
