package net.fabricmc.fabric.api.client.event.lifecycle.v1;

import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** Forge compat shim for Fabric's {@code ClientTickEvents}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class ClientTickEvents {
    private static final List<Consumer<Minecraft>> END_TICK = new ArrayList<>();
    private static final List<Consumer<Minecraft>> START_TICK = new ArrayList<>();

    public static final Event END_CLIENT_TICK = new Event(END_TICK);
    public static final Event START_CLIENT_TICK = new Event(START_TICK);

    public static final class Event {
        private final List<Consumer<Minecraft>> listeners;

        Event(List<Consumer<Minecraft>> listeners) {
            this.listeners = listeners;
        }

        public void register(Consumer<Minecraft> listener) {
            listeners.add(listener);
        }
    }

    private ClientTickEvents() {}

    @SubscribeEvent
    public static void onClientTick(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
        if (event.phase == net.minecraftforge.event.TickEvent.Phase.END) {
            END_TICK.forEach(listener -> listener.accept(Minecraft.getInstance()));
        } else {
            START_TICK.forEach(listener -> listener.accept(Minecraft.getInstance()));
        }
    }
}
