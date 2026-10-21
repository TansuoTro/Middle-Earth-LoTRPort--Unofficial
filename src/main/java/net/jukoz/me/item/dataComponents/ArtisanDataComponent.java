package net.jukoz.me.item.dataComponents;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.compat.TooltipAppender;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.network.chat.Component;
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
    public static final Codec<ArtisanDataComponent> CODEC = ComponentCodecs.alternative(BASE_CODEC, Uuids.CODEC, ArtisanDataComponent::new);

    public static final PacketCodec<FriendlyByteBuf, ArtisanDataComponent> PACKET_CODEC  = PacketCodec.tuple(ComponentCodecs.UUID_CODEC, ArtisanDataComponent::uuid,
            ArtisanDataComponent::new);

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext context, List<Component> tooltip) {
        // 1.21 could resolve the artisan's profile asynchronously from the UUID; 1.20.1 has no
        // such lookup, so use the name the client already knows and fall back to the uuid.
        net.minecraft.client.network.PlayerListEntry entry = null;
        net.minecraft.client.network.ClientPlayNetworkHandler handler =
                net.minecraft.client.MinecraftClient.getInstance().getNetworkHandler();
        if (handler != null) {
            entry = handler.getPlayerListEntry(uuid);
        }
        String name = entry != null ? entry.getProfile().getName() : uuid.toString().substring(0, 8);
        tooltip.add(Component.translatable("tooltip.%s.artisan".formatted(MiddleEarth.MOD_ID)).append(name)
                .formatted(Formatting.GRAY));
    }

    @Override
    public UUID uuid() {
        return uuid;
    }
}
