package net.fabricmc.fabric.api.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Forge compat shim for Fabric's {@code FuelRegistry}. Fabric registers fuel times up front;
 * Forge only exposes them through {@link FurnaceFuelBurnTimeEvent}, so the registrations are
 * buffered here and applied whenever the game asks for a burn time.
 */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public interface FuelRegistry {
    FuelRegistry INSTANCE = new FuelRegistry() {};

    Map<Item, Integer> BY_ITEM = new HashMap<>();
    List<Object[]> BY_PREDICATE = new ArrayList<>();

    default void add(ItemLike item, int burnTime) {
        BY_ITEM.put(item.asItem(), burnTime);
    }

    default void add(Predicate<ItemLike> predicate, int burnTime) {
        BY_PREDICATE.add(new Object[]{predicate, burnTime});
    }

    default Integer get(Item item) {
        Integer direct = BY_ITEM.get(item);
        if (direct != null) {
            return direct;
        }
        for (Object[] entry : BY_PREDICATE) {
            @SuppressWarnings("unchecked")
            Predicate<ItemLike> predicate = (Predicate<ItemLike>) entry[0];
            if (predicate.test(item)) {
                return (Integer) entry[1];
            }
        }
        return null;
    }

    @SubscribeEvent
    static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getBurnTime() > 0) {
            return;
        }
        Integer value = INSTANCE.get(event.getItemStack().getItem());
        if (value != null) {
            event.setBurnTime(value);
        }
    }

    /** Kept for API parity with Fabric's item-based lookup helper. */
    static int burnTime(ItemStack stack, RecipeType<?> type) {
        Integer value = INSTANCE.get(stack.getItem());
        return value == null ? 0 : value;
    }
}
