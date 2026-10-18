package net.jukoz.me.item.dataComponents;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Uuids;
import net.minecraft.util.dynamic.Codecs;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;
import net.jukoz.me.resources.datas.factions.Faction;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.List;

public record ArtisanDataComponent(UUID uuid) implements TooltipAppender {
    private static final Codec<ArtisanDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Uuids.CODEC.fieldOf("uuid").forGetter(ArtisanDataComponent::uuid))
                .apply(instance, ArtisanDataComponent::new);
    });
    public static final Codec<ArtisanDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Uuids.CODEC, ArtisanDataComponent::new);

    public static final PacketCodec<ByteBuf, ArtisanDataComponent> PACKET_CODEC  = PacketCodec.tuple(Uuids.PACKET_CODEC, ArtisanDataComponent::uuid,
            ArtisanDataComponent::new);

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip) {
        CompletableFuture<Optional<GameProfile>> profile = SkullBlockEntity.fetchProfileByUuid(uuid);
        try {
            if (profile.get().isPresent()){
                try {
                    tooltip.add(Text.translatable("tooltip.%s.artisan".formatted(MiddleEarth.MOD_ID)).append(
                            profile.get().get().getName()).formatted(Formatting.GRAY));
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID uuid() {
        return uuid;
    }
}
