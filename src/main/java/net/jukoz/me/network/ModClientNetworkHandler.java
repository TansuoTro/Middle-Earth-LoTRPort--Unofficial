package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.network.connections.IConnectionToServer;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.S2C.*;
import net.jukoz.me.network.packets.ServerToClientPacket;

import java.util.function.BiConsumer;

public class ModClientNetworkHandler {
    public static void register(IConnectionToServer connection) {
        net.jukoz.me.compat.net.PacketCodecs.setClientRegistries(() -> {
            var handler = net.minecraft.client.MinecraftClient.getInstance().getNetworkHandler();
            return handler == null ? null : handler.getRegistryManager();
        });
        // Application [CLIENT SIDE]
        ClientPlayNetworking.registerGlobalReceiver(PacketForceOnboardingScreen.TYPE, wrapClientHandler(connection, PacketForceOnboardingScreen::process));
        ClientPlayNetworking.registerGlobalReceiver(PacketOnboardingResult.TYPE, wrapClientHandler(connection, PacketOnboardingResult::process));
        ClientPlayNetworking.registerGlobalReceiver(PacketLivingEntityData.TYPE, wrapClientHandler(connection, PacketLivingEntityData::process));
        ClientPlayNetworking.registerGlobalReceiver(InscriptionEnchantInfoPacket.TYPE, wrapClientHandler(connection, InscriptionEnchantInfoPacket::process));
        ClientPlayNetworking.registerGlobalReceiver(ShapingAnvilRecipePacket.TYPE, wrapClientHandler(connection, ShapingAnvilRecipePacket::process));
        ClientPlayNetworking.registerGlobalReceiver(ArtisanRecipePacket.TYPE, wrapClientHandler(connection, ArtisanRecipePacket::process));
        ClientPlayNetworking.registerGlobalReceiver(PacketOpenMapScreen.TYPE, wrapClientHandler(connection, PacketOpenMapScreen::process));
    }

    private static <T extends ServerToClientPacket<T>> ClientPlayNetworking.PlayPacketHandler<T> wrapClientHandler(IConnectionToServer connection, BiConsumer<T, ClientPacketContext> consumer) {
        return (packet, player, responseSender) ->
                consumer.accept(packet, new ClientPacketContext(player, connection));
    }
}
