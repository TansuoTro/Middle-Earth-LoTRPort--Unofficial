package net.jukoz.me.api.entity.ai.brain;

import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.jukoz.me.api.SevenStarsApi;

public class ActivitiesAPI {
    public static final Activity BABY_IDLE = register("baby_idle");
    public static final Activity BABY_REST = register("baby_rest");

    private static Activity register(String id) {
        return Registry.register(Registries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + SevenStarsApi.MOD_ID);
    }
}
