package net.jukoz.me.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.crockpot.CrockpotScreenHandler;
import net.jukoz.me.gui.alloyfurnace.AlloyFurnaceScreenHandler;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.gui.forge.ForgeAlloyingScreenHandler;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.jukoz.me.gui.structuremanager.StructureManagerScreenData;
import net.jukoz.me.gui.structuremanager.StructureManagerScreenHandler;
import net.jukoz.me.gui.structuremanager.structurenest.StructureNestScreenData;
import net.jukoz.me.gui.structuremanager.structurenest.StructureNestScreenHandler;
import net.jukoz.me.gui.wood_pile.WoodPileScreenHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.resources.ResourceLocation;

public class ModScreenHandlers {
    public static MenuType<AlloyFurnaceScreenHandler> ALLOY_SCREEN_HANDLER;
    public static MenuType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER;
    public static MenuType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER;
    public static MenuType<InscriptionTableScreenHandler> INSCRIPTION_SCREEN_HANDLER;
    public static MenuType<CrockpotScreenHandler> CROCKPOT_SCREEN_HANDLER;
    public static MenuType<ForgeAlloyingScreenHandler> FORGE_ALLOYING_SCREEN_HANDLER;
    public static MenuType<ShapingAnvilScreenHandler> TREATED_ANVIL_SCREEN_HANDLER;
    public static MenuType<StructureManagerScreenHandler> STRUCTURE_MANAGER_SCREEN_HANDLER;
    public static MenuType<StructureNestScreenHandler> STRUCTURE_NEST_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        ALLOY_SCREEN_HANDLER = new MenuType<>(AlloyFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        ARTISAN_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(
                (syncId, inventory, buf) -> new ArtisanTableScreenHandler(syncId, inventory, buf.readUtf()));
        WOOD_PILE_SCREEN_HANDLER = new MenuType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        INSCRIPTION_SCREEN_HANDLER = new MenuType<>(InscriptionTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

        CROCKPOT_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(
                (syncId, inventory, buf) -> new CrockpotScreenHandler(syncId, inventory, buf.readBlockPos()));
        FORGE_ALLOYING_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(
                (syncId, inventory, buf) -> new ForgeAlloyingScreenHandler(syncId, inventory, buf.readBlockPos()));
        TREATED_ANVIL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(
                (syncId, inventory, buf) -> new ShapingAnvilScreenHandler(syncId, inventory, buf.readBlockPos()));
        STRUCTURE_MANAGER_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(
                (syncId, inventory, buf) -> new StructureManagerScreenHandler(syncId, inventory, StructureManagerScreenData.PACKET_CODEC.decode(buf)));
        STRUCTURE_NEST_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(
                (syncId, inventory, buf) -> new StructureNestScreenHandler(syncId, inventory, StructureNestScreenData.PACKET_CODEC.decode(buf)));

        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "alloy_furnace"), ALLOY_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "artisan_table"), ARTISAN_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "wood_pile"), WOOD_PILE_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_table"), INSCRIPTION_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "crockpot"), CROCKPOT_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "forge_alloying"), FORGE_ALLOYING_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "treated_anvil"), TREATED_ANVIL_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "structure_manager"), STRUCTURE_MANAGER_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new ResourceLocation(MiddleEarth.MOD_ID, "structure_nest"), STRUCTURE_NEST_SCREEN_HANDLER);
    }
}
