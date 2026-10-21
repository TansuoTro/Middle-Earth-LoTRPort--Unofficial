package net.fabricmc.fabric.api.entity.event.v1;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

/** Forge compat shim for Fabric's {@code ServerEntityCombatEvents}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ServerEntityCombatEvents {
    private static final List<KilledHandler> KILLED_HANDLERS = new ArrayList<>();

    private ServerEntityCombatEvents() {}

    /** Fabric's {@code (world, entity, killedEntity) -> ...} shape. */
    public interface KilledHandler {
        void afterKilledOtherEntity(Level world, Entity entity, Entity killed);
    }

    public static final class Event {
        public void register(KilledHandler handler) {
            KILLED_HANDLERS.add(handler);
        }
    }

    public static final Event AFTER_KILLED_OTHER_ENTITY = new Event();

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity killed = event.getEntity();
        Entity killer = killed.getKillCredit();
        if (killer == null) {
            return;
        }
        KILLED_HANDLERS.forEach(handler -> handler.afterKilledOtherEntity(killed.level(), killer, killed));
    }
}
