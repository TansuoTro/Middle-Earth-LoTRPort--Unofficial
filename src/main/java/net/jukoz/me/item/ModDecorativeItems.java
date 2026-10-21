package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.item.items.CustomSpawnEggItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModVerticallyAttachableBlockItem;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;

public class ModDecorativeItems {
    public static final Item DWARVEN_LANTERN = DecorativeItemsME.DWARVEN_LANTERN;
    public static final Item SILVER_LANTERN = DecorativeItemsME.SILVER_LANTERN;
    public static final Item ALLOY_FURNACE = registerItem("alloy_furnace",
            new BlockItem(net.jukoz.me.compat.MEBlocksLegacy.ALLOY_FURNACE, new MESettings()));
    public static final Item ARTISAN_TABLE = DecorativeItemsME.ARTISAN_TABLE;
    public static final Item WOOD_PILE = DecorativeItemsME.WOOD_PILE;
    public static final Item TROLL_STATUE = DecorativeItemsME.TROLL_STATUE;

    private static Item registerItem(String name, Item item) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
