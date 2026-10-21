package net.fabricmc.fabric.api.event.lifecycle.v1;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** Forge compat shim for Fabric's {@code ServerLifecycleEvents}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ServerLifecycleEvents {
    private static final List<Consumer<MinecraftServer>> STARTED = new ArrayList<>();
    private static final List<Consumer<MinecraftServer>> STOPPED = new ArrayList<>();

    public static final Event STARTED_EVENT = new Event(STARTED);
    public static final Event STOPPED_EVENT = new Event(STOPPED);

    /** Fabric's {@code SERVER_STARTED.register(server -> ...)} holder. */
    public static final class Event {
        private final List<Consumer<MinecraftServer>> listeners;

        Event(List<Consumer<MinecraftServer>> listeners) {
            this.listeners = listeners;
        }

        public void register(Consumer<MinecraftServer> listener) {
            listeners.add(listener);
        }
    }

    /** Kept so existing {@code SERVER_STARTED} references keep compiling. */
    public static final class ServerStarted extends Event {
        ServerStarted() {
            super(STARTED);
        }
    }

    public static final class ServerStopped extends Event {
        ServerStopped() {
            super(STOPPED);
        }
    }

    public static final ServerStarted SERVER_STARTED = new ServerStarted();
    public static final ServerStopped SERVER_STOPPED = new ServerStopped();

    private ServerLifecycleEvents() {}

    @SubscribeEvent
    public static void onServerStarted(net.minecraftforge.event.server.ServerStartedEvent event) {
        STARTED.forEach(listener -> listener.accept(event.getServer()));
    }

    @SubscribeEvent
    public static void onServerStopped(net.minecraftforge.event.server.ServerStoppedEvent event) {
        STOPPED.forEach(listener -> listener.accept(event.getServer()));
    }
}
