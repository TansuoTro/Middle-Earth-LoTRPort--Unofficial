package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** Forge compat shim for Fabric's {@code ServerPlayConnectionEvents}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ServerPlayConnectionEvents {
    /** Fabric's handler shape: {@code (handler, sender, server) -> ...}. */
    public interface PlayJoin {
        void onPlayReady(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server);
    }

    public interface PlayDisconnect {
        void onPlayDisconnect(ServerGamePacketListenerImpl handler, MinecraftServer server);
    }

    /** Tiny listener list that mirrors Fabric's {@code EVENT.register(...)} spelling. */
    public static final class JoinEvent {
        private final List<PlayJoin> listeners = new ArrayList<>();

        public void register(PlayJoin listener) {
            listeners.add(listener);
        }

        private void invoke(ServerGamePacketListenerImpl handler, MinecraftServer server) {
            PacketSender sender = () -> {
            };
            for (PlayJoin listener : listeners) {
                listener.onPlayReady(handler, sender, server);
            }
        }
    }

    public static final class DisconnectEvent {
        private final List<PlayDisconnect> listeners = new ArrayList<>();

        public void register(PlayDisconnect listener) {
            listeners.add(listener);
        }

        private void invoke(ServerGamePacketListenerImpl handler, MinecraftServer server) {
            for (PlayDisconnect listener : listeners) {
                listener.onPlayDisconnect(handler, server);
            }
        }
    }

    public static final JoinEvent JOIN = new JoinEvent();
    public static final DisconnectEvent DISCONNECT = new DisconnectEvent();

    private ServerPlayConnectionEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(
            net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            JOIN.invoke(player.connection, player.getServer());
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(
            net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            DISCONNECT.invoke(player.connection, player.getServer());
        }
    }
}
