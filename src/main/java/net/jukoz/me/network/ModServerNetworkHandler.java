package net.jukoz.me.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.network.connections.IConnectionToClient;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.C2S.*;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;

/**
 * Server-side registration of the client-to-server packets.
 *
 * <p>1.21.8 registers payload types into a registry and then registers receivers keyed by
 * payload id. Fabric 1.20.1's {@code PacketType} does both at once: {@code PacketType.create}
 * claims the channel and {@code registerGlobalReceiver(type, handler)} binds the handler, so
 * the separate registration block disappears.
 */
public class ModServerNetworkHandler {
    public static void register(IConnectionToClient connection) {
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureManagerRespawnEntities.TYPE, wrapServerHandler(connection, PacketStructureManagerRespawnEntities::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureManagerShowAllEntities.TYPE, wrapServerHandler(connection, PacketStructureManagerShowAllEntities::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureNestUpdateBlockEntityRequest.TYPE, wrapServerHandler(connection, PacketStructureNestUpdateBlockEntityRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureManagerUpdateBlockEntityRequest.TYPE, wrapServerHandler(connection, PacketStructureManagerUpdateBlockEntityRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetAffiliation.TYPE, wrapServerHandler(connection, PacketSetAffiliation::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetRace.TYPE, wrapServerHandler(connection, PacketSetRace::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCurrentOverworldSpawn.TYPE, wrapServerHandler(connection, PacketTeleportToCurrentOverworldSpawn::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToDynamicWorldCoordinate.TYPE, wrapServerHandler(connection, PacketTeleportToDynamicWorldCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCustomCoordinate.TYPE, wrapServerHandler(connection, PacketTeleportToCustomCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToDynamicCoordinate.TYPE, wrapServerHandler(connection, PacketTeleportToDynamicCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCurrentSpawn.TYPE, wrapServerHandler(connection, PacketTeleportToCurrentSpawn::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetSpawnData.TYPE, wrapServerHandler(connection, PacketSetSpawnData::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketOnboardingRequest.TYPE, wrapServerHandler(connection, PacketOnboardingRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(ForgeOutputPacket.TYPE, wrapServerHandler(connection, ForgeOutputPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ForgeModeSwitchPacket.TYPE, wrapServerHandler(connection, ForgeModeSwitchPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(InscriptionWordUpdatePacket.TYPE, wrapServerHandler(connection, InscriptionWordUpdatePacket::process));
        ServerPlayNetworking.registerGlobalReceiver(InscriptionConfirmationPacket.TYPE, wrapServerHandler(connection, InscriptionConfirmationPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(AnvilIndexPacket.TYPE, wrapServerHandler(connection, AnvilIndexPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ArtisanIndexPacket.TYPE, wrapServerHandler(connection, ArtisanIndexPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ArtisanTableTabPacket.TYPE, wrapServerHandler(connection, ArtisanTableTabPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(HoodStateTogglePacket.TYPE, wrapServerHandler(connection, HoodStateTogglePacket::process));
    }

    private static <T extends ClientToServerPacket<T>> ServerPlayNetworking.PlayPacketHandler<T> wrapServerHandler(
            IConnectionToClient connection,
            BiConsumer<T, ServerPacketContext> consumer
    ) {
        return (packet, player, responseSender) -> {
            ServerPlayer serverPlayer = player;
            consumer.accept(packet, new ServerPacketContext(serverPlayer, connection));
        };
    }
}
