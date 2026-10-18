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
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<AlloyFurnaceScreenHandler> ALLOY_SCREEN_HANDLER;
    public static ScreenHandlerType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER;
    public static ScreenHandlerType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER;
    public static ScreenHandlerType<InscriptionTableScreenHandler> INSCRIPTION_SCREEN_HANDLER;
    public static ScreenHandlerType<CrockpotScreenHandler> CROCKPOT_SCREEN_HANDLER;
    public static ScreenHandlerType<ForgeAlloyingScreenHandler> FORGE_ALLOYING_SCREEN_HANDLER;
    public static ScreenHandlerType<ShapingAnvilScreenHandler> TREATED_ANVIL_SCREEN_HANDLER;
    public static ScreenHandlerType<StructureManagerScreenHandler> STRUCTURE_MANAGER_SCREEN_HANDLER;
    public static ScreenHandlerType<StructureNestScreenHandler> STRUCTURE_NEST_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        ALLOY_SCREEN_HANDLER = new ScreenHandlerType<>(AlloyFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        ARTISAN_SCREEN_HANDLER = new ScreenHandlerType<>(ArtisanTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        WOOD_PILE_SCREEN_HANDLER = new ScreenHandlerType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
        INSCRIPTION_SCREEN_HANDLER = new ScreenHandlerType<>(InscriptionTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

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

        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "alloy_furnace"), ALLOY_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "artisan_table"), ARTISAN_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "wood_pile"), WOOD_PILE_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "inscription_table"), INSCRIPTION_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "crockpot"), CROCKPOT_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "forge_alloying"), FORGE_ALLOYING_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "treated_anvil"), TREATED_ANVIL_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "structure_manager"), STRUCTURE_MANAGER_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MiddleEarth.MOD_ID, "structure_nest"), STRUCTURE_NEST_SCREEN_HANDLER);
    }
}
