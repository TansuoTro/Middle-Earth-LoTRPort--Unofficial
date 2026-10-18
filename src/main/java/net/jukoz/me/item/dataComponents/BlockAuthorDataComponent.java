package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.jukoz.me.compat.net.PacketCodec;
import net.minecraft.text.Text;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.utils.BlockAuthor;

import java.util.List;

public record BlockAuthorDataComponent(BlockAuthor author) implements TooltipAppender {
    private static final Codec<BlockAuthorDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockAuthor.CODEC.fieldOf("author").forGetter(BlockAuthorDataComponent::author))
                .apply(instance, BlockAuthorDataComponent::new);
    });
    public static final Codec<BlockAuthorDataComponent> CODEC  = ComponentCodecs.alternative(BASE_CODEC, BlockAuthor.CODEC, BlockAuthorDataComponent::new);
    public static final PacketCodec<PacketByteBuf, BlockAuthorDataComponent> PACKET_CODEC  = PacketCodec.tuple(BlockAuthor.PACKET_CODEC, BlockAuthorDataComponent::author, BlockAuthorDataComponent::new);
    ;
    @Override
    public BlockAuthor author() {
        return author;
    }

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Text> tooltip) {
        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".author").append(this.author.getAuthorName()));
    }
}
