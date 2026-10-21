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
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;
import net.jukoz.me.resources.datas.factions.Faction;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.List;
import net.minecraft.client.Minecraft;

public record ArtisanDataComponent(UUID uuid) implements TooltipAppender {
    private static final Codec<ArtisanDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(UUIDUtil.CODEC.fieldOf("uuid").forGetter(ArtisanDataComponent::uuid))
                .apply(instance, ArtisanDataComponent::new);
    });
    public static final Codec<ArtisanDataComponent> CODEC = ComponentCodecs.alternative(BASE_CODEC, UUIDUtil.CODEC, ArtisanDataComponent::new);

    public static final PacketCodec<FriendlyByteBuf, ArtisanDataComponent> PACKET_CODEC  = PacketCodec.tuple(ComponentCodecs.UUID_CODEC, ArtisanDataComponent::uuid,
            ArtisanDataComponent::new);

    @Override
    public void appendTooltip(ItemStack stack, net.minecraft.world.item.TooltipFlag context, List<Component> tooltip) {
        // 1.21 could resolve the artisan's profile asynchronously from the UUID; 1.20.1 has no
        // such lookup, so use the name the client already knows and fall back to the uuid.
        net.minecraft.client.multiplayer.PlayerInfo entry = null;
        net.minecraft.client.multiplayer.ClientPacketListener handler =
                net.minecraft.client.Minecraft.getInstance().getNetworkHandler();
        if (handler != null) {
            entry = handler.getPlayerListEntry(uuid);
        }
        String name = entry != null ? entry.getProfile().getName() : uuid.toString().substring(0, 8);
        tooltip.add(Component.translatable("tooltip.%s.artisan".formatted(MiddleEarth.MOD_ID)).append(name)
                .formatted(ChatFormatting.GRAY));
    }

    @Override
    public UUID uuid() {
        return uuid;
    }
}
