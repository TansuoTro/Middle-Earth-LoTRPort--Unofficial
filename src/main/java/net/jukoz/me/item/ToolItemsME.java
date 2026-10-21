package net.jukoz.me.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.util.Unit;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.datageneration.content.TranslationEntries;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.item.items.CustomPickaxeItem;
import net.jukoz.me.item.items.CustomShovelItem;
import net.jukoz.me.item.items.CustomHoeItem;
import net.jukoz.me.item.items.PipeItem;
import net.jukoz.me.item.items.SmithingHammerItem;
import net.jukoz.me.item.items.weapons.CustomAxeWeaponItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ToolMaterialsME;
import net.jukoz.me.registries.RegistryAliasesME;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ToolItemsME {
    /**
     * Middle-earth mod Tool Items registry
     */

    private static final int IRON_DAMAGE = 1;
    private static final int IRON_AXE_DAMAGE = IRON_DAMAGE + 5;
    private static final int IRON_PICKAXE_DAMAGE = IRON_DAMAGE - 1;
    private static final int IRON_HOE_DAMAGE = IRON_DAMAGE - 2;
    private static final float IRON_ATTACK_SPEED = -2.5f;
    private static final float IRON_AXE_ATTACK_SPEED = -3.1f;
    private static final float IRON_HOE_ATTACK_SPEED = -2f;

    public static List<Item> smithingHammers = new ArrayList<>();

    public static final Item COPPER_SMITHING_HAMMER = registerSmithingHammerItem("copper_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.COPPER_HAMMER, -3.5f), new MESettings().enchantable(8));
    public static final Item BRONZE_SMITHING_HAMMER = registerSmithingHammerItem("bronze_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BRONZE_HAMMER, -3.35f), new MESettings().enchantable(10));
    public static final Item CRUDE_SMITHING_HAMMER = registerSmithingHammerItem("crude_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.CRUDE_HAMMER, -3.35f), new MESettings().enchantable(10));

    public static final Item SMITHING_HAMMER = registerSmithingHammerItem("smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.STEEL_HAMMER, -3.2f), new MESettings().enchantable(12));
    public static final Item NOBLE_SMITHING_HAMMER = registerSmithingHammerItem("noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.NOBLE_STEEL_HAMMER, -3.0f), new MESettings().enchantable(15));

    public static final Item DWARVEN_SMITHING_HAMMER = registerSmithingHammerItem("dwarven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.KHAZAD_STEEL_HAMMER, -3.2f), new MESettings().enchantable(12));
    public static final Item DWARVEN_NOBLE_SMITHING_HAMMER = registerSmithingHammerItem("dwarven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.KHAZAD_NOBLE_STEEL_HAMMER, -3.0f), new MESettings().enchantable(15));

    public static final Item ELVEN_SMITHING_HAMMER = registerSmithingHammerItem("elven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.EDHEL_STEEL_HAMMER, -3.2f), new MESettings().enchantable(12));
    public static final Item ELVEN_NOBLE_SMITHING_HAMMER = registerSmithingHammerItem("elven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.EDHEL_NOBLE_STEEL_HAMMER, -3.0f), new MESettings().enchantable(15));

    public static final Item ORCISH_SMITHING_HAMMER = registerSmithingHammerItem("orcish_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BURZUM_STEEL_HAMMER, -3.2f), new MESettings().enchantable(12));
    public static final Item ORCISH_ELITE_SMITHING_HAMMER = registerSmithingHammerItem("orcish_elite_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BURZUM_NOBLE_STEEL_HAMMER, -3.0f), new MESettings().enchantable(15));

    public static final Item MITHRIL_SMITHING_HAMMER = registerSmithingHammerItem("mithril_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.MITHRIL_HAMMER, -2.5f), new MESettings().enchantable(20)
                    .unbreakable());

    public static final Item IRON_CHISEL = registerItemHandheld("iron_chisel",
            Item::new, new MESettings().maxCount(1)
                    .maxDamage(4));
    public static final Item STEEL_CHISEL = registerItemHandheld("steel_chisel",
            Item::new, new MESettings().maxCount(1)
                    .maxDamage(16));
    public static final Item MITHRIL_CHISEL = registerItemHandheld("mithril_chisel",
            Item::new, new MESettings().maxCount(1));

    public static final Item BRONZE_PICKAXE = registerItemHandheld("bronze_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.BRONZE, 0.5f, -2.8f, settings), new MESettings().maxDamage(ToolMaterialsME.BRONZE.getUses()));
    public static final Item BRONZE_AXE = registerItemHandheld("bronze_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BRONZE, settings), new MESettings().maxDamage(ToolMaterialsME.BRONZE.getUses()));
    public static final Item BRONZE_SHOVEL = registerItemHandheld("bronze_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.BRONZE, 1.5f, -3.0f, settings), new MESettings().maxDamage(ToolMaterialsME.BRONZE.getUses()));
    public static final Item BRONZE_HOE = registerItemHandheld("bronze_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.BRONZE, -2, -1.0f, settings), new MESettings().maxDamage(ToolMaterialsME.BRONZE.getUses()));

    public static final Item CRUDE_PICKAXE = registerItemHandheld("crude_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.CRUDE, 0.5f, -2.8f, settings), new MESettings().maxDamage(ToolMaterialsME.CRUDE.getUses()));
    public static final Item CRUDE_AXE = registerItemDualModel("crude_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.CRUDE, settings), new MESettings().maxDamage(ToolMaterialsME.CRUDE.getUses()));
    public static final Item CRUDE_SHOVEL = registerItemHandheld("crude_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.CRUDE, 1.5f, -3.0f, settings), new MESettings().maxDamage(ToolMaterialsME.CRUDE.getUses()));
    public static final Item CRUDE_HOE = registerItemHandheld("crude_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.CRUDE, -2, -1.0f, settings), new MESettings().maxDamage(ToolMaterialsME.CRUDE.getUses()));

    public static final Item STEEL_PICKAXE = registerItemHandheld("steel_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.STEEL, 1, -2.8f, settings), new MESettings().maxDamage(ToolMaterialsME.STEEL.getUses()));
    public static final Item STEEL_AXE = registerItemDualModel("steel_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.STEEL, settings), new MESettings().maxDamage(ToolMaterialsME.STEEL.getUses()));
    public static final Item STEEL_SHOVEL = registerItemHandheld("steel_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.STEEL, 1.5f, -3.0f, settings), new MESettings().maxDamage(ToolMaterialsME.STEEL.getUses()));
    public static final Item STEEL_HOE = registerItemHandheld("steel_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.STEEL, -2, -1.0f, settings), new MESettings().maxDamage(ToolMaterialsME.STEEL.getUses()));

    public static final Item BURZUM_STEEL_PICKAXE = registerItemHandheld("burzum_steel_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.BURZUM_STEEL, 1, -2.8f, settings), new MESettings().maxDamage(ToolMaterialsME.BURZUM_STEEL.getUses()));
    public static final Item BURZUM_STEEL_AXE = registerItemDualModel("burzum_steel_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new MESettings().maxDamage(ToolMaterialsME.BURZUM_STEEL.getUses()));
    public static final Item BURZUM_STEEL_SHOVEL = registerItemHandheld("burzum_steel_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.BURZUM_STEEL, 1.5f, -3.0f, settings), new MESettings().maxDamage(ToolMaterialsME.BURZUM_STEEL.getUses()));
    public static final Item BURZUM_STEEL_HOE = registerItemHandheld("burzum_steel_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.BURZUM_STEEL, -2, -1.0f, settings), new MESettings().maxDamage(ToolMaterialsME.BURZUM_STEEL.getUses()));

    public static final Item EDHEL_STEEL_PICKAXE = registerItemHandheld("edhel_steel_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.EDHEL_STEEL, 1, -2.8f, settings), new MESettings().maxDamage(ToolMaterialsME.EDHEL_STEEL.getUses()));
    public static final Item EDHEL_STEEL_AXE = registerItemDualModel("edhel_steel_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.EDHEL_STEEL, settings), new MESettings().maxDamage(ToolMaterialsME.EDHEL_STEEL.getUses()));
    public static final Item EDHEL_STEEL_SHOVEL = registerItemHandheld("edhel_steel_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.EDHEL_STEEL, 1.5f, -3.0f, settings), new MESettings().maxDamage(ToolMaterialsME.EDHEL_STEEL.getUses()));
    public static final Item EDHEL_STEEL_HOE = registerItemHandheld("edhel_steel_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.EDHEL_STEEL, -2, -1.0f, settings), new MESettings().maxDamage(ToolMaterialsME.EDHEL_STEEL.getUses()));
    
    public static final Item KHAZAD_STEEL_PICKAXE = registerItemHandheld("khazad_steel_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.KHAZAD_STEEL, 1, -2.8f, settings), new MESettings().maxDamage(ToolMaterialsME.KHAZAD_STEEL.getUses()));
    public static final Item KHAZAD_STEEL_AXE = registerItemHandheld("khazad_steel_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new MESettings().maxDamage(ToolMaterialsME.KHAZAD_STEEL.getUses()));
    public static final Item KHAZAD_STEEL_SHOVEL = registerItemHandheld("khazad_steel_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.KHAZAD_STEEL, 1.5f, -3.0f, settings), new MESettings().maxDamage(ToolMaterialsME.KHAZAD_STEEL.getUses()));
    public static final Item KHAZAD_STEEL_HOE = registerItemHandheld("khazad_steel_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.KHAZAD_STEEL, -2, -1.0f, settings), new MESettings().maxDamage(ToolMaterialsME.KHAZAD_STEEL.getUses()));

    public static final Item MITHRIL_PICKAXE = registerItemHandheld("mithril_pickaxe",
            (settings) -> new CustomPickaxeItem(ToolMaterialsME.MITHRIL, 1, -2.7f, settings), new MESettings().fireproof()
                    .unbreakable());
    public static final Item MITHRIL_AXE = registerItemDualModel("mithril_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.MITHRIL, settings), new MESettings().fireproof()
                    .unbreakable());
    public static final Item MITHRIL_SHOVEL = registerItemHandheld("mithril_shovel",
            (settings) -> new CustomShovelItem(ToolMaterialsME.MITHRIL, 1.5f, -3.0f, settings), new MESettings().fireproof()
                    .unbreakable());
    public static final Item MITHRIL_HOE = registerItemHandheld("mithril_hoe",
            (settings) -> new CustomHoeItem(ToolMaterialsME.MITHRIL, -2, 1.0f, settings), new MESettings().fireproof()
                    .unbreakable());

    public static final Item PIPE = registerItem2dGUI3dPerson("pipe",
            (settings) -> new PipeItem(settings, 3), new MESettings().maxCount(1));
    public static final Item CLAY_PIPE = registerItem2dGUI3dPerson("clay_pipe",
            (settings) -> new PipeItem(settings, 3), new MESettings().maxCount(1));
    public static final Item RIVERBEND_PIPE = registerItem2dGUI3dPerson("riverbend_pipe",
            (settings) -> new PipeItem(settings, 3 ), new MESettings().maxCount(1));
    public static final Item BRIMMINGBEND_PIPE = registerItem2dGUI3dPerson("brimmingbend_pipe",
            (settings) -> new PipeItem(settings, 5), new MESettings().maxCount(1));
    public static final Item LONGBOTTOM_PIPE = registerItem2dGUI3dPerson("longbottom_pipe",
            (settings) -> new PipeItem(settings, 5), new MESettings().maxCount(1));
    
    private static Item registerItemHandheld(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = (Item)factory.apply(settings);
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultInstance());
        SimpleHandheldItemModel.items.add(item);
        return registerItem(item, name);
    }

    private static Item registerSmithingHammerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = registerItemHandheld(name, factory, settings);
        smithingHammers.add(item);
        return item;
    }

    private static Item registerItemDualModel(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = (Item)factory.apply(settings);
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultInstance());
        SimpleBigItemModel.items.add(item);
        return registerItem(item, name);
    }
    
    public static Item registerItem2dGUI3dPerson(String name, Function<Item.Properties, Item> factory, Item.Properties settings){
        Item item = (Item)factory.apply(settings);
        SimpleBigItemModel.genericItems.add(item);
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultInstance());
        return registerItem(item, name);
    }

    private static Item registerItem(Item item, String name){
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }

}
