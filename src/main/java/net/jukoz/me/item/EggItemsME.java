package net.jukoz.me.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.datageneration.content.TranslationEntries;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.registries.RegistryAliasesME;
import java.util.function.Function;

public class EggItemsME {

    /**
     * Middle-earth mod Spawn Eggs registry
     */

    public static final Item BROADHOOF_GOAT_SPAWN_EGG = registerItem("broadhoof_goat_spawn_egg",
            (settings) -> egg(EntitiesME.BROADHOOF_GOAT, settings), new MESettings());

    public static final Item GREAT_HORN_SPAWN_EGG = registerItem("great_horn_spawn_egg",
            (settings) -> egg(EntitiesME.GREAT_HORN, settings), new MESettings());

    public static final Item WARG_SPAWN_EGG = registerItem("warg_spawn_egg",
            (settings) -> egg(EntitiesME.WARG, settings), new MESettings());

    public static final Item STONE_TROLL_SPAWN_EGG = registerItem("stone_troll_spawn_egg",
            (settings) -> egg(EntitiesME.STONE_TROLL, settings), new MESettings());

    public static final Item SNOW_TROLL_SPAWN_EGG = registerItem("snow_troll_spawn_egg",
            (settings) -> egg(EntitiesME.SNOW_TROLL, settings), new MESettings());
    public static final Item CAVE_TROLL_SPAWN_EGG = registerItem("cave_troll_spawn_egg",
            (settings) -> egg(EntitiesME.CAVE_TROLL, settings), new MESettings());

    public static final Item SHELOBITE_LARVA_SPAWN_EGG = registerItem("shelobite_larva_spawn_egg",
            (settings) -> egg(EntitiesME.SHELOBITE_LARVA, settings), new MESettings());
    public static final Item SHELOBITE_SCUTTLER_SPAWN_EGG = registerItem("shelobite_scuttler_spawn_egg",
            (settings) -> egg(EntitiesME.SHELOBITE_SCUTTLER, settings), new MESettings());
    public static final Item SHELOBITE_SPAWN_SPAWN_EGG = registerItem("spawn_of_shelob_spawn_egg",
            (settings) -> egg(EntitiesME.SPAWN_OF_SHELOB, settings), new MESettings());

    // Npcs
    public static final Item NPC_SPAWN_EGG = registerSpecialEgg("npc_spawn_egg",
            (settings) -> egg(EntitiesME.NPC, settings), new MESettings());

    /** 1.20.1 spawn eggs still take two colour ints; keep one shared Middle-earth palette. */
    private static SpawnEggItem egg(EntityType<? extends Mob> type, Item.Settings settings) {
        return new SpawnEggItem(type, 0x5B4A32, 0xD9C7A3, settings);
    }

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings);
        ModItemGroups.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name), item);
    }
    private static Item registerSpecialEgg(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings);
        ModItemGroups.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Egg Items for " + MiddleEarth.MOD_ID);

        TranslationEntries.spawnEggEntries.add(MiddleEarth.of("npc_random_spawn_egg"));
    }
}
