package net.fabricmc.fabric.api.loot.v2;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

/** Forge compat shim for Fabric's {@code LootTableEvents}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class LootTableEvents {
    /** Fabric's {@code (resourceManager, lootManager, id, tableBuilder, source) -> ...} shape. */
    public interface ModifyHandler {
        void modifyLootTable(net.minecraft.server.packs.resources.ResourceManager resourceManager,
                             Object lootManager, ResourceLocation id, LootTable table,
                             LootTableSource source);
    }

    public enum LootTableSource {
        VANILLA, MOD
    }

    private static final List<ModifyHandler> MODIFY_HANDLERS = new ArrayList<>();

    public static final class ModifyEvent {
        public void register(ModifyHandler handler) {
            MODIFY_HANDLERS.add(handler);
        }
    }

    /** Kept so {@code MODIFY.register(...)} keeps compiling. */
    public static final class Modify extends ModifyEvent {
    }

    public static final Modify MODIFY = new Modify();

    private LootTableEvents() {}

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        LootTableSource source = id.getNamespace().equals(MiddleEarth.MOD_ID)
                ? LootTableSource.MOD
                : LootTableSource.VANILLA;
        var server = net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        for (ModifyHandler handler : MODIFY) {
            handler.modifyLootTable(server == null ? null : server.getResourceManager(), null, id,
                    event.getTable(), source);
        }
    }
}
