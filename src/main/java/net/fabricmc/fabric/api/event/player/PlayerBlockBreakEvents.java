package net.fabricmc.fabric.api.event.player;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/** Forge compat shim for Fabric's {@code PlayerBlockBreakEvents}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class PlayerBlockBreakEvents {
    private static final List<BreakHandler> AFTER_HANDLERS = new ArrayList<>();

    private PlayerBlockBreakEvents() {}

    /** Fabric's {@code (world, player, pos, state, blockEntity) -> ...} shape. */
    public interface BreakHandler {
        void onBlockBreak(Level world, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity);
    }

    public static final class AfterEvent {
        public void register(BreakHandler handler) {
            AFTER_HANDLERS.add(handler);
        }
    }

    public static final AfterEvent AFTER_EVENT = new AfterEvent();

    /** Kept so {@code AFTER.register(...)} keeps compiling. */
    public static final class After extends AfterEvent {
    }

    public static final After AFTER = new After();

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        AFTER_HANDLERS.forEach(handler -> handler.onBlockBreak(
                (Level) event.getLevel(), event.getPlayer(), event.getPos(), event.getState(),
                event.getLevel().getBlockEntity(event.getPos())));
    }
}
